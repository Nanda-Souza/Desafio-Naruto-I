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
              "idade": 12,
              "aldeia": "Konoha",
              "jutsus": ["Contra-Genjutsu"],
              "chakra": 65
            }
            """;

        mockMvc.perform(post("/personagem/ninja_de_ninjutsu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Sakura Haruno"));
    }

    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Genjutsu com sucesso!")
    void deveCriarNinjaDeGenjutsuComSucesso() throws Exception {
        String json = """
            {
              "nome": "Shikamaru Nara",
              "idade": 12,
              "aldeia": "Konoha",
              "jutsus": ["Método das Sombras"],
              "chakra": 65
            }
            """;

        mockMvc.perform(post("/personagem/ninja_de_genjutsu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Shikamaru Nara"));

    }

    @Test
    @DisplayName("Deve salvar e retornar o Ninja de Taijutsu com sucesso!")
    void deveCriarNinjaDeTaijutsuComSucesso() throws Exception {
        String json = """
            {
              "nome": "Neji Hyuuga",
              "idade": 12,
              "aldeia": "Konoha",
              "jutsus": ["Oito Trigramas: Sessenta e Quatro Palmas"],
              "chakra": 45
            }
            """;

        mockMvc.perform(post("/personagem/ninja_de_taijutsu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nome").value("Neji Hyuuga"));

    }

    @Test
    @DisplayName("Deve listar todos os personagens cadastrados!")
    void deveListarPersonagensComSucesso() throws Exception {

        String json1 = """
            {
              "nome": "Sakura Haruno",
              "idade": 12,
              "aldeia": "Konoha",
              "jutsus": ["Contra-Genjutsu"],
              "chakra": 65
            }
            """;

        String json2 = """
            {
              "nome": "Shikamaru Nara",
              "idade": 12,
              "aldeia": "Konoha",
              "jutsus": ["Método das Sombras"],
              "chakra": 65
            }
            """;

        String json3 = """
            {
              "nome": "Neji Hyuuga",
              "idade": 12,
              "aldeia": "Konoha",
              "jutsus": ["Oito Trigramas: Sessenta e Quatro Palmas"],
              "chakra": 45
            }
            """;

        mockMvc.perform(post("/personagem/ninja_de_ninjutsu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json1))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/personagem/ninja_de_genjutsu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json2))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/personagem/ninja_de_taijutsu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json3))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/personagem"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].nome").value("Sakura Haruno"))
                .andExpect(jsonPath("$[1].nome").value("Shikamaru Nara"))
                .andExpect(jsonPath("$[2].nome").value("Neji Hyuuga"));
    }

    @Test
    @DisplayName("Deve deletar personagem com sucesso!")
    void deveDeletarPersonagemComSucesso() throws Exception {

        String json = """
            {
              "nome": "Sakura Haruno",
              "idade": 12,
              "aldeia": "Konoha",
              "jutsus": ["Contra-Genjutsu"],
              "chakra": 65
            }
            """;

        String response = mockMvc.perform(post("/personagem/ninja_de_ninjutsu")
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
              "idade": 12,
              "aldeia": "Konoha",
              "jutsus": ["Contra-Genjutsu"],
              "chakra": 65
            }
            """;

        String response = mockMvc.perform(post("/personagem/ninja_de_ninjutsu")
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
