package com.db.naruto.domain.service;

import com.db.naruto.domain.dto.JutsuRequest;
import com.db.naruto.domain.dto.PersonagemRequest;
import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.dto.PersonagemUpdateRequest;
import com.db.naruto.domain.entity.*;
import com.db.naruto.domain.repository.PersonagemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonagemServiceTest {
    @Mock
    private PersonagemRepository personagemRepository;

    @InjectMocks
    private PersonagemService personagemService;

    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Ninjutsu com sucesso!")
    void deveSalvarNinjaDeNinjutsuComSucesso(){


        Map<String, JutsuRequest> jutsus = new HashMap<>();
        jutsus.put("Rasengan", new JutsuRequest(65, 40));


        PersonagemRequest request = new PersonagemRequest(
                "Naruto Uzumaki",
                110,
                TipoNinja.NINJUTSU,
                jutsus
        );

        NinjaDeNinjutsu ninjaSalvo = new NinjaDeNinjutsu(
                request.nome(),
                request.vida()
        );

        request.jutsus().forEach((nomeJutsu, jutsuRequest) ->
                ninjaSalvo.getJutsus().put(
                        nomeJutsu,
                        new Jutsus(
                                jutsuRequest.dano(),
                                jutsuRequest.consumoDeChakra()
                        )
                )
        );

        when(personagemRepository.save(any(NinjaDeNinjutsu.class)))
                .thenReturn(ninjaSalvo);

        PersonagemResponse response = personagemService.salvarPersonagem(request);

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Naruto Uzumaki", response.nome(), "Deve retornar Naruto Uzumaki!");
        assertTrue(response.jutsus().containsKey("Rasengan"), "Deve retornar o Jutsu Rasengan!");
        assertEquals(65, response.jutsus().get("Rasengan").getDano(), "Deve retornar 65 de dano!");
        assertEquals(40, response.jutsus().get("Rasengan").getConsumoDeChakra(), "Deve retornar 40 de consumo de chakra!");


    }

    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Taijutsu com sucesso!")
    void deveSalvarNinjaDeTaijutsuComSucesso(){


        Map<String, JutsuRequest> jutsus = new HashMap<>();
        jutsus.put("Oito Trigramas: Palma Rotativa", new JutsuRequest(35, 10));


        PersonagemRequest request = new PersonagemRequest(
                "Neji Hyuuga",
                130,
                TipoNinja.TAIJUTSU,
                jutsus
        );

        NinjaDeTaijutsu ninjaSalvo = new NinjaDeTaijutsu(
                request.nome(),
                request.vida()
        );

        request.jutsus().forEach((nomeJutsu, jutsuRequest) ->
                ninjaSalvo.getJutsus().put(
                        nomeJutsu,
                        new Jutsus(
                                jutsuRequest.dano(),
                                jutsuRequest.consumoDeChakra()
                        )
                )
        );

        when(personagemRepository.save(any(NinjaDeTaijutsu.class)))
                .thenReturn(ninjaSalvo);

        PersonagemResponse response = personagemService.salvarPersonagem(request);

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Neji Hyuuga", response.nome(), "Deve retornar Neji Hyuuga!");
        assertTrue(response.jutsus().containsKey("Oito Trigramas: Palma Rotativa"), "Deve retornar o Jutsu Oito Trigramas: Palma Rotativa!");
        assertEquals(35, response.jutsus().get("Oito Trigramas: Palma Rotativa").getDano(), "Deve retornar 35 de dano!");
        assertEquals(10, response.jutsus().get("Oito Trigramas: Palma Rotativa").getConsumoDeChakra(), "Deve retornar 10 de consumo de chakra!");


    }


    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Genjutsu com sucesso!")
    void deveSalvarNinjaDeGenjutsuComSucesso(){


        Map<String, JutsuRequest> jutsus = new HashMap<>();
        jutsus.put("Técnica da Captura pela Sombra", new JutsuRequest(25, 10));


        PersonagemRequest request = new PersonagemRequest(
                "Shikamaru Nara",
                115,
                TipoNinja.GENJUTSU,
                jutsus
        );

        NinjaDeGenjutsu ninjaSalvo = new NinjaDeGenjutsu(
                request.nome(),
                request.vida()
        );

        request.jutsus().forEach((nomeJutsu, jutsuRequest) ->
                ninjaSalvo.getJutsus().put(
                        nomeJutsu,
                        new Jutsus(
                                jutsuRequest.dano(),
                                jutsuRequest.consumoDeChakra()
                        )
                )
        );

        when(personagemRepository.save(any(NinjaDeGenjutsu.class)))
                .thenReturn(ninjaSalvo);

        PersonagemResponse response = personagemService.salvarPersonagem(request);

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Shikamaru Nara", response.nome(), "Deve retornar Shikamaru Nara!");
        assertTrue(response.jutsus().containsKey("Técnica da Captura pela Sombra"), "Deve retornar o Jutsu Técnica da Captura pela Sombra!");
        assertEquals(25, response.jutsus().get("Técnica da Captura pela Sombra").getDano(), "Deve retornar 25 de dano!");
        assertEquals(10, response.jutsus().get("Técnica da Captura pela Sombra").getConsumoDeChakra(), "Deve retornar 10 de consumo de chakra!");


    }

    @Test
    @DisplayName("Deve listar todos os personagens cadastrados!")
    void deveListarTodosOsPersonagensCadastrados(){

        Map<String, Jutsus> jutsusPersonagem1 = new HashMap<>();
        jutsusPersonagem1.put("Rasengan", new Jutsus(65, 40));

        NinjaDeNinjutsu personagem1 = new NinjaDeNinjutsu(
                "Naruto Uzumaki",
                110
        );

        personagem1.setJutsus(jutsusPersonagem1);

        Map<String, Jutsus> jutsusPersonagem2 = new HashMap<>();
        jutsusPersonagem2.put("Oito Trigramas: Palma Rotativa", new Jutsus(35, 10));

        NinjaDeTaijutsu personagem2 = new NinjaDeTaijutsu(
                "Neji Hyuuga",
                130
        );

        personagem2.setJutsus(jutsusPersonagem2);

        Map<String, Jutsus> jutsusPersonagem3 = new HashMap<>();
        jutsusPersonagem3.put("Técnica da Captura pela Sombra", new Jutsus(25, 10));

        NinjaDeGenjutsu personagem3 = new NinjaDeGenjutsu(
                "Shikamaru Nara",
                115
        );

        personagem3.setJutsus(jutsusPersonagem3);

        when(personagemRepository.findAll())
                .thenReturn(List.of(personagem1, personagem2, personagem3));

        List<PersonagemResponse> response = personagemService.listarPersonagens();

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals(3, response.size(), "Deve retornar uma lista com três personagens!");

        PersonagemResponse primeiroPersonagem = response.get(0);
        assertEquals("Naruto Uzumaki", primeiroPersonagem.nome(), "Deve retornar Naruto Uzumaki!");
        assertTrue(primeiroPersonagem.jutsus().containsKey("Rasengan"), "Deve retornar o Jutsu Rasengan!");
        assertEquals(65, primeiroPersonagem.jutsus().get("Rasengan").getDano(), "Deve retornar 65 de dano!");
        assertEquals(40, primeiroPersonagem.jutsus().get("Rasengan").getConsumoDeChakra(), "Deve retornar 40 de consumo de chakra!");

        PersonagemResponse segundoPersonagem = response.get(1);
        assertEquals("Neji Hyuuga", segundoPersonagem.nome(), "Deve retornar Neji Hyuuga!");
        assertTrue(segundoPersonagem.jutsus().containsKey("Oito Trigramas: Palma Rotativa"), "Deve retornar o Jutsu Oito Trigramas: Palma Rotativa!");
        assertEquals(35, segundoPersonagem.jutsus().get("Oito Trigramas: Palma Rotativa").getDano(), "Deve retornar 35 de dano!");
        assertEquals(10, segundoPersonagem.jutsus().get("Oito Trigramas: Palma Rotativa").getConsumoDeChakra(), "Deve retornar 10 de consumo de chakra!");

        PersonagemResponse terceiroPersonagem = response.get(2);
        assertEquals("Shikamaru Nara", terceiroPersonagem.nome(), "Deve retornar Shikamaru Nara!");
        assertTrue(terceiroPersonagem.jutsus().containsKey("Técnica da Captura pela Sombra"), "Deve retornar o Jutsu Técnica da Captura pela Sombra!");
        assertEquals(25, terceiroPersonagem.jutsus().get("Técnica da Captura pela Sombra").getDano(), "Deve retornar 25 de dano!");
        assertEquals(10, terceiroPersonagem.jutsus().get("Técnica da Captura pela Sombra").getConsumoDeChakra(), "Deve retornar 10 de consumo de chakra!");

        verify(personagemRepository, times(1)).findAll();

    }

    @Test
    @DisplayName("Deve deletar um personagem com sucesso!")
    void deveDeletarPersonagemComSucesso(){

        Long id = 1L;

        Map<String, Jutsus> jutsusPersonagem = new HashMap<>();
        jutsusPersonagem.put("Rasengan", new Jutsus(65, 40));

        NinjaDeNinjutsu personagem = new NinjaDeNinjutsu(
                "Naruto Uzumaki",
                110
        );

        personagem.setJutsus(jutsusPersonagem);

        when(personagemRepository.findById(id))
                .thenReturn(Optional.of(personagem));

        personagemService.deletarPersonagem(id);

        verify(personagemRepository).findById(id);
        verify(personagemRepository).delete(personagem);

    }

    @Test
    @DisplayName("Deve lançar exceção 404 ao tentar deletar um personagem inexistente!")
    void deveLancarExcecaoAoDeletarPersonagemInexistente(){

        Long id = 1L;

        when(personagemRepository.findById(id))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> personagemService.deletarPersonagem(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Deve retornar status code 404!");
        assertEquals("Personagem com Id 1 não foi encontrado!", exception.getReason(), "Deve retornar personagem com Id 1 não foi encontrado!");

        verify(personagemRepository).findById(id);
        verify(personagemRepository, never()).delete(any());

    }

    @Test
    @DisplayName("Deve atualizar o personagem com sucesso!")
    void deveAtualizarPersonagemComSucesso(){

        Long id = 1L;

        Map<String, Jutsus> jutsusPersonagem = new HashMap<>();
        jutsusPersonagem.put("Rasengan", new Jutsus(6, 4));

        NinjaDeNinjutsu personagem = new NinjaDeNinjutsu(
                "Naruto",
                10
        );

        personagem.setJutsus(jutsusPersonagem);

        Map<String, JutsuRequest> jutsusPersonagemAtualizado = new HashMap<>();
        jutsusPersonagemAtualizado.put("Rasengan", new JutsuRequest(65, 40));

        PersonagemUpdateRequest updateRequest = new PersonagemUpdateRequest(
                "Naruto Uzumaki",
                110,
                jutsusPersonagemAtualizado
        );

        when(personagemRepository.findById(id))
                .thenReturn(Optional.of(personagem));

        when(personagemRepository.save(any(Personagem.class)))
                .thenAnswer(i -> i.getArgument(0));

        PersonagemResponse response = personagemService.atualizarPersonagem(id, updateRequest);

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Naruto Uzumaki", response.nome(), "Deve retornar Naruto Uzumaki!");
        assertTrue(response.jutsus().containsKey("Rasengan"), "Deve retornar o Jutsu Rasengan!");
        assertEquals(65, response.jutsus().get("Rasengan").getDano(), "Deve retornar 65 de dano!");
        assertEquals(40, response.jutsus().get("Rasengan").getConsumoDeChakra(), "Deve retornar 40 de consumo de chakra!");

        verify(personagemRepository).findById(id);
        verify(personagemRepository).save(any(Personagem.class));


    }

    @Test
    @DisplayName("Deve lançar exceção 404 ao tentar atualizar um personagem com inexistente!")
    void deveLancarExcecaoAoAtualizarPersonagemInexistente(){

        Long id = 1L;

        Map<String, JutsuRequest> jutsusPersonagemAtualizado = new HashMap<>();
        jutsusPersonagemAtualizado.put("Rasengan", new JutsuRequest(65, 40));

        PersonagemUpdateRequest updateRequest = new PersonagemUpdateRequest(
                "Naruto Uzumaki",
                110,
                jutsusPersonagemAtualizado
        );

        when(personagemRepository.findById(id))
                .thenReturn(Optional.empty());


        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> personagemService.atualizarPersonagem(id, updateRequest)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Deve retornar statu code 404!");
        assertEquals("Personagem com Id 1 não foi encontrado!", exception.getReason(), "Deve retornar personagem com o id 1 não foi encontrado!");

        verify(personagemRepository).findById(id);
        verify(personagemRepository, never()).save(any());


    }

    @Test
    @DisplayName("Deve retornar o personagem ao buscar por id com sucesso!")
    void deveRetornarPersonagemPorIdComSucesso() {
        Long id = 1L;

        Map<String, Jutsus> jutsusPersonagem = new HashMap<>();
        jutsusPersonagem.put("Rasengan", new Jutsus(65, 40));

        NinjaDeNinjutsu personagem = new NinjaDeNinjutsu(
                "Naruto Uzumaki",
                110
        );

        personagem.setJutsus(jutsusPersonagem);

        when(personagemRepository.findById(id)).thenReturn(Optional.of(personagem));

        PersonagemResponse response = personagemService.buscarPersonagemPorId(id);

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Naruto Uzumaki", response.nome(), "Deve retornar Naruto Uzumaki!");
        assertTrue(response.jutsus().containsKey("Rasengan"), "Deve retornar o Jutsu Rasengan!");
        assertEquals(65, response.jutsus().get("Rasengan").getDano(), "Deve retornar 65 de dano!");
        assertEquals(40, response.jutsus().get("Rasengan").getConsumoDeChakra(), "Deve retornar 40 de consumo de chakra!");

        verify(personagemRepository).findById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção 404 ao buscar personagem inexistente por id!")
    void deveLancarExcecaoQuandoNaoEncontrarPersonagemPorId() {
        Long id = 1L;

        when(personagemRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> personagemService.buscarPersonagemPorId(id)
        );

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode(), "Deve retornar status code 404!");
        assertEquals("Personagem com Id 1 não encontrado!", ex.getReason(),"Deve retornar mensagem de não encontrado!");

        verify(personagemRepository).findById(id);
        verify(personagemRepository, never()).save(any());
    }

}
