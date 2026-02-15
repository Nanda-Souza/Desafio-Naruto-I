package com.db.naruto.domain.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record PersonagemRequest(

        @NotBlank(message = "O Nome do Ninja é obrigatório!")
        String nome,

        @Min(value = 1, message = "Um ninja deve ter no mínimo 7 anos!")
        int idade,

        @NotBlank(message = "O Nome da Aldeia é obrigatório!")
        String aldeia,

        @NotNull(message = "O ninja deve possuir deve possuir ao menos um jutsu!")
        @Size(min = 1, message = "O ninja deve possuir deve possuir ao menos um jutsu!")
        List<String> jutsus,

        @Positive(message = "O Chakra deve ser maior que zero!")
        int chakra

) {
}
