package com.db.naruto.domain.service;

import com.jayway.jsonpath.JsonPath;
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

import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureJsonTesters
@Transactional
class JutsuIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Deve remover jutsu com sucesso!")
    void deveRemoverJutsuComSucesso() throws Exception {

        String jsonCriarPersonagem = """
            {
              "nome": "Naruto Uzumaki",
              "vida": 120,
              "tipoNinja": "NINJUTSU",
              "jutsus": {
                "Rasengan": { "dano": 80, "consumoDeChakra": 40 },
                "Chidori":  { "dano": 85, "consumoDeChakra": 50 }
              }
            }
            """;

        String responseCriarPersonagem = mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCriarPersonagem))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();


        Number id = JsonPath.read(responseCriarPersonagem, "$.id");
        Long idPersonagem = id.longValue();

        String json = """
            { "nomeJutsu": "Chidori" }
            """;

        mockMvc.perform(delete("/jutsu/remover/{idPersonagem}", idPersonagem)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Naruto Uzumaki"))
                .andExpect(jsonPath("$.jutsus", hasKey("Rasengan")))
                .andExpect(jsonPath("$.jutsus['Rasengan'].dano").value(80))
                .andExpect(jsonPath("$.jutsus['Rasengan'].consumoDeChakra").value(40))
                .andExpect(jsonPath("$.jutsus.Chidori").doesNotExist());
    }
}