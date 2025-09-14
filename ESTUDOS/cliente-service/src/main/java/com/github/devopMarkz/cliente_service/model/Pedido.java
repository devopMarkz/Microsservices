package com.github.devopMarkz.cliente_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tb_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @EqualsAndHashCode.Include
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.PRIVATE)
    private List<ItemPedido> itens = new ArrayList<>();

    @Column(name = "total_pedido", nullable = false)
    private BigDecimal totalPedido;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.EM_PREPARO;

    public void adicionarItemPedido(ItemPedido itemPedido){
        this.itens.add(itemPedido);
    }

}
