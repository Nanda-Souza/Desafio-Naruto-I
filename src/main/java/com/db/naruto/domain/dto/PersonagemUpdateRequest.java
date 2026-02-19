package com.db.naruto.domain.dto;

import java.util.List;

public record PersonagemUpdateRequest(
        String nome,
        int idade,
        String aldeia,
        List<String> jutsus,
        int chakra
) {
}
