package com.github.devopMarkz.produto_service.repositories.specs;

import com.github.devopMarkz.produto_service.model.Produto;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecifications {

    public static Specification<Produto> id(Long id){
        return (root, query, criteriaBuilder) -> (id == null)
                ? null
                : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Produto> nome(String nome){
        return (root, query, criteriaBuilder) -> (nome == null || nome.isEmpty())
                ? null
                : criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Produto> preco(Double preco){
        return (root, query, criteriaBuilder) -> (preco == null)
                ? null
                : criteriaBuilder.equal(root.get("preco"), preco);
    }

}
