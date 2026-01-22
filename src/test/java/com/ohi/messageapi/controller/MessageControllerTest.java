package com.ohi.messageapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MessageControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void deveSalvarMensagemComSucesso() throws Exception {

        String json = """
    {
      "chave": "teste-chave-1",
      "mensagem": "teste 1",
      "canalCategoria": "EMAIL"
    }
    """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.chaveMensagem").value("teste-chave-1"))
                .andExpect(jsonPath("$.mensagem").value("teste 1"))
                .andExpect(jsonPath("$.canalCategoria").value("EMAIL"));
    }


    @Test
    void deveBuscarMensagemPorChave() throws Exception {

        String json = """
    {
      "chave": "teste-chave-2",
      "mensagem": "mensagem de teste",
      "canalCategoria": "WHATSAPP"
    }
    """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/messages")
                        .param("chave", "teste-chave-2")
                        .param("canalCategoria", "WHATSAPP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mensagem")
                        .value("mensagem de teste"));
    }

    @Test
    void deveBuscarMensagemApenasPorChave() throws Exception {

        String json = """
    {
      "chave": "chave-apenas",
      "mensagem": "mensagem so chave",
      "canalCategoria": "EMAIL"
    }
    """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/messages")
                        .param("chave", "chave-apenas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mensagem")
                        .value("mensagem so chave"));
    }


    @Test
    void deveBuscarMensagemApenasPorCanal() throws Exception {

        String json = """
    {
      "chave": "outra-chave",
      "mensagem": "mensagem whatsapp",
      "canalCategoria": "WHATSAPP"
    }
    """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/messages")
                        .param("canalCategoria", "WHATSAPP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].canalCategoria")
                        .value("WHATSAPP"));
    }



    @Test
    void deveRetornar400QuandoNenhumFiltroForInformado() throws Exception {

        mockMvc.perform(get("/messages"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void deveRetornar404QuandoMensagemNaoExistir() throws Exception {

        mockMvc.perform(get("/messages")
                        .param("chave", "nao-existe")
                        .param("canalCategoria", "EMAIL"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message")
                        .value("Mensagem não encontrada"));
    }

    @Test
    void deveRetornar400QuandoBodyForInvalido() throws Exception {

        String jsonInvalido = "{}";

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void deveRetornar400QuandoSalvarMensagemInvalida() throws Exception {

        String jsonInvalido = """
    {
      "mensagem": "sem chave",
      "canalCategoria": "EMAIL"
    }
    """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }

    @Test
    void deveBuscarMensagemViaPostBuscar() throws Exception {

        String jsonCriar = """
    {
      "chave": "buscar-post",
      "mensagem": "teste post buscar",
      "canalCategoria": "EMAIL"
    }
    """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCriar))
                .andExpect(status().isCreated());

        String jsonBusca = """
    {
      "chave": "buscar-post",
      "canalCategoria": "EMAIL"
    }
    """;

        mockMvc.perform(post("/messages/buscar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBusca))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mensagem")
                        .value("teste post buscar"));
    }

    @Test
    void deveRetornar404NoPostBuscarQuandoNaoEncontrar() throws Exception {

        String jsonBusca = """
    {
      "chave": "nao-existe",
      "canalCategoria": "EMAIL"
    }
    """;

        mockMvc.perform(post("/messages/buscar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBusca))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void deveBuscarMensagemApenasPorCategoria() throws Exception {

        String json = """
    {
      "chave": "categoria-only",
      "mensagem": "mensagem categoria",
      "canalCategoria": "SMS"
    }
    """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/messages")
                        .param("canalCategoria", "SMS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mensagem")
                        .value("mensagem categoria"));
    }

    @Test
    void deveAtualizarMensagemQuandoChaveJaExistir() throws Exception {

        String jsonOriginal = """
    {
      "chave": "atualizar-chave",
      "mensagem": "mensagem original",
      "canalCategoria": "EMAIL"
    }
    """;

        String jsonAtualizado = """
    {
      "chave": "atualizar-chave",
      "mensagem": "mensagem atualizada",
      "canalCategoria": "EMAIL"
    }
    """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOriginal))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAtualizado))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mensagem")
                        .value("mensagem atualizada"));
    }

    @Test
    void deveInativarMensagemComSucesso() throws Exception {

        String json = """
    {
      "chave": "teste-1",
      "mensagem": "mensagem teste",
      "canalCategoria": "WHATSAPP"
    }
    """;

        mockMvc.perform(post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/messages/teste-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chaveMensagem").value("teste-1"))
                .andExpect(jsonPath("$.status").value("i"));
    }


    @Test
    void deveRetornar404AoInativarMensagemInexistente() throws Exception {

        mockMvc.perform(delete("/messages/chave-nao-existe"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Mensagem não encontrada"));
    }
}