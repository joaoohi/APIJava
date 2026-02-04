package com.ohi.messageapi.controller;

import com.ohi.messageapi.model.Message;
import com.ohi.messageapi.service.MessageService;
import com.ohi.messageapi.dto.MessageRequest;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.ohi.messageapi.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import com.ohi.messageapi.dto.MessageSearchRequest;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/messages")
@CrossOrigin(origins = "http://localhost:4200")
public class MessageController {

    private final MessageService service;

    public MessageController(MessageService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message salvar(@RequestBody @Valid MessageRequest request) {
        return service.salvar(
                request.chave,
                request.mensagem,
                request.canalCategoria,
                request.status
        );
    }

    @GetMapping
    public List<MessageResponse> buscarComFiltros(
            @RequestParam(required = false) String chave,
            @RequestParam(required = false) String canalCategoria,
            @RequestParam(required = false) String status
    ) {
        return service.buscarComFiltros(chave, canalCategoria, status);
    }

    @PostMapping("/buscar")
    public List<MessageResponse> buscar(
            @RequestBody MessageSearchRequest request
    ) {
        return service.buscarComFiltros(
                request.chave,
                request.canalCategoria,
                request.status
        );
    }

    @DeleteMapping("/{chave}")
    public Message inativar(@PathVariable String chave) {
        return service.inativar(chave);
    }

    @PutMapping("/{chave}")
    public Message atualizar(
            @PathVariable String chave,
            @RequestBody @Valid MessageRequest request
    ) {
        return service.atualizar(
                chave,
                request.mensagem,
                request.canalCategoria,
                request.status
        );
    }

}
