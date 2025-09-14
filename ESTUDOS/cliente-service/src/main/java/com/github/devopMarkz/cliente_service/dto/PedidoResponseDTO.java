package com.github.devopMarkz.cliente_service.dto;

import com.github.devopMarkz.cliente_service.model.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {

    private Long id;
    private Long clienteId;
    private BigDecimal totalPedido;
    private StatusPedido status;
    private List<ItemPedidoResponseDTO> itens;

}