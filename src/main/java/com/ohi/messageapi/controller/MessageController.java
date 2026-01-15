package com.ohi.messageapi.controller;

import com.ohi.messageapi.model.Message;
import com.ohi.messageapi.service.MessageService;
import com.ohi.messageapi.dto.MessageRequest;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.ohi.messageapi.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message salvar(@RequestBody @Valid MessageRequest request) {
        return service.salvar(request.chave, request.mensagem);
    }

    @GetMapping("/{chave}")
    public MessageResponse buscar(@PathVariable String chave) {
        return service.buscarRespostaPorChave(chave);
    }
}
