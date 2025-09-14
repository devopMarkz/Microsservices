package com.github.devopMarkz.cliente_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoRequest {

    @NotNull(message = "ID do produto deve ser informado.")
    private Long produtoId;

    @NotNull(message = "Quantidade deve ser informada.")
    @Positive(message = "Quantidade deve ser maior que 0.")
    private Integer quantidade;

}
