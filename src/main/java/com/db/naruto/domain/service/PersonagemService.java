package com.db.naruto.domain.service;


import com.db.naruto.domain.dto.PersonagemRequest;
import com.db.naruto.domain.dto.PersonagemResponse;
import com.db.naruto.domain.dto.PersonagemUpdateRequest;
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

    public PersonagemResponse atualizarPersonagem(Long id, PersonagemUpdateRequest personagemUpdate){

        Personagem personagem = personagemRepository.findById(id)
                .orElseThrow(()->
                        new RuntimeException("Personagem com Id " + id + " não foi encontrado!"));

        if (personagemUpdate.nome() != null){
            if(personagemUpdate.nome().isBlank()){
                throw new IllegalArgumentException("O nome do ninja não pode ser vazio!");
            }
            personagem.setNome(personagemUpdate.nome());

        }

        Integer idadeNinja = personagemUpdate.idade();
        if (idadeNinja != 0){
            if(idadeNinja < 7){
                throw new IllegalArgumentException("Um ninja deve ter no mínimo 7 anos!");
            }
            personagem.setIdade(idadeNinja);
        }

        if (personagemUpdate.aldeia() != null){
            if(personagemUpdate.aldeia().isBlank()){
                throw new IllegalArgumentException("O nome da aldeia do ninja não pode ser vazia!");

            }

            personagem.setAldeia(personagemUpdate.aldeia());
        }

        if  (personagemUpdate.jutsus() != null){
            if(personagemUpdate.jutsus().isEmpty()){
                throw new IllegalArgumentException("O ninja deve possuir ao menos um jutsu!");
            }

            personagem.setJutsus(personagemUpdate.jutsus());
        }

        Integer chakraNinja = personagemUpdate.chakra();
        if (chakraNinja != 0){
            if(chakraNinja < 1){
                throw new IllegalArgumentException("O chakra do ninja deve ser maior que zero!");
            }
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



}
