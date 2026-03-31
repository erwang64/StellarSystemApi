package edu.esiea.stellarsystemapi.security.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import edu.esiea.stellarsystemapi.security.controllers.dto.LoginRequest;
import edu.esiea.stellarsystemapi.security.controllers.dto.UserRequest;
import edu.esiea.stellarsystemapi.security.controllers.dto.UserResponse;
import edu.esiea.stellarsystemapi.security.controllers.dto.mappers.UserMapper;
import edu.esiea.stellarsystemapi.security.model.User;
import edu.esiea.stellarsystemapi.security.services.JwtService;
import edu.esiea.stellarsystemapi.security.services.UserService;
import edu.esiea.stellarsystemapi.controllers.dto.error.EndPointException;
import edu.esiea.stellarsystemapi.controllers.dto.error.ResourceType;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) throws EndPointException {
        try {
            User user = UserMapper.toEntity(userRequest);
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(createdUser));
        } catch (Exception e) {
            throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.USER, e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(UserMapper.toDtoList(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) throws EndPointException {
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            throw new EndPointException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé", ResourceType.USER, null, id);
        }
        return ResponseEntity.ok(UserMapper.toDto(user.get()));
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<UserResponse> findByLogin(@PathVariable String login) throws EndPointException {
        Optional<User> user = userService.findByLogin(login);
        if (user.isEmpty()) {
            throw new EndPointException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec le login: " + login, ResourceType.USER, null);
        }
        return ResponseEntity.ok(UserMapper.toDto(user.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int id, @Valid @RequestBody UserRequest userDetails) throws EndPointException {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isEmpty()) {
            throw new EndPointException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé", ResourceType.USER, null, id);
        }

        try {
            User user = existingUser.get();
            if (userDetails.getLogin() != null) {
                user.setLogin(userDetails.getLogin());
            }
            if (userDetails.getPassword() != null) {
                user.setPassword(userDetails.getPassword());
            }
            if (userDetails.getProfile() != null) {
                user.setProfile(edu.esiea.stellarsystemapi.security.model.Profile.valueOf(userDetails.getProfile().toUpperCase()));
            }
            User updatedUser = userService.updateUser(user);
            return ResponseEntity.ok(UserMapper.toDto(updatedUser));
        } catch (Exception e) {
            throw new EndPointException(HttpStatus.BAD_REQUEST, e.getMessage(), ResourceType.USER, e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) throws EndPointException {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        throw new EndPointException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé", ResourceType.USER, null, id);
    }

    /**
     * Endpoint de login
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserRequest dto) throws EndPointException {
        if (dto.getLogin() == null || dto.getLogin().isBlank() || dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new EndPointException(HttpStatus.BAD_REQUEST, "Login ou mot de passe non fourni", ResourceType.USER, null);
        }

        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = this.jwtService.generateToken(userDetails);
        
        return ResponseEntity.noContent()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .build();
    }
}
