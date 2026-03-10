package com.db.naruto.domain.controller;

import com.db.naruto.domain.dto.BatalhaRequest;
import com.db.naruto.domain.dto.BatalhaResponse;
import com.db.naruto.domain.service.BatalhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batalha")
public class BatalhaController {
    @Autowired
    private final BatalhaService batalhaService;

    public BatalhaController(BatalhaService batalhaService) {this.batalhaService = batalhaService;}

    @Operation(description = "Realiza a batalha entre 2 personagens por id informando o nome do jutsu")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o resultado da batalha entre os dois personagens com base nos Ids informado"),
            @ApiResponse(responseCode = "404", description = "Não encontrou algum dos personagens pelo id informado"),
            @ApiResponse(responseCode = "404", description = "Não encontrou o jutsu informado"),
            @ApiResponse(responseCode = "422", description = "Não executou a batalha pois algum dos personagens já foi derrotado")
    })
    @PostMapping()
    public ResponseEntity<BatalhaResponse> batalhaNinja(
            @RequestBody @Valid BatalhaRequest batalhaRequest
    ) {
        return ResponseEntity.ok(batalhaService.batalhar(batalhaRequest));
    }
}
