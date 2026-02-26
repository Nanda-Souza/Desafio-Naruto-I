package com.db.naruto.domain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_ninja", length = 30)

public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private int idade;
    private String aldeia;
    private List<String> jutsus = new ArrayList<>();
    private int chakra;

    public Personagem(String nome, int idade, String aldeia, List<String> jutsus, int chakra) {
        this.nome = nome;
        this.idade = idade;
        this.aldeia = aldeia;
        this.jutsus = jutsus;
        this.chakra = chakra;
    }

    protected Personagem() {}

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getAldeia() {
        return aldeia;
    }

    public List<String> getJutsus() {
        return jutsus;
    }

    public int getChakra() {
        return chakra;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setAldeia(String aldeia) {
        this.aldeia = aldeia;
    }

    public void setJutsus(List<String> jutsus) {
        this.jutsus = jutsus;
    }

    public void setChakra(int chakra) {
        this.chakra = chakra;
    }
}