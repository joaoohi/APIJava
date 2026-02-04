package com.ohi.messageapi.service;

import com.ohi.messageapi.model.Message;
import com.ohi.messageapi.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ohi.messageapi.exception.MessageNotFoundException;
import com.ohi.messageapi.dto.MessageResponse;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository repository;

    public Message salvar(
            String chave,
            String mensagem,
            String canalCategoria,
            String status
    ) {

        var existente = repository
                .findByChaveMensagemIgnoreCaseAndCanalCategoriaIgnoreCase(
                        chave,
                        canalCategoria
                );

        if (existente.isPresent()) {
            Message message = existente.get();

            boolean alterou = false;

            if (!message.getMensagem().equals(mensagem)) {
                message.setMensagem(mensagem);
                alterou = true;
            }

            if (status != null && !status.equals(message.getStatus())) {
                message.setStatus(status);
                alterou = true;
            }

            if (alterou) {
                message.setDataAlteracao(LocalDateTime.now());
                return repository.save(message);
            }

            return message;
        }

        Message nova = new Message(chave, mensagem, canalCategoria);

        if (status != null) {
            nova.setStatus(status);
        }

        return repository.save(nova);
    }

    public List<MessageResponse> buscarComFiltros(
            String chave,
            String canalCategoria,
            String status
    ) {

        List<Message> mensagens = repository.buscarComFiltros(
                (chave == null || chave.isBlank()) ? null : chave,
                (canalCategoria == null || canalCategoria.isBlank()) ? null : canalCategoria,
                (status == null || status.isBlank()) ? null : status
        );

        return mensagens.stream().map(message -> {
            MessageResponse response = new MessageResponse();
            response.id = message.getId();
            response.chave = message.getChaveMensagem();
            response.mensagem = message.getMensagem();
            response.canalCategoria = message.getCanalCategoria();
            response.dataCriacao = message.getDataCriacao();
            response.dataAlteracao = message.getDataAlteracao();
            response.status = message.getStatus();
            return response;
        }).toList();
    }

    public Message inativar(String chave) {

        Message message = repository.findByChaveMensagemIgnoreCase(chave)
                .orElseThrow(() -> new MessageNotFoundException("Mensagem não encontrada"));

        message.setStatus("i");
        message.setDataAlteracao(java.time.LocalDateTime.now());

        return repository.save(message);
    }

    public Message atualizar(
            String chave,
            String mensagem,
            String canalCategoria,
            String status
    ) {
        Message message = repository.findByChaveMensagemIgnoreCase(chave)
                .orElseThrow(() ->
                        new IllegalArgumentException("Mensagem não encontrada para a chave: " + chave)
                );

        message.setMensagem(mensagem);
        message.setCanalCategoria(canalCategoria);
        message.setStatus(status);
        message.setDataAlteracao(LocalDateTime.now());

        return repository.save(message);
    }
}
