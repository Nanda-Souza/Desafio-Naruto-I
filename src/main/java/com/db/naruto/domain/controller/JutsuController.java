package com.db.naruto.domain.controller;


import com.db.naruto.domain.dto.JutsuRemoveRequest;
import com.db.naruto.domain.dto.JutsuRemoveResponse;
import com.db.naruto.domain.service.JutsuService;
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

    @DeleteMapping("/remover/{idPersonagem}")
    public ResponseEntity<JutsuRemoveResponse> removerJutsuDoPersonagem(
            @PathVariable Long idPersonagem,
            @RequestBody @Valid JutsuRemoveRequest jutsuRemoveRequest
    ){
        return ResponseEntity.ok(jutsuService.removerJutsu(idPersonagem, jutsuRemoveRequest));
    }


}
