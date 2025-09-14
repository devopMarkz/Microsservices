package com.github.devopMarkz.cliente_service.repositories;

import com.github.devopMarkz.cliente_service.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
