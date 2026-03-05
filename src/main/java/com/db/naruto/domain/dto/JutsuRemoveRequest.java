package com.db.naruto.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record JutsuRemoveRequest(
        @NotBlank(message = "O nome do jutsu não pode ser vazio!")
        String nomeJutsu
) {
}
