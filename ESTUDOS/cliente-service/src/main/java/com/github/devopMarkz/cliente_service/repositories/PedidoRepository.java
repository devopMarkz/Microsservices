package com.github.devopMarkz.cliente_service.repositories;

import com.github.devopMarkz.cliente_service.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT obj FROM Pedido obj JOIN FETCH obj.itens WHERE obj.id = :id")
    Optional<Pedido> findWithItens(@Param("id") Long id);

    @Query("SELECT obj FROM Pedido obj JOIN FETCH obj.itens WHERE obj.cliente.id = :clienteId")
    List<Pedido> buscarTodos(@Param("clienteId") Long clienteId);

}
