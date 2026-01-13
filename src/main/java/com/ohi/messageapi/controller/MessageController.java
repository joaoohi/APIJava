package com.ohi.messageapi.controller;

import com.ohi.messageapi.model.Message;
import com.ohi.messageapi.service.MessageService;
import com.ohi.messageapi.dto.MessageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping
    public Message salvar(@RequestBody MessageRequest request) {
        return service.salvar(request.chave, request.mensagem);
    }

    @GetMapping("/{chave}")
    public Message buscar(@PathVariable String chave) {
        return service.buscarPorChave(chave);
    }
}
