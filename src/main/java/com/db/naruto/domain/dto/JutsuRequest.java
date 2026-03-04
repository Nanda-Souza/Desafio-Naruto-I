package com.db.naruto.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record JutsuRequest(
        @NotNull(message = "O jutsu deve ter dano!")
        @Positive(message = "O dano deve ser mair que zero!")
        Integer dano,

        @NotNull(message = "O jutsu deve ter consumo de chakra!")
        @Positive(message = "O consumo de chakra deve ser maior que zero!")
        Integer consumoDeChakra

) {}