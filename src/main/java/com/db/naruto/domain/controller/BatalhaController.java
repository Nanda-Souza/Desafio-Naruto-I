package com.db.naruto.domain.controller;

import com.db.naruto.domain.dto.BatalhaRequest;
import com.db.naruto.domain.dto.BatalhaResponse;
import com.db.naruto.domain.service.BatalhaService;
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

    @PostMapping()
    public ResponseEntity<BatalhaResponse> batalhaNinja(
            @RequestBody @Valid BatalhaRequest batalhaRequest
    ) {
        return ResponseEntity.ok(batalhaService.batalhar(batalhaRequest));
    }
}
