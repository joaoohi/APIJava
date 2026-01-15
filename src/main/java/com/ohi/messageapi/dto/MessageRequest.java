package com.ohi.messageapi.dto;

import jakarta.validation.constraints.NotBlank;

public class MessageRequest {

    @NotBlank(message = "A chave não pode ser vazia")
    public String chave;

    @NotBlank(message = "A mensagem não pode ser vazia")
    public String mensagem;

}