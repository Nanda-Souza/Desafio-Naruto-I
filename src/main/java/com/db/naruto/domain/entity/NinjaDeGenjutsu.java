package com.db.naruto.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("genjutsu")
public class NinjaDeGenjutsu extends Personagem implements Ninja{

    public NinjaDeGenjutsu(String nome, int vida) {
        super(nome, vida);
    }

    protected NinjaDeGenjutsu() {
        super();
    }


    @Override
    public void usarJutsu() {
        System.out.println(getNome() + " está atacando com um golpe de Genjutsu!");
    }

    @Override
    public void desviar() {
        System.out.println(getNome() + " está se desviando de um ataque recebido com um golpe de Genjutsu!");
    }
}
