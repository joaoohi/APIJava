package com.ohi.messageapi.repository;

import com.ohi.messageapi.model.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MessageRepositoryTest{

    @Autowired
    MessageRepository repository;

    @Test
    void deveSalvarEMostrarMensagem(){
        Message message = new Message(
                "chave-teste",
                "mensagem de teste"
        );


        Message salvo = repository.save(message);

        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getMensagem()).isEqualTo("mensagem de teste");

    }
}