package com.ohi.messageapi.repository;

import com.ohi.messageapi.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("""
    SELECT m FROM Message m
    WHERE (:chave IS NULL OR LOWER(m.chaveMensagem) LIKE LOWER(CONCAT('%', :chave, '%')))
      AND (:canalCategoria IS NULL OR LOWER(m.canalCategoria) LIKE LOWER(CONCAT('%', :canalCategoria, '%')))
      AND (:status IS NULL OR m.status = :status)
""")
    List<Message> buscarComFiltros(
            @Param("chave") String chave,
            @Param("canalCategoria") String canalCategoria,
            @Param("status") String status
    );

    Optional<Message> findByChaveMensagemIgnoreCaseAndCanalCategoriaIgnoreCase(
            String chaveMensagem,
            String canalCategoria
    );

    Optional<Message> findByChaveMensagemIgnoreCase(String chaveMensagem);


}
