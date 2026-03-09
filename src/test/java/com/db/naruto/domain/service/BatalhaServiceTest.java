package com.db.naruto.domain.service;


import com.db.naruto.domain.dto.BatalhaRequest;
import com.db.naruto.domain.dto.BatalhaResponse;
import com.db.naruto.domain.entity.Jutsu;
import com.db.naruto.domain.entity.NinjaDeNinjutsu;
import com.db.naruto.domain.entity.NinjaDeTaijutsu;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatalhaServiceTest {

    @Mock
    private PersonagemRepository personagemRepository;

    @InjectMocks
    private BatalhaService batalhaService;

    @Test
    @DisplayName("Deve lançar exceção 404 quando personagem atacante não é encontrado!")
    void deveLancarExcecaoQuandoAtacanteNaoExiste() {
        Long idAtacante = 1L;
        Long idDefensor = 2L;
        when(personagemRepository.findById(idAtacante)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> batalhaService.batalhar(new BatalhaRequest(idAtacante,"Rasengan", idDefensor ))
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Deve retornar status code 404!");
        assertEquals("Personagem atacante com Id 1 não foi encontrado!", exception.getReason(), "Deve retornar que o personagem atacante com Id 1 não foi encontrado!");

        verify(personagemRepository).findById(idAtacante);

    }

    @Test
    @DisplayName("Deve lançar exceção 404 quando personagem defensor não é encontrado!")
    void deveLancarExcecaoQuandoDefensorNaoExiste() {
        Long idAtacante = 1L;
        Long idDefensor = 2L;

        Personagem atacante = mock(Personagem.class);
        when(personagemRepository.findById(idAtacante)).thenReturn(Optional.of(atacante));
        when(personagemRepository.findById(idDefensor)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> batalhaService.batalhar(new BatalhaRequest(idAtacante,"Rasengan", idDefensor ))
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Deve retornar status code 404!");
        assertEquals("Personagem defensor com Id 2 não foi encontrado!", exception.getReason(), "Deve retornar que o personagem defensor com Id 2 não foi encontrado!");

        verify(personagemRepository).findById(idDefensor);

    }

    @Test
    @DisplayName("Deve lançar exceção 404 quando jutsu do atacante não é encontrado no Map")
    void deveLancarExcecaoQuandoJutsuDoAtacanteNaoEncontrado() {
        Long idAtacante = 1L;
        Long idDefensor = 2L;

        Personagem atacante = mock(Personagem.class);
        Personagem defensor = mock(Personagem.class);

        when(personagemRepository.findById(idAtacante)).thenReturn(Optional.of(atacante));
        when(personagemRepository.findById(idDefensor)).thenReturn(Optional.of(defensor));

        when(atacante.getJutsus()).thenReturn(new HashMap<>());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> batalhaService.batalhar(new BatalhaRequest(idAtacante, "Contra", idDefensor ))
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Deve retornar status code 404!");
        assertEquals("Jutsu do personagem atacante não encontrado!", exception.getReason(), "Deve retornar que o futsu do personagem atacante não encontrado!");
    }

    @Test
    @DisplayName("Deve lançar exceção 422 quando atacante está derrotado ou não pode atacar")
    void atacanteDerrotadoOuSemChakraParaQualquerJutsu() {
        Long idAtacante = 1L;
        Long idDefensor = 2L;
        String nomeJutsu = "Rasengan";

        Personagem atacante = mock(Personagem.class);
        Personagem defensor = mock(Personagem.class);

        Map<String, Jutsu> jutsusAtacante = new HashMap<>();
        jutsusAtacante.put(nomeJutsu, new Jutsu(10, 5));

        when(personagemRepository.findById(idAtacante)).thenReturn(Optional.of(atacante));
        when(personagemRepository.findById(idDefensor)).thenReturn(Optional.of(defensor));

        when(atacante.getJutsus()).thenReturn(jutsusAtacante);
        when(atacante.foiDerrotado()).thenReturn(false);
        when(atacante.podeAtacar()).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> batalhaService.batalhar(new BatalhaRequest(idAtacante, nomeJutsu, idDefensor))
        );

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode(), "Deve retornar status code 422!");
        assertEquals("O personagem atacante já foi derrotado ou não tem mais chakra para atacar com nenhum jutsu!", exception.getReason(), "Deve retornar que o personagem atacante já foi derrotado!");
    }

    @Test
    @DisplayName("Deve lançar exceção 422 quando defensor está derrotado ou não pode atacar")
    void defensorDerrotadoOuSemChakraParaQualquerJutsu() {
        Long idAtacante = 1L;
        Long idDefensor = 2L;
        String nomeJutsu = "Rasengan";

        Personagem atacante = mock(Personagem.class);
        Personagem defensor = mock(Personagem.class);

        Map<String, Jutsu> jutsusAtacante = new HashMap<>();
        jutsusAtacante.put(nomeJutsu, new Jutsu(10, 5));

        when(personagemRepository.findById(idAtacante)).thenReturn(Optional.of(atacante));
        when(personagemRepository.findById(idDefensor)).thenReturn(Optional.of(defensor));

        when(atacante.getJutsus()).thenReturn(jutsusAtacante);
        when(atacante.foiDerrotado()).thenReturn(false);
        when(atacante.podeAtacar()).thenReturn(true);

        when(defensor.foiDerrotado()).thenReturn(false);
        when(defensor.podeAtacar()).thenReturn(false);

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> batalhaService.batalhar(new BatalhaRequest(idAtacante, nomeJutsu, idDefensor))
        );

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, exception.getStatusCode(), "Deve retornar status code 422!");
        assertEquals("O personagem defensor já foi derrotado ou não tem mais chakra para atacar com nenhum jutsu!", exception.getReason(), "Deve retornar que o personagem defensor já foi derrotado!");
    }

    @Test
    @DisplayName("Não deve causar dano/consumir chakra se o atacante não tem chakra suficiente para usar o jutsu escolhido quando ele tem outro disponivel que possa usar!")
    void atacanteSemChakraSuficienteParaJutsu() {
        Long idAtacante = 1L;
        Long idDefensor = 2L;
        String nomeJutsu = "Rasengan";

        Personagem atacante = mock(Personagem.class);
        Personagem defensor = mock(Personagem.class);

        Map<String, Jutsu> jutsusAtacante = new HashMap<>();
        jutsusAtacante.put(nomeJutsu, new Jutsu(80, 50));

        when(personagemRepository.findById(idAtacante)).thenReturn(Optional.of(atacante));
        when(personagemRepository.findById(idDefensor)).thenReturn(Optional.of(defensor));

        when(atacante.getNome()).thenReturn("Naruto Uzumaki");
        when(atacante.getJutsus()).thenReturn(jutsusAtacante);
        when(atacante.getChakra()).thenReturn(30);

        when(atacante.foiDerrotado()).thenReturn(false);
        when(atacante.podeAtacar()).thenReturn(true);
        when(defensor.foiDerrotado()).thenReturn(false);
        when(defensor.podeAtacar()).thenReturn(true);

        BatalhaResponse response = batalhaService.batalhar(new BatalhaRequest(idAtacante, nomeJutsu, idDefensor));

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Naruto Uzumaki não tem chakra o suficiente para usar Rasengan tente usar outro jutsu!", response.resultado(), "Deve retornar que Naruto não tem chakra para usar o jutsu e deve tentar outro jutsu!");

        verify(atacante, never()).gastarChakra(anyInt());
        verify(defensor, never()).levarDano(anyInt());
        verify(personagemRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve consumir chakra e não deve causar dano se o defensor se desvia!")
    void defensorDesvia() {
        Long idAtacante = 1L;
        Long idDefensor = 2L;
        String nomeJutsu = "Chidori";

        NinjaDeNinjutsu atacante = mock(NinjaDeNinjutsu.class);
        NinjaDeTaijutsu defensor = mock(NinjaDeTaijutsu.class);

        Map<String, Jutsu> jutsusAtacante = new HashMap<>();
        jutsusAtacante.put(nomeJutsu, new Jutsu(60, 35));

        when(personagemRepository.findById(idAtacante)).thenReturn(Optional.of(atacante));
        when(personagemRepository.findById(idDefensor)).thenReturn(Optional.of(defensor));

        when(defensor.getNome()).thenReturn("Rock Lee");

        when(atacante.getJutsus()).thenReturn(jutsusAtacante);
        when(atacante.getChakra()).thenReturn(100);
        when(atacante.foiDerrotado()).thenReturn(false);
        when(atacante.podeAtacar()).thenReturn(true);

        when(defensor.foiDerrotado()).thenReturn(false);
        when(defensor.podeAtacar()).thenReturn(true);
        when(defensor.conseguiuDesviar()).thenReturn(true);

        BatalhaResponse response = batalhaService.batalhar(new BatalhaRequest(idAtacante, nomeJutsu, idDefensor));

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Rock Lee se desviou do jutsu Chidori!", response.resultado(), "Deve retornar que o Rock Lee se desviou do Chidori!");

        verify(atacante).usarJutsu();
        verify(defensor).desviar();

        verify(atacante).gastarChakra(35);
        verify(personagemRepository).save(atacante);
        verify(defensor, never()).levarDano(anyInt());
        verify(personagemRepository, never()).save(defensor);
    }

    @Test
    @DisplayName("Deve consumir chakra, causa dano e retorna mensagem de ataque ao acertar!")
    void ataqueSemDesvioComSucesso() {
        Long idAtacante = 1L;
        Long idDefensor = 2L;
        String nomeJutsu = "Rasengan";

        NinjaDeNinjutsu atacante = mock(NinjaDeNinjutsu.class);
        NinjaDeTaijutsu defensor = mock(NinjaDeTaijutsu.class);

        Map<String, Jutsu> jutsusAtacante = new HashMap<>();
        jutsusAtacante.put(nomeJutsu, new Jutsu(60, 30));

        when(personagemRepository.findById(idAtacante)).thenReturn(Optional.of(atacante));
        when(personagemRepository.findById(idDefensor)).thenReturn(Optional.of(defensor));

        when(atacante.getNome()).thenReturn("Naruto Uzumaki");
        when(defensor.getNome()).thenReturn("Sasuke Uchiha");

        when(atacante.getJutsus()).thenReturn(jutsusAtacante);
        when(atacante.getChakra()).thenReturn(100);
        when(atacante.foiDerrotado()).thenReturn(false);
        when(atacante.podeAtacar()).thenReturn(true);

        when(defensor.foiDerrotado()).thenReturn(false);
        when(defensor.podeAtacar()).thenReturn(true);
        when(defensor.conseguiuDesviar()).thenReturn(false);

        BatalhaResponse response = batalhaService.batalhar(new BatalhaRequest(idAtacante, nomeJutsu, idDefensor));

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Naruto Uzumaki está atacando Sasuke Uchiha com Rasengan!", response.resultado(), "Deve retornar que Naruto está atacando Sasuke com o Rasengan!");

        verify(atacante).usarJutsu();
        verify(atacante).gastarChakra(30);
        verify(personagemRepository).save(atacante);
        verify(defensor).levarDano(60);
        verify(personagemRepository).save(defensor);
    }

    @Test
    @DisplayName("Defensor é derrotado após receber dano")
    void defensorDerrotadoAposDano() {
        Long idAtacante = 1L;
        Long idDefensor = 2L;
        String nomeJutsu = "Técnica da Grande Bola de Fogo";

        NinjaDeNinjutsu atacante = mock(NinjaDeNinjutsu.class);
        NinjaDeTaijutsu defensor = mock(NinjaDeTaijutsu.class);

        Map<String, Jutsu> jutsusAtacante = new HashMap<>();
        jutsusAtacante.put(nomeJutsu, new Jutsu(60, 35));

        when(personagemRepository.findById(idAtacante)).thenReturn(Optional.of(atacante));
        when(personagemRepository.findById(idDefensor)).thenReturn(Optional.of(defensor));

        when(atacante.getNome()).thenReturn("Sasuke Uchiha");
        when(defensor.getNome()).thenReturn("Neji Hyuuga");

        when(atacante.getJutsus()).thenReturn(jutsusAtacante);
        when(atacante.getChakra()).thenReturn(100);
        when(atacante.foiDerrotado()).thenReturn(false);
        when(atacante.podeAtacar()).thenReturn(true);

        when(defensor.foiDerrotado()).thenReturn(false, true);
        when(defensor.podeAtacar()).thenReturn(true);
        when(defensor.conseguiuDesviar()).thenReturn(false);

        BatalhaResponse response = batalhaService.batalhar(new BatalhaRequest(idAtacante, nomeJutsu, idDefensor));

        assertNotNull(response, "O retorno não pode ser nulo!");
        assertEquals("Neji Hyuuga foi recebeu o jutsu Técnica da Grande Bola de Fogo e foi derrotado(a) por Sasuke Uchiha!", response.resultado(), "Deve retornar que Neji recebeu o jutsu de Bola de fogo e foi derrotado por Sasuke!");

        verify(atacante).usarJutsu();
        verify(atacante).gastarChakra(35);
        verify(personagemRepository).save(atacante);

        verify(defensor).levarDano(60);
        verify(personagemRepository).save(defensor);
    }

}
