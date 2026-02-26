package com.db.naruto.domain.service;


import com.db.naruto.domain.dto.PersonagemRequest;
import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.dto.PersonagemUpdateRequest;
import com.db.naruto.domain.entity.NinjaDeGenjutsu;
import com.db.naruto.domain.entity.NinjaDeNinjutsu;
import com.db.naruto.domain.entity.NinjaDeTaijutsu;
import com.db.naruto.domain.entity.Personagem;
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
                        personagem.getIdade(),
                        personagem.getAldeia(),
                        personagem.getJutsus(),
                        personagem.getChakra()
                )).toList();
    }

    public PersonagemResponse salvarNinjaDeNinjutsu(PersonagemRequest personagemRequest){

        NinjaDeNinjutsu ninja = new NinjaDeNinjutsu(
                personagemRequest.nome(),
                personagemRequest.idade(),
                personagemRequest.aldeia(),
                personagemRequest.jutsus(),
                personagemRequest.chakra()
        );

        NinjaDeNinjutsu ninjaSalvo = personagemRepository.save(ninja);

        return new PersonagemResponse(
                ninjaSalvo.getId(),
                ninjaSalvo.getNome(),
                ninjaSalvo.getIdade(),
                ninjaSalvo.getAldeia(),
                ninjaSalvo.getJutsus(),
                ninjaSalvo.getChakra()
        );
    }

    public PersonagemResponse salvarNinjaDeTaijutsu(PersonagemRequest personagemRequest){

        NinjaDeTaijutsu ninja = new NinjaDeTaijutsu(
                personagemRequest.nome(),
                personagemRequest.idade(),
                personagemRequest.aldeia(),
                personagemRequest.jutsus(),
                personagemRequest.chakra()
        );

        NinjaDeTaijutsu ninjaSalvo = personagemRepository.save(ninja);

        return new PersonagemResponse(
                ninjaSalvo.getId(),
                ninjaSalvo.getNome(),
                ninjaSalvo.getIdade(),
                ninjaSalvo.getAldeia(),
                ninjaSalvo.getJutsus(),
                ninjaSalvo.getChakra()
        );
    }

    public PersonagemResponse salvarNinjaDeGenjutsu(PersonagemRequest personagemRequest){

        NinjaDeGenjutsu ninja = new NinjaDeGenjutsu(
                personagemRequest.nome(),
                personagemRequest.idade(),
                personagemRequest.aldeia(),
                personagemRequest.jutsus(),
                personagemRequest.chakra()
        );

        NinjaDeGenjutsu ninjaSalvo = personagemRepository.save(ninja);

        return new PersonagemResponse(
                ninjaSalvo.getId(),
                ninjaSalvo.getNome(),
                ninjaSalvo.getIdade(),
                ninjaSalvo.getAldeia(),
                ninjaSalvo.getJutsus(),
                ninjaSalvo.getChakra()
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

        Integer idadeNinja = personagemUpdate.idade();
        if (idadeNinja != null){
            personagem.setIdade(idadeNinja);
        }

        if (personagemUpdate.aldeia() != null){
            personagem.setAldeia(personagemUpdate.aldeia().trim());
        }

        if  (personagemUpdate.jutsus() != null){
            personagem.setJutsus(personagemUpdate.jutsus());
        }



        Integer chakraNinja = personagemUpdate.chakra();
        if (chakraNinja != null){
            personagem.setChakra(chakraNinja);
        }

        Personagem personagemAtualizado = personagemRepository.save(personagem);

        return new PersonagemResponse(
                personagemAtualizado.getId(),
                personagemAtualizado.getNome(),
                personagemAtualizado.getIdade(),
                personagemAtualizado.getAldeia(),
                personagemAtualizado.getJutsus(),
                personagemAtualizado.getChakra()
        );

    }

    public PersonagemResponse buscarPersonagemPorId(Long id){
        Personagem personagem = personagemRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Personagem com Id " + id + " não encontrado!")
                );

        return new PersonagemResponse(
                personagem.getId(),
                personagem.getNome(),
                personagem.getIdade(),
                personagem.getAldeia(),
                personagem.getJutsus(),
                personagem.getChakra()
        );
    }

}
