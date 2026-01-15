package com.ohi.messageapi.service;

import com.ohi.messageapi.exception.ChaveDuplicadaException;
import com.ohi.messageapi.model.Message;
import com.ohi.messageapi.repository.MessageRepository;
import org.springframework.stereotype.Service;
import com.ohi.messageapi.exception.MessageNotFoundException;
import com.ohi.messageapi.dto.MessageResponse;

@Service
public class MessageService {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public Message salvar(String chave, String mensagem) {

        if (repository.findByChaveMensagem(chave).isPresent()) {
            throw new ChaveDuplicadaException("Já existe uma mensagem com essa chave");
        }

        Message message = new Message(chave, mensagem);
        return repository.save(message);
    }


    public Message buscarPorChave(String chave) {
        return repository.findByChaveMensagem(chave)
                .orElseThrow(() -> new MessageNotFoundException(chave));
    }

    public MessageResponse buscarRespostaPorChave(String chave){
        Message message = buscarPorChave(chave);

        MessageResponse response = new MessageResponse();
        response.id = message.getId();
        response.chave = message.getChaveMensagem();
        response.mensagem = message.getMensagem();
        response.dataCriacao = message.getDataCriacao();
        response.dataAlteracao = message.getDataAlteracao();

        return response;
    }
}
