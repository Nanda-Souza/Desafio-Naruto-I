package com.db.naruto.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PersonagemUpdateRequest(
        @Pattern(regexp = ".*\\S.*", message = "O nome do ninja não pode ser vazio!")
        String nome,

        @Min(value = 7, message = "Um ninja deve ter no mínimo 7 anos!")
        Integer idade,

        @Pattern(regexp = ".*\\S.*", message = "O nome da aldeia do ninja não pode ser vazio!")
        String aldeia,


        @Size(min = 1, message = "O ninja deve possuir ao menos um jutsu!")
        List<@Pattern(regexp = ".*\\S.*", message = "O nome do jutsu não pode ser vazio!") String> jutsus,

        @Positive(message = "O Chakra deve ser maior que zero!")
        Integer chakra
) {
}
