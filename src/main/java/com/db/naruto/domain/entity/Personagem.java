package com.db.naruto.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_ninja", length = 30)

public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int vida;
    private int chakra;

    @ElementCollection
    @CollectionTable(name = "personagem_jutsus",
            joinColumns = @JoinColumn(name = "personagem_id"))
    @MapKeyColumn(name = "nome_jutsu")
    private Map<String, Jutsu> jutsus = new HashMap<>();

    public Personagem(String nome, int vida ) {
        this.nome = nome;
        this.vida = vida;
        this.chakra = 100;
    }

    public boolean foiDerrotado(){
        return chakra <= 0 || vida <= 0;
    }


    public boolean podeAtacar() {
        for (Jutsu j : jutsus.values()) {
            if (j.getConsumoDeChakra() <= this.chakra) {
                return true;
            }
        }
        return false;
    }

    public boolean conseguiuDesviar(){

        if (Math.random() < 0.20){
            return true;
        }

        return false;
    }

    public void levarDano(int dano) {
        this.vida -= dano;
    }

    public void gastarChakra(int custo) {
        this.chakra -= custo;
    }





}