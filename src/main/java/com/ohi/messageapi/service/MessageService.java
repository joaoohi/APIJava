package com.ohi.messageapi.service;

import com.ohi.messageapi.model.Message;
import com.ohi.messageapi.repository.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Message salvar(String chave, String mensagem) {
        Message message = new Message(chave, mensagem);
        return repository.save(message);
    }

    public Message buscarPorChave(String chave) {
        return repository.findByChaveMensagem(chave)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));
    }
}
