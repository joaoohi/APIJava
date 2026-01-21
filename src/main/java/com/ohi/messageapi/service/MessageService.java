package com.ohi.messageapi.service;

import com.ohi.messageapi.model.Message;
import com.ohi.messageapi.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ohi.messageapi.exception.MessageNotFoundException;
import com.ohi.messageapi.dto.MessageResponse;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository repository;

    public Message salvar(String chave, String mensagem, String canalCategoria) {

        var existente = repository
                .findByChaveMensagemIgnoreCaseAndCanalCategoriaIgnoreCase(
                        chave,
                        canalCategoria
                );

        if (existente.isPresent()) {
            Message message = existente.get();

            if (!message.getMensagem().equals(mensagem)) {
                message.setMensagem(mensagem);
                message.setDataAlteracao(java.time.LocalDateTime.now());
                return repository.save(message);
            }

            return message;
        }

        Message nova = new Message(chave, mensagem, canalCategoria);
        return repository.save(nova);
    }


    public List<MessageResponse> buscarComFiltros(
            String chave,
            String canalCategoria
    ) {

        boolean temChave = chave != null && !chave.isBlank();
        boolean temCategoria = canalCategoria != null && !canalCategoria.isBlank();

        List<Message> mensagens;

        if (temChave && temCategoria) {
            mensagens = repository
                    .findByChaveMensagemContainingIgnoreCaseAndCanalCategoria(
                            chave,
                            canalCategoria
                    );

        } else if (temChave) {
            mensagens = repository
                    .findByChaveMensagemContainingIgnoreCase(chave);

        } else if (temCategoria) {
            mensagens = repository
                    .findByCanalCategoriaContainingIgnoreCase(canalCategoria);
        } else {
            throw new IllegalArgumentException(
                    "Informe pelo menos um filtro: chave ou canalCategoria"
            );
        }

        if (mensagens.isEmpty()) {
            throw new MessageNotFoundException("Nenhuma mensagem encontrada");
        }

        return mensagens.stream().map(message -> {
            MessageResponse response = new MessageResponse();
            response.id = message.getId();
            response.chave = message.getChaveMensagem();
            response.mensagem = message.getMensagem();
            response.canalCategoria= message.getCanalCategoria();
            response.dataCriacao = message.getDataCriacao();
            response.dataAlteracao = message.getDataAlteracao();
            return response;
        }).toList();
    }

}
