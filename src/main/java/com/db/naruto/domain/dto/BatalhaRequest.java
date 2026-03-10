package com.db.naruto.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BatalhaRequest(
        @NotNull(message = "O id do ninja atacante deve ser fornecido!")
        @Positive(message = "O id do ninja deve ser maior que zero!")
        Long idAtacante,

        @NotBlank(message = "O nome do jutsu não pode ser vazio!")
        String nomeJutsu,

        @NotNull(message = "O id do ninja defensor deve ser fornecido!")
        @Positive(message = "O id do ninja deve ser maior que zero!")
        Long idDefensor
) {
}
