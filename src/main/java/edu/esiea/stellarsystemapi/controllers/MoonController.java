package edu.esiea.stellarsystemapi.controllers;

import java.net.URI;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.esiea.stellarsystemapi.model.Moon;
import edu.esiea.stellarsystemapi.services.MoonService;

@RestController
@RequestMapping("/api/moon")
public class MoonController {

    private final MoonService moonService;

    public MoonController(MoonService moonService) {
        this.moonService = moonService;
    }

    @PostMapping
    public ResponseEntity<Moon> addMoon(@RequestBody Moon moon) {
        try {
            moon = this.moonService.createMoon(moon);
        } catch (ServiceException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(moon.getId()).toUri();
        return ResponseEntity.created(uri).body(moon);
    }
}
