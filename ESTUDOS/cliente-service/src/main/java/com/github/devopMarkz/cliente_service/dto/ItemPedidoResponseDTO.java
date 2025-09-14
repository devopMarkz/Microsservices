package com.github.devopMarkz.cliente_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoResponseDTO {

    private Long id;
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal precoUnitario;

}