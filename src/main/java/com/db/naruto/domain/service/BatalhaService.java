package com.db.naruto.domain.service;

import com.db.naruto.domain.dto.BatalhaRequest;
import com.db.naruto.domain.dto.BatalhaResponse;
import com.db.naruto.domain.entity.Jutsu;
import com.db.naruto.domain.entity.Ninja;
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
        String resultado = "";
        boolean desviou = false;


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

        if (!atacante.getJutsus().containsKey(batalhaRequest.nomeJutsu())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Jutsu do personagem atacante não encontrado!");
        }

        if (atacante.foiDerrotado() || !atacante.podeAtacar()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O personagem atacante já foi derrotado ou não tem mais chakra para atacar com nenhum jutsu!");
        }

        if (defensor.foiDerrotado() || !defensor.podeAtacar()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "O personagem defensor já foi derrotado ou não tem mais chakra para atacar com nenhum jutsu!");
        }

        String nomeJutsu = batalhaRequest.nomeJutsu();
        Jutsu jutsu =  atacante.getJutsus().get(nomeJutsu);

        if (jutsu.getConsumoDeChakra() > atacante.getChakra()){
            resultado = atacante.getNome() + " não tem chakra o suficiente para usar " + nomeJutsu + " tente usar outro jutsu!";

            return new BatalhaResponse(resultado);
        }

        if (atacante instanceof Ninja ninja) {
            ninja.usarJutsu();
        }

        if (defensor.conseguiuDesviar()){
            desviou = true;
            if (defensor instanceof Ninja ninja) {
                ninja.desviar();
            }
        }

        atacante.gastarChakra(jutsu.getConsumoDeChakra());
        personagemRepository.save(atacante);

        if (desviou){
            resultado = defensor.getNome() + " se desviou do jutsu " + nomeJutsu + "!";
            return new BatalhaResponse(resultado);
        }

        defensor.levarDano(jutsu.getDano());
        personagemRepository.save(defensor);

        if (defensor.foiDerrotado()){
            resultado = defensor.getNome() + " foi recebeu o jutsu " + nomeJutsu + " e foi derrotado(a) por " + atacante.getNome() + "!";
            return new BatalhaResponse(resultado);
        }

        resultado = atacante.getNome() + " está atacando " + defensor.getNome() + " com " + nomeJutsu + "!";

        return new BatalhaResponse(resultado);

    }


}
