package com.db.naruto.domain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Map;

public record PersonagemRequest(

        @NotBlank(message = "O Nome do Ninja é obrigatório!")
        String nome,

        @NotNull(message = "O ninja deve ter um valor para vida!")
        @Positive(message = "O ninja deve ter vida maior que zero!")
        Integer vida,

        @NotNull(message = "O ninja deve possuir ao menos um jutsu!")
        @Size(min = 1, message = "O ninja deve possuir ao menos um jutsu!")
        Map<
                @NotBlank(message = "O nome do jutsu não pode ser vazio!")
                        String,
                @Valid
                @NotNull(message = "Os dados do jutsu são obrigatórios!")
                        JutsuRequest
                > jutsus

) {
}
