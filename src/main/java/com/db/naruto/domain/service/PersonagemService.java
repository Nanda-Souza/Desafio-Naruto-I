package com.db.naruto.domain.service;


import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.repository.PersonagemRepository;
import org.springframework.stereotype.Service;

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
                        personagem.getIdade(),
                        personagem.getAldeia(),
                        personagem.getJutsus(),
                        personagem.getChakra()
                )).toList();
    }


}
