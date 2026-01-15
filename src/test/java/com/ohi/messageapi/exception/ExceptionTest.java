package com.ohi.messageapi.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExceptionTest {
    @Test
    void deveCriarChaveDuplicadaException() {
        ChaveDuplicadaException ex =
                new ChaveDuplicadaException("Chave já existe");

        assertThat(ex.getMessage()).isEqualTo("Chave já existe");
    }

    @Test
    void deveCriarMessageNotFoundException() {
        MessageNotFoundException ex =
                new MessageNotFoundException("Mensagem não encontrada");

        assertThat(ex.getMessage()).isEqualTo("Mensagem não encontrada");
    }

}
