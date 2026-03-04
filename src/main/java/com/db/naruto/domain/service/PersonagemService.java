package com.db.naruto.domain.service;


import com.db.naruto.domain.dto.PersonagemRequest;
import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.dto.PersonagemUpdateRequest;
import com.db.naruto.domain.entity.*;
import com.db.naruto.domain.repository.PersonagemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonagemService {

    private final PersonagemRepository personagemRepository;

    public PersonagemService(PersonagemRepository personagemRepository) {this.personagemRepository = personagemRepository;}

    public List<PersonagemResponse> listarPersonagens(){

        return personagemRepository.findAll()
                .stream()
                .map( personagem -> new PersonagemResponse(
                        personagem.getId(),
                        personagem.getNome(),
                        personagem.getVida(),
                        personagem.getChakra(),
                        personagem.getJutsus()
                )).toList();
    }


    public PersonagemResponse salvarPersonagem(PersonagemRequest personagemRequest){

        Personagem ninja = switch (personagemRequest.tipoNinja()){
            case NINJUTSU -> new NinjaDeNinjutsu(personagemRequest.nome(), personagemRequest.vida());
            case GENJUTSU -> new NinjaDeGenjutsu(personagemRequest.nome(), personagemRequest.vida());
            case TAIJUTSU -> new NinjaDeTaijutsu(personagemRequest.nome(), personagemRequest.vida());
        };

        personagemRequest.jutsus().forEach((nomeJutsu, jutsuRequest) ->
                ninja.getJutsus().put(
                        nomeJutsu,
                        new Jutsus(
                                jutsuRequest.dano(),
                                jutsuRequest.consumoDeChakra()
                        )
                )
        );

        Personagem ninjaSalvo = personagemRepository.save(ninja);

        return new PersonagemResponse(
                ninjaSalvo.getId(),
                ninjaSalvo.getNome(),
                ninjaSalvo.getVida(),
                ninjaSalvo.getChakra(),
                ninjaSalvo.getJutsus()
        );
    }


    public void deletarPersonagem(Long id){

        Personagem personagem = personagemRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Personagem com Id " + id + " não foi encontrado!")
                );

        personagemRepository.delete(personagem);

    }

    public PersonagemResponse atualizarPersonagem(Long id, PersonagemUpdateRequest personagemUpdate){

        Personagem personagem = personagemRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Personagem com Id " + id + " não foi encontrado!")
                );

        if (personagemUpdate.nome() != null){
            personagem.setNome(personagemUpdate.nome().trim());
        }

        Integer vidaNinja = personagemUpdate.vida();
        if (vidaNinja != null){
            personagem.setVida(vidaNinja);
        }

        if (personagemUpdate.jutsus() != null) {

            personagemUpdate.jutsus().forEach((nomeJutsu, jutsuRequest) -> {

                personagem.getJutsus().put(
                        nomeJutsu,
                        new Jutsus(
                                jutsuRequest.dano(),
                                jutsuRequest.consumoDeChakra()
                        )
                );

            });
        }



        Personagem personagemAtualizado = personagemRepository.save(personagem);

        return new PersonagemResponse(
                personagemAtualizado.getId(),
                personagemAtualizado.getNome(),
                personagemAtualizado.getVida(),
                personagemAtualizado.getChakra(),
                personagemAtualizado.getJutsus()
        );

    }

    public PersonagemResponse buscarPersonagemPorId(Long id){
        Personagem personagem = personagemRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Personagem com Id " + id + " não encontrado!")
                );

    public PersonagemResponse buscarPersonagemPorId(Long id){
        Personagem personagem = personagemRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Personagem com Id " + id + " não encontrado!")
                );

        return new PersonagemResponse(
                personagem.getId(),
                personagem.getNome(),
                personagem.getVida(),
                personagem.getChakra(),
                personagem.getJutsus()
        );
    }

}
