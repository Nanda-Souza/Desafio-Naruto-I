package com.db.naruto.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureJsonTesters
@Transactional
public class BatalhaServiceIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Deve executar batalha e consumir chakra do atacante!")
    void deveExecutarUmaBatalhaComSucesso() throws Exception {

        String jsonCriarAtacante = """
                {
                  "nome": "Naruto Uzumaki",
                  "vida": 120,
                  "tipoNinja": "NINJUTSU",
                  "jutsus": {
                    "Rasengan": { "dano": 60, "consumoDeChakra": 35 }
                  }
                }
                """;

        String jsonCriarDefensor = """
                {
                  "nome": "Sasuke Uchiha",
                  "vida": 120,
                  "tipoNinja": "NINJUTSU",
                  "jutsus": {
                    "Chidori": { "dano": 60, "consumoDeChakra": 35 }
                  }
                }
                
                """;

        String responseCriarPersonagemAtacante = mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCriarAtacante))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String responseCriarPersonagemDefensor = mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCriarDefensor))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String json = """
                {
                    "idAtacante": 1,
                    "nomeJutsu": "Rasengan",
                    "idDefensor": 2
                }
                """;

        mockMvc.perform(post("/batalha")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultado").exists());



    }

}
