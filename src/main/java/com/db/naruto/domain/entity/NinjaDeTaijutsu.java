package com.db.naruto.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
@DiscriminatorValue("taijutsu")
public class NinjaDeTaijutsu extends Personagem implements Ninja{

    public NinjaDeTaijutsu(String nome, int idade, String aldeia, List<String> jutsus, int chakra) {
        super(nome, idade, aldeia, jutsus, chakra);
    }

    protected NinjaDeTaijutsu() {
        super();
    }


    @Override
    public void usarJutsu() {
        System.out.println("O personagem está atacando com um golpe de Taijutsu!");
    }

    @Override
    public void desviar() {
        System.out.println("O personagem está se desviando usando um golpe de Taijutsu!");
    }
}
