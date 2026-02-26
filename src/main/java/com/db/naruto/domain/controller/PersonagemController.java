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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @Operation(description = "Busca um personagem por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o personagem com base no Id informado"),
            @ApiResponse(responseCode = "404", description = "Não encontrou o personagem pelo id informado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonagemResponse> listarPersonagenPorId(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(personagemService.buscarPersonagemPorId(id));
    }

    @Operation(description = "Cria um ninja de ninjutsu")
    @ApiResponse(responseCode = "201", description = "Retorna o ninja de ninjutsu criado")
    @PostMapping("/ninja_de_ninjutsu")
    public ResponseEntity<PersonagemResponse> salvarNinjaDeNinjutsu(
            @RequestBody @Valid PersonagemRequest personagemRequest
    ){
        PersonagemResponse salvo = personagemService.salvarNinjaDeNinjutsu(personagemRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();

        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(description = "Cria um ninja de taijutsu")
    @ApiResponse(responseCode = "201", description = "Retorna o ninja de taijutsu criado")
    @PostMapping("/ninja_de_taijutsu")
    public ResponseEntity<PersonagemResponse> salvarNinjaDeTaijutsu(
            @RequestBody @Valid PersonagemRequest personagemRequest
    ){
        PersonagemResponse salvo = personagemService.salvarNinjaDeTaijutsu(personagemRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();

        return ResponseEntity.created(location).body(salvo);
    }

    @Operation(description = "Cria um ninja de genjutsu")
    @ApiResponse(responseCode = "201", description = "Retorna o ninja de genjutsu criado")
    @PostMapping("/ninja_de_genjutsu")
    public ResponseEntity<PersonagemResponse> salvarNinjaDeGejutsu(
            @RequestBody @Valid PersonagemRequest personagemRequest
    ){

        PersonagemResponse salvo = personagemService.salvarNinjaDeGenjutsu(personagemRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(salvo.id())
                .toUri();

        return ResponseEntity.created(location).body(salvo);

    }

    @Operation(description = "Deleta um personagem com base no id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Retorna uma resposta vazia quando deleta o personagem com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrou o personagem pelo id informado")
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
            @ApiResponse(responseCode = "404", description = "Não encontrou o personagem pelo id informado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PersonagemResponse> atualizarPersonagem(
            @PathVariable Long id,
            @RequestBody @Valid PersonagemUpdateRequest personagemRequest
    ) {
        return ResponseEntity.ok(personagemService.atualizarPersonagem(id, personagemRequest));
    }

}
