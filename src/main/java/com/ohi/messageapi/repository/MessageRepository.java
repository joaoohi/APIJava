package com.ohi.messageapi.repository;

import com.ohi.messageapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Optional<Message> findByChaveMensagem(String chaveMensagem);

}
