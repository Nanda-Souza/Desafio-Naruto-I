package com.db.naruto.domain.service;

import com.db.naruto.domain.dto.PersonagemRequest;
import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.entity.NinjaDeGenjutsu;
import com.db.naruto.domain.entity.NinjaDeNinjutsu;
import com.db.naruto.domain.entity.NinjaDeTaijutsu;
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

    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Taijutsu com sucesso!")
    void deveSalvarNinjaDeTaijutsuComSucesso(){

        List<String> jutsus = new ArrayList<>();
        jutsus.add("Furacão da Folha");

        PersonagemRequest request = new PersonagemRequest(
                "Rock Lee",
                12,
                "Konoha",
                jutsus,
                20
        );

        NinjaDeTaijutsu ninjaSalvo = new NinjaDeTaijutsu(
                request.nome(),
                request.idade(),
                request.aldeia(),
                request.jutsus(),
                request.idade()
        );

        when(personagemRepository.save(any(NinjaDeTaijutsu.class)))
                .thenReturn(ninjaSalvo);

        PersonagemResponse response = personagemService.salvarNinjaDeTaijutsu(request);

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Rock Lee", response.nome(), "Deve retornar Rock Lee!");

    }

    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Genjutsu com sucesso!")
    void deveSalvarNinjaDeGenjutsuComSucesso(){

        List<String> jutsus = new ArrayList<>();
        jutsus.add("Técnica de Transferência de Mente");

        PersonagemRequest request = new PersonagemRequest(
                "Ino Yamanaka",
                12,
                "Konoha",
                jutsus,
                60
        );

        NinjaDeGenjutsu ninjaSalvo = new NinjaDeGenjutsu(
                request.nome(),
                request.idade(),
                request.aldeia(),
                request.jutsus(),
                request.idade()
        );

        when(personagemRepository.save(any(NinjaDeGenjutsu.class)))
                .thenReturn(ninjaSalvo);

        PersonagemResponse response = personagemService.salvarNinjaDeGenjutsu(request);

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Ino Yamanaka", response.nome(), "Deve retornar Ino Yamanaka!");

    }

    @Test
    @DisplayName("Deve listar todos os personagens cadastrados!")
    void deveListarTodosOsPersonagensCadastrados(){

        List<String> jutsusSasuke = new ArrayList<>();
        jutsusSasuke.add("Técnica do Dragão de Fogo");

        NinjaDeNinjutsu sasuke = new NinjaDeNinjutsu(
                "Sasuke Uchiha",
                12,
                "Konoha",
                jutsusSasuke,
                75
        );

        List<String> jutsusRockLee = new ArrayList<>();
        jutsusRockLee.add("Furacão da Folha");

        NinjaDeTaijutsu rockLee = new NinjaDeTaijutsu(
                "Rock Lee",
                12,
                "Konoha",
                jutsusRockLee,
                20
        );

        List<String> jutsusInoYamanaka = new ArrayList<>();
        jutsusInoYamanaka.add("Técnica de Transferência de Mente");

        NinjaDeGenjutsu inoYamanaka = new NinjaDeGenjutsu(
                "Ino Yamanaka",
                12,
                "Konoha",
                jutsusInoYamanaka,
                60
        );

        when(personagemRepository.findAll())
                .thenReturn(List.of(sasuke, rockLee, inoYamanaka));

        List<PersonagemResponse> response = personagemService.listarPersonagens();

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals(3, response.size(), "Deve retornar uma lista com três personagens!");

        PersonagemResponse primeiroPersonagem = response.get(0);
        assertEquals("Sasuke Uchiha", primeiroPersonagem.nome(), "Deve retornar Sasuke Uchiha!");

        PersonagemResponse segundoPersonagem = response.get(1);
        assertEquals("Rock Lee", segundoPersonagem.nome(), "Deve retornar Rock Lee!");

        PersonagemResponse terceiroPersonagem = response.get(2);
        assertEquals("Ino Yamanaka", terceiroPersonagem.nome(), "Deve retornar Ino Yamanak!");

        verify(personagemRepository, times(1)).findAll();

    }

}
