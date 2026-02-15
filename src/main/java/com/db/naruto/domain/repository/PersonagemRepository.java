package com.db.naruto.domain.repository;

import com.db.naruto.domain.entity.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonagemRepository extends JpaRepository<Personagem, Long> {
}
