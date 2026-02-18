package com.db.naruto.domain.service;


import com.db.naruto.domain.dto.PersonagemRequest;
import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.entity.NinjaDeGenjutsu;
import com.db.naruto.domain.entity.NinjaDeNinjutsu;
import com.db.naruto.domain.entity.NinjaDeTaijutsu;
import com.db.naruto.domain.entity.Personagem;
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
                .orElseThrow(()->
                        new RuntimeException("Personagem com Id " + id + " não foi encontrado!"));

        personagemRepository.delete(personagem);

    }



}
