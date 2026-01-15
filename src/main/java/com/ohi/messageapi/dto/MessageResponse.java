package com.ohi.messageapi.dto;

import java.time.LocalDateTime;

public class MessageResponse {

    public Long id;
    public String chave;
    public String mensagem;
    public LocalDateTime dataCriacao;
    public LocalDateTime dataAlteracao;
}
