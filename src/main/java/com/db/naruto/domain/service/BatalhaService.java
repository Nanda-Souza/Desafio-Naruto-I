package com.db.naruto.domain.service;

import com.db.naruto.domain.dto.BatalhaRequest;
import com.db.naruto.domain.dto.BatalhaResponse;
import com.db.naruto.domain.entity.Personagem;
import com.db.naruto.domain.repository.PersonagemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BatalhaService {

    private final PersonagemRepository personagemRepository;

    public BatalhaService(PersonagemRepository personagemRepository) { this.personagemRepository = personagemRepository; }

    public BatalhaResponse batalhar(BatalhaRequest batalhaRequest) {
        Long idAtacante = batalhaRequest.idAtacante();
        Long idDefensor = batalhaRequest.idDefensor();


        Personagem atacante = personagemRepository.findById(idAtacante)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Personagem atacante com Id " + idAtacante + " não foi encontrado!")
                );

        Personagem defensor = personagemRepository.findById(idDefensor)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Personagem defensor com Id " + idDefensor + " não foi encontrado!")
                );

        System.out.println("Atacante: " + atacante.getNome());
        System.out.println("Defensor: " + defensor.getNome());

        String resultado = atacante.getNome() + " está atacando " + defensor.getNome();

        return new BatalhaResponse(resultado);

    }


}
