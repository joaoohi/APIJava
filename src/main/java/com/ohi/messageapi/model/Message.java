package com.ohi.messageapi.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chave_mensagem", unique = true, nullable = false)
    private String chaveMensagem;

    @Column(nullable = false)
    private String mensagem;

    @Column(name = "canal_categoria", nullable = false)
    private String canalCategoria;

    @Column(nullable = false, length = 1)
    String status;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime dataAlteracao;

    public Message() {}

    public Message(String chaveMensagem, String mensagem, String canalCategoria) {
        this.chaveMensagem = chaveMensagem;
        this.mensagem = mensagem;
        this.canalCategoria = canalCategoria;
        this.status = "a";
        this.dataCriacao = LocalDateTime.now();
        this.dataAlteracao = this.dataCriacao;
    }


    public Long getId() {
        return id;
    }

    public String getChaveMensagem() {
        return chaveMensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCanalCategoria() {return canalCategoria;}

    public String getStatus() {return status;}

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
