package com.ohi.messageapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MessageRequest {

    @NotBlank(message = "A chave não pode ser vazia")
    public String chave;

    @NotBlank(message = "A mensagem não pode ser vazia")
    public String mensagem;

    @NotBlank(message = "canalCategoria não pode ser vazio")
    public String canalCategoria;

    @Pattern(
            regexp = "a|i",
            message = "Status deve ser 'a' (ativo) ou 'i' (inativo)"
    )
    public String status;

}