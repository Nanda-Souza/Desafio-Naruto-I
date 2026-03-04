package com.db.naruto.domain.service;



import com.db.naruto.domain.repository.PersonagemRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureJsonTesters
@Transactional

public class PersonagemIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonagemRepository personagemRepository;


    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Ninjutsu com sucesso!")
    void deveCriarNinjaDeNinjutsuComSucesso() throws Exception {
        String json = """
            {
              "nome": "Sakura Haruno",
              "vida": 115,
              "tipoNinja": "NINJUTSU",
              "jutsus": {
                "Contra-Genjutsu": {
                    "dano": 5,
                    "consumoDeChakra": 10
                }
              }
            }
            """;

        mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Sakura Haruno"))
                .andExpect(jsonPath("$.vida").value(115))
                .andExpect(jsonPath("$.jutsus", hasKey("Contra-Genjutsu")))
                .andExpect(jsonPath("$.jutsus['Contra-Genjutsu'].dano").value(5))
                .andExpect(jsonPath("$.jutsus['Contra-Genjutsu'].consumoDeChakra").value(10));

    }

    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Genjutsu com sucesso!")
    void deveCriarNinjaDeGenjutsuComSucesso() throws Exception {
        String json = """
            {
              "nome": "Ino Yamanaka",
              "vida": 115,
              "tipoNinja": "GENJUTSU",
              "jutsus": {
                "Técnica de Transferência de Mente": {
                    "dano": 25,
                    "consumoDeChakra": 10
                }
              }
            }
            """;

        mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Ino Yamanaka"))
                .andExpect(jsonPath("$.vida").value(115))
                .andExpect(jsonPath("$.jutsus", hasKey("Técnica de Transferência de Mente")))
                .andExpect(jsonPath("$.jutsus['Técnica de Transferência de Mente'].dano").value(25))
                .andExpect(jsonPath("$.jutsus['Técnica de Transferência de Mente'].consumoDeChakra").value(10));

    }

    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Taijutsu com sucesso!")
    void deveCriarNinjaDeTaijutsuComSucesso() throws Exception {
        String json = """
            {
              "nome": "Neji Hyuuga",
              "vida": 130,
              "tipoNinja": "TAIJUTSU",
              "jutsus": {
                "Oito Trigramas: Palma Rotativa": {
                    "dano": 35,
                    "consumoDeChakra": 10
                }
              }
            }
            """;

        mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Neji Hyuuga"))
                .andExpect(jsonPath("$.vida").value(130))
                .andExpect(jsonPath("$.jutsus", hasKey("Oito Trigramas: Palma Rotativa")))
                .andExpect(jsonPath("$.jutsus['Oito Trigramas: Palma Rotativa'].dano").value(35))
                .andExpect(jsonPath("$.jutsus['Oito Trigramas: Palma Rotativa'].consumoDeChakra").value(10));

    }

    @Test
    @DisplayName("Deve listar todos os personagens cadastrados!")
    void deveListarPersonagensComSucesso() throws Exception {

        String json1 = """
            {
              "nome": "Sakura Haruno",
              "vida": 115,
              "tipoNinja": "NINJUTSU",
              "jutsus": {
                "Contra-Genjutsu": {
                    "dano": 5,
                    "consumoDeChakra": 10
                }
              }
            }
            """;

        String json2 = """
            {
              "nome": "Ino Yamanaka",
              "vida": 115,
              "tipoNinja": "GENJUTSU",
              "jutsus": {
                "Técnica de Transferência de Mente": {
                    "dano": 25,
                    "consumoDeChakra": 10
                }
              }
            }
            """;

        String json3 = """
            {
              "nome": "Neji Hyuuga",
              "vida": 130,
              "tipoNinja": "TAIJUTSU",
              "jutsus": {
                "Oito Trigramas: Palma Rotativa": {
                    "dano": 35,
                    "consumoDeChakra": 10
                }
              }
            }
            """;

        mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json3))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/personagem"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].nome").value("Sakura Haruno"))
                .andExpect(jsonPath("$[1].nome").value("Ino Yamanaka"))
                .andExpect(jsonPath("$[2].nome").value("Neji Hyuuga"));
    }

    @Test
    @DisplayName("Deve deletar personagem com sucesso!")
    void deveDeletarPersonagemComSucesso() throws Exception {

        String json = """
            {
              "nome": "Sakura Haruno",
              "vida": 115,
              "tipoNinja": "NINJUTSU",
              "jutsus": {
                "Contra-Genjutsu": {
                    "dano": 5,
                    "consumoDeChakra": 10
                }
              }
            }
            """;

        String response = mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number idNumber = JsonPath.read(response, "$.id");
        Long id = idNumber.longValue();

        mockMvc.perform(delete("/personagem/{id}", id))
                .andExpect(status().isNoContent());


        mockMvc.perform(get("/personagem/{id}", id))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Deve atualizar o nome do personagem com sucesso!")
    void deveAtualizarPersonagemComSucesso() throws Exception {

        String json = """
            {
              "nome": "Sakura",
              "vida": 115,
              "tipoNinja": "NINJUTSU",
              "jutsus": {
                "Contra-Genjutsu": {
                    "dano": 5,
                    "consumoDeChakra": 10
                }
              }
            }
            """;

        String response = mockMvc.perform(post("/personagem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Number idNumber = JsonPath.read(response, "$.id");
        Long id = idNumber.longValue();

        String jsonUpdate = """
        {
          "nome": "Sakura Haruno"
        }
        """;

        mockMvc.perform(patch("/personagem/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Sakura Haruno"));
    }


}
