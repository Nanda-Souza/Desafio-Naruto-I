package com.db.naruto.domain.controller;

import com.db.naruto.domain.dto.PersonagemRequest;
import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.service.PersonagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personagem")
public class PersonagemController {
    @Autowired
    private final PersonagemService personagemService;

    public PersonagemController(PersonagemService personagemService) {this.personagemService = personagemService;}

    @GetMapping
    public ResponseEntity<List<PersonagemResponse>> listarPersonagens() {
        return ResponseEntity.ok(personagemService.listarPersonagens());
    }

}
