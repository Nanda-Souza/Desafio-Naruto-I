package com.db.naruto.domain.controller;

import com.db.naruto.domain.dto.PersonagemRequest;
import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.dto.PersonagemUpdateRequest;
import com.db.naruto.domain.service.PersonagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(description = "Lista todos os personagens criados")
    @ApiResponse(responseCode = "200", description = "Lista todos os personagens criados")
    @GetMapping
    public ResponseEntity<List<PersonagemResponse>> listarPersonagens() {
        return ResponseEntity.ok(personagemService.listarPersonagens());
    }

    @Operation(description = "Cria um ninja de ninjutsu")
    @ApiResponse(responseCode = "200", description = "Retorna o ninja de ninjutsu criado")
    @PostMapping("/ninja_de_ninjutsu")
    public ResponseEntity<PersonagemResponse> salvarNinjaDeNinjutsu(
            @RequestBody @Valid PersonagemRequest personagemRequest
    ){
        return ResponseEntity.ok(personagemService.salvarNinjaDeNinjutsu(personagemRequest));
    }

    @Operation(description = "Cria um ninja de taijutsu")
    @ApiResponse(responseCode = "200", description = "Retorna o ninja de taijutsu criado")
    @PostMapping("/ninja_de_taijutsu")
    public ResponseEntity<PersonagemResponse> salvarNinjaDeTaijutsu(
            @RequestBody @Valid PersonagemRequest personagemRequest
    ){
        return ResponseEntity.ok(personagemService.salvarNinjaDeTaijutsu(personagemRequest));
    }

    @Operation(description = "Cria um ninja de genjutsu")
    @ApiResponse(responseCode = "200", description = "Retorna o ninja de genjutsu criado")
    @PostMapping("/ninja_de_genjutsu")
    public ResponseEntity<PersonagemResponse> salvarNinjaDeGejutsu(
            @RequestBody @Valid PersonagemRequest personagemRequest
    ){
        return ResponseEntity.ok(personagemService.salvarNinjaDeGenjutsu(personagemRequest));
    }

    @Operation(description = "Deleta um personagem com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma resposta vazia quando deleta o personagem com sucesso"),
            @ApiResponse(responseCode = "500", description = "Não encontrou o personagem pelo id informado")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPersonagem(
            @PathVariable Long id
    ) {
        personagemService.deletarPersonagem(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Atualiza um personagem com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o personagem atualizado"),
            @ApiResponse(responseCode = "500", description = "Não encontrou o personagem pelo id informado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PersonagemResponse> atualizarPersonagem(
            @PathVariable Long id,
            @RequestBody PersonagemUpdateRequest personagemRequest
    ) {
        return ResponseEntity.ok(personagemService.atualizarPersonagem(id, personagemRequest));
    }

}
