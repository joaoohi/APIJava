package com.ohi.messageapi.repository;

import com.ohi.messageapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChaveMensagemContainingIgnoreCaseAndCanalCategoria(
            String chaveMensagem,
            String canalCategoria
    );

    List<Message> findByChaveMensagemContainingIgnoreCase(String chaveMensagem);

    List<Message> findByCanalCategoriaContainingIgnoreCase(String canalCategoria);

    Optional<Message> findByChaveMensagemIgnoreCaseAndCanalCategoriaIgnoreCase(
            String chaveMensagem,
            String canalCategoria
    );

    Optional<Message> findByChaveMensagemIgnoreCase(String chaveMensagem);


}
