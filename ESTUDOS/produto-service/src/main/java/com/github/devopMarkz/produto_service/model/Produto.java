package com.github.devopMarkz.produto_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_produtos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "preco", nullable = false)
    private Double preco;

    @Column(name = "quantidade_em_estoque", nullable = false)
    private Integer quantidadeEmEstoque;

    public void retirarEstoque(Integer quantidade){
        this.quantidadeEmEstoque -= quantidade;
    }

    public void reporEstoque(Integer quantidade){
        this.quantidadeEmEstoque += quantidade;
    }

}
