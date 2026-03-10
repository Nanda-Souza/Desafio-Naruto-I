package com.db.naruto.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("taijutsu")
public class NinjaDeTaijutsu extends Personagem implements Ninja{

    public NinjaDeTaijutsu(String nome, int vida) {
        super(nome, vida);
    }

    protected NinjaDeTaijutsu() {
        super();
    }


    @Override
    public void usarJutsu() {
        System.out.println(getNome() + " está atacando com um golpe de Taijutsu!");
    }

    @Override
    public void desviar() {
        System.out.println(getNome() + " está se desviando de um ataque recebido com um golpe de Taijutsu!");
    }
}
