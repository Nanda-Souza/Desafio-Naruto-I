package com.db.naruto.domain.controller;


import com.db.naruto.domain.dto.JutsuRemoveRequest;
import com.db.naruto.domain.dto.JutsuRemoveResponse;
import com.db.naruto.domain.service.JutsuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jutsu")
public class JutsuController {
    @Autowired
    private final JutsuService jutsuService;

    public JutsuController(JutsuService jutsuService) {this.jutsuService = jutsuService;}

    @Operation(description = "Remove um jutsu de um personagem buscando por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna quando o jutsu é removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Não encontrou o personagem pelo id informado"),
            @ApiResponse(responseCode = "404", description = "Não encontrou o jutsu para remover no personagem informado"),
            @ApiResponse(responseCode = "422", description = "Não executa a remoção pois não pode remover um jutsu caso o personagem fique sem jutsus")
    })
    @DeleteMapping("/remover/{idPersonagem}")
    public ResponseEntity<JutsuRemoveResponse> removerJutsuDoPersonagem(
            @PathVariable Long idPersonagem,
            @RequestBody @Valid JutsuRemoveRequest jutsuRemoveRequest
    ){
        return ResponseEntity.ok(jutsuService.removerJutsu(idPersonagem, jutsuRemoveRequest));
    }


}
