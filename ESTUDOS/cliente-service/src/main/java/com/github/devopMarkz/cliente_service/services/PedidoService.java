package com.github.devopMarkz.cliente_service.services;

import com.github.devopMarkz.cliente_service.dto.ItemPedidoRequest;
import com.github.devopMarkz.cliente_service.dto.ItemPedidoResponseDTO;
import com.github.devopMarkz.cliente_service.dto.PedidoResponseDTO;
import com.github.devopMarkz.cliente_service.dto.ProdutoDTO;
import com.github.devopMarkz.cliente_service.model.Cliente;
import com.github.devopMarkz.cliente_service.model.ItemPedido;
import com.github.devopMarkz.cliente_service.model.Pedido;
import com.github.devopMarkz.cliente_service.model.StatusPedido;
import com.github.devopMarkz.cliente_service.proxy.ProdutoFeignClient;
import com.github.devopMarkz.cliente_service.repositories.ClienteRepository;
import com.github.devopMarkz.cliente_service.repositories.ItemPedidoRepository;
import com.github.devopMarkz.cliente_service.repositories.PedidoRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoFeignClient produtoFeignClient;

    public PedidoService(ClienteRepository clienteRepository,
                         PedidoRepository pedidoRepository,
                         ItemPedidoRepository itemPedidoRepository,
                         ProdutoFeignClient produtoFeignClient) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoFeignClient = produtoFeignClient;
    }

    @Transactional
    public void criarPedido(Long clienteId, List<ItemPedidoRequest> itens) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado."));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        BigDecimal totalPedido = BigDecimal.ZERO;

        for (ItemPedidoRequest pedidoRequest : itens) {
            ProdutoDTO produtoDTO = produtoFeignClient.buscarPorId(pedidoRequest.getProdutoId());

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProdutoId(produtoDTO.getId());
            itemPedido.setQuantidade(pedidoRequest.getQuantidade());
            itemPedido.setPrecoUnitario(produtoDTO.getPreco());

            itemPedido.setPedido(pedido);

            BigDecimal precoUnitario = itemPedido.getPrecoUnitario();
            BigDecimal quantidade = BigDecimal.valueOf(itemPedido.getQuantidade());
            BigDecimal totalItem = precoUnitario.multiply(quantidade);

            totalPedido = totalPedido.add(totalItem);

            pedido.adicionarItemPedido(itemPedido);
        }

        pedido.setTotalPedido(totalPedido);

        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoPorId(Long clienteId, Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        verificarPedidoCliente(pedido, clienteId);

        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                .map(item -> new ItemPedidoResponseDTO(
                        item.getId(),
                        item.getProdutoId(),
                        item.getQuantidade(),
                        item.getPrecoUnitario()))
                .collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getTotalPedido(),
                pedido.getStatus(),
                itensDTO
        );
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarTodos(Long clienteId) {
        List<Pedido> pedidos = pedidoRepository.buscarTodos(clienteId);

        List<PedidoResponseDTO> pedidosDTO = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                    .map(item -> new ItemPedidoResponseDTO(
                            item.getId(),
                            item.getProdutoId(),
                            item.getQuantidade(),
                            item.getPrecoUnitario()
                    ))
                    .toList();

            PedidoResponseDTO pedidoDTO = new PedidoResponseDTO(
                    pedido.getId(),
                    pedido.getCliente().getId(),
                    pedido.getTotalPedido(),
                    pedido.getStatus(),
                    itensDTO
            );

            pedidosDTO.add(pedidoDTO);
        }

        return pedidosDTO;
    }

    @Transactional
    public void confirmarPedido(Long clienteId, Long id){
        Pedido pedido = pedidoRepository.findWithItens(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        verificarPedidoCliente(pedido, clienteId);

        if(!pedido.getStatus().podeConfirmarPedido()){
            throw new IllegalArgumentException("Pedido não pode ser confirmado. Status atual: " + pedido.getStatus());
        }

        Map<Long, Integer> objCache = new HashMap<>();

        try{
            for(ItemPedido item : pedido.getItens()){
                Map<String, Integer> obj = new HashMap<>();
                obj.put("quantidade", item.getQuantidade());
                produtoFeignClient.retirarEstoque(item.getProdutoId(), obj);
                objCache.put(item.getProdutoId(), item.getQuantidade());
            }

             pedido.setStatus(StatusPedido.CONFIRMADO);
        } catch (Exception e){
            for (Map.Entry<Long, Integer> entry : objCache.entrySet()){
                Map<String, Integer> obj = new HashMap<>();
                obj.put("quantidade", entry.getValue());
                produtoFeignClient.reporEstoque(entry.getKey(), obj);
            }

           throw new IllegalArgumentException("Erro ao retirar estoque.", e);
        }
    }

    public void verificarPedidoCliente(Pedido pedido, Long clienteId){
        if(!Objects.equals(clienteId, pedido.getCliente().getId())){
            throw new IllegalArgumentException("Pedido não pertence ao cliente " + clienteId);
        }
    }
}
