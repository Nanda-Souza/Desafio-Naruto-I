package com.db.naruto.domain.service;

import com.db.naruto.domain.dto.JutsuRemoveRequest;
import com.db.naruto.domain.dto.JutsuRemoveResponse;
import com.db.naruto.domain.entity.Personagem;
import com.db.naruto.domain.repository.PersonagemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JutsuService {

    private final PersonagemRepository personagemRepository;

    public JutsuService(PersonagemRepository personagemRepository) {this.personagemRepository = personagemRepository;}

    public JutsuRemoveResponse removerJutsu(Long id, JutsuRemoveRequest jutsuRequest) {
        Personagem personagem = personagemRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Personagem com Id " + id + " não foi encontrado!")
                );

        if (!personagem.getJutsus().containsKey(jutsuRequest.nomeJutsu())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Jutsu não encontrado!");
        }

        if (personagem.getJutsus().size() == 1) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Não foi possível remover o jutsu pois o ninja deve possuir ao menos um jutsu!");
        }

        personagem.getJutsus().remove(jutsuRequest.nomeJutsu());

        Personagem ninjaSalvo = personagemRepository.save(personagem);

        return new JutsuRemoveResponse(
                ninjaSalvo.getId(),
                ninjaSalvo.getNome(),
                ninjaSalvo.getJutsus()
        );
    }

}
