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
    private Map<String, Jutsus> jutsus = new HashMap<>();

    public Personagem(String nome, int vida ) {
        this.nome = nome;
        this.vida = vida;
        this.chakra = 100;
    }

}