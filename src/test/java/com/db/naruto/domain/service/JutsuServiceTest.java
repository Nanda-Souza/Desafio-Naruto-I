package com.db.naruto.domain.service;

import com.db.naruto.domain.dto.JutsuRemoveRequest;
import com.db.naruto.domain.dto.JutsuRemoveResponse;
import com.db.naruto.domain.entity.Jutsu;
import com.db.naruto.domain.entity.Personagem;
import com.db.naruto.domain.repository.PersonagemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JutsuServiceTest {
    @Mock
    private PersonagemRepository personagemRepository;

    @InjectMocks
    private JutsuService jutsuService;


    @Test
    @DisplayName("Deve remover o jutsu com sucesso!")
    void deveRemoverJutsuComSucesso() {
        Long id = 1L;

        Personagem personagem = new Personagem("Naruto Uzumaki", 110);
        personagem.setId(id);
        Map<String, Jutsu> jutsus = new HashMap<>();
        jutsus.put("Rasengan", new Jutsu(80, 40));
        jutsus.put("Chidori", new Jutsu(85, 50));
        personagem.setJutsus(jutsus);

        when(personagemRepository.findById(id)).thenReturn(Optional.of(personagem));
        when(personagemRepository.save(any(Personagem.class)))
                .thenAnswer(inv -> inv.getArgument(0));

        JutsuRemoveRequest request = new JutsuRemoveRequest("Chidori");


        JutsuRemoveResponse response = jutsuService.removerJutsu(id, request);


        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals(id, response.id(), "Deve retornar o id do personagem!");
        assertEquals("Naruto Uzumaki", response.nome(), "Deve retornar Naruto Uzumaki!");
        assertTrue(response.jutsus().containsKey("Rasengan"), "Rasengan deve permanecer!");
        assertFalse(response.jutsus().containsKey("Chidori"), "Chidori deve ser removido!");

        verify(personagemRepository, times(1)).findById(id);
        verify(personagemRepository, times(1)).save(personagem);
    }


    @Test
    @DisplayName("Não deve remover o único jutsu!")
    void deveLancarExecaoAoTentarRemoverUnicoJutsu() {
        Long id = 1L;

        Personagem personagem = new Personagem("Rock Lee", 140);
        personagem.setId(id);
        Map<String, Jutsu> jutsus = new HashMap<>();
        jutsus.put("Furacão da Folha", new Jutsu(30, 15));
        personagem.setJutsus(jutsus);

        when(personagemRepository.findById(id)).thenReturn(Optional.of(personagem));

        JutsuRemoveRequest request = new JutsuRemoveRequest("Furacão da Folha");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> jutsuService.removerJutsu(id, request));

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode(),"Deve retornar status code 422!");
        assertEquals("Não foi possível remover o jutsu pois o ninja deve possuir ao menos um jutsu!", exception.getReason(), "Deve retornar que não foi possível remover o jutsu pois o ninja deve possuir ao menos um jutsu!");

        verify(personagemRepository, times(1)).findById(id);
        verify(personagemRepository, never()).save(any());

    }


    @Test
    @DisplayName("Deve lançar exceção 404 ao tentar remover jutsu inexistente")
    void deveLancarExcecaoQuandoJutsuNaoExiste() {
        Long id = 1L;

        Personagem personagem = new Personagem("Sakura Haruno", 115);
        personagem.setId(id);
        Map<String, Jutsu> jutsus = new HashMap<>();
        jutsus.put("Contra-Genjutsu", new Jutsu(10, 10));
        personagem.setJutsus(jutsus);

        when(personagemRepository.findById(id)).thenReturn(Optional.of(personagem));

        JutsuRemoveRequest request = new JutsuRemoveRequest("Contra");


        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> jutsuService.removerJutsu(id, request));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Deve retornar status code 404!");
        assertEquals("Jutsu não encontrado!", exception.getReason(), "Deve retornar que o Jutsu não encontrado!");

        verify(personagemRepository, times(1)).findById(id);
        verify(personagemRepository, never()).save(any());
    }
}

