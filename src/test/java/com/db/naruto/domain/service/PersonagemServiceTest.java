package com.db.naruto.domain.service;

import com.db.naruto.domain.dto.PersonagemRequest;
import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.entity.NinjaDeNinjutsu;
import com.db.naruto.domain.repository.PersonagemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonagemServiceTest {
    @Mock
    private PersonagemRepository personagemRepository;

    @InjectMocks
    private PersonagemService personagemService;

    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Ninjutsu com sucesso!")
    void deveSalvarNinjaDeNinjutsuComSucesso(){

        List<String> jutsus = new ArrayList<>();
        jutsus.add("Técnica do Dragão de Fogo");

        PersonagemRequest request = new PersonagemRequest(
                "Sasuke Uchiha",
                12,
                "Konoha",
                jutsus,
                75
        );

        NinjaDeNinjutsu ninjaSalvo = new NinjaDeNinjutsu(
                request.nome(),
                request.idade(),
                request.aldeia(),
                request.jutsus(),
                request.idade()
        );

        when(personagemRepository.save(any(NinjaDeNinjutsu.class)))
                .thenReturn(ninjaSalvo);

        PersonagemResponse response = personagemService.salvarNinjaDeNinjutsu(request);

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Sasuke Uchiha", response.nome(), "Deve retornar Sasuke Uchiha!");

    }
}
