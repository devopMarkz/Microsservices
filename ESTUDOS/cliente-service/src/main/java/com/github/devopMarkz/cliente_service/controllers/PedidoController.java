package com.github.devopMarkz.cliente_service.controllers;

import com.github.devopMarkz.cliente_service.dto.ItemPedidoRequest;
import com.github.devopMarkz.cliente_service.dto.PedidoResponseDTO;
import com.github.devopMarkz.cliente_service.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes/{clienteId}/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Void> criarPedido(@PathVariable Long clienteId, @RequestBody @Valid List<ItemPedidoRequest> itens) {
        pedidoService.criarPedido(clienteId, itens);
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long clienteId, @PathVariable Long id) {
        PedidoResponseDTO responseDTO = pedidoService.buscarPedidoPorId(clienteId, id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> buscarPorId(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> responseDTOList = pedidoService.buscarTodos(clienteId);
        return ResponseEntity.ok(responseDTOList);
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<PedidoResponseDTO> confirmarPedido(@PathVariable Long clienteId, @PathVariable Long id) {
        pedidoService.confirmarPedido(clienteId, id);
        return ResponseEntity.noContent().build();
    }
}
