package com.ohi.messageapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MessageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void deveSalvarMensagemComSucesso() throws Exception{

        String json = """
                {
                    "chave": "teste-chave-1",
                    "mensagem": "teste 1"
                }
                """;

        mockMvc.perform(post("/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.chaveMensagem").value("teste-chave-1"))
                .andExpect(jsonPath("$.mensagem").value("teste 1"));
    }

    @Test
    void deveBuscarMensagemPorChave() throws Exception {

        String json = """
        {
          "chave": "teste-chave-2",
          "mensagem": "mensagem de teste"
        }
        """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/messages/teste-chave-2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("mensagem de teste"));
    }

}