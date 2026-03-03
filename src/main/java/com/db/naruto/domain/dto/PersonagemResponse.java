package com.db.naruto.domain.dto;

import com.db.naruto.domain.entity.Jutsus;

import java.util.Map;

public record PersonagemResponse(
        Long id,
        String nome,
        int vida,
        int chakra,
        Map<String, Jutsus> jutsus
) {
}
