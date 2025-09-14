package com.github.devopMarkz.cliente_service.model;

public enum StatusPedido {

    EM_PREPARO,
    CONFIRMADO,
    CANCELADO;

    public boolean podeConfirmarPedido(){
        return StatusPedido.EM_PREPARO.equals(this);
    }
}
