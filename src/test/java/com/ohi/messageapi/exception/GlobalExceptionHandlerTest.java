package com.ohi.messageapi.exception;

import com.ohi.messageapi.controller.MessageController;
import com.ohi.messageapi.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc(addFilters = false)

@WebMvcTest(MessageController.class)
class GlobalExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void deveRetornar404QuandoMensagemNaoExistir() throws Exception {

        when(messageService.buscarRespostaPorChave("chave-que-nao-existe"))
                .thenThrow(new MessageNotFoundException("chave-que-nao-existe"));

        mockMvc.perform(get("/messages/chave-que-nao-existe"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Mensagem não encontrada"));
    }


    @Test
    void deveRetornar400QuandoCamposInvalidos() throws Exception {

        String jsonInvalido = "{}";

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void deveRetornar409QuandoChaveDuplicada() throws Exception {

        String json = """
    {
      "chave": "duplicada",
      "mensagem": "teste"
    }
    """;

        when(messageService.salvar(any(), any()))
                .thenThrow(new ChaveDuplicadaException(
                        "Já existe uma mensagem com essa chave"
                ));

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.message")
                        .value("Já existe uma mensagem com essa chave"));
    }


    @MockBean
    MessageService messageService;
    @Test
    void deveRetornar500QuandoErroInesperado() throws Exception {

        when(messageService.buscarRespostaPorChave(any()))
                .thenThrow(new RuntimeException("Erro qualquer"));

        mockMvc.perform(get("/messages/qualquer"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("Erro inesperado"));
    }

}
