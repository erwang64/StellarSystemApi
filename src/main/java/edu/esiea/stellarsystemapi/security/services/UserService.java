package edu.esiea.stellarsystemapi.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.esiea.stellarsystemapi.security.model.User;
import edu.esiea.stellarsystemapi.security.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    	 this.userRepository = userRepository;
    	 this.passwordEncoder = passwordEncoder;
    }
    


    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }


    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
