package com.db.naruto.domain.dto;

import com.db.naruto.domain.entity.Jutsu;

import java.util.Map;

public record JutsuRemoveResponse(
        Long id,
        String nome,
        Map<String, Jutsu> jutsus
) {
}
