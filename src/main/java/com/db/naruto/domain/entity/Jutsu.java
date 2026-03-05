package com.db.naruto.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Jutsu {

    private int dano;
    private int consumoDeChakra;
}