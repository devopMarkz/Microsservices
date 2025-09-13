package com.github.devopMarkz.produto_service.repositories.specs;

import com.github.devopMarkz.produto_service.model.Produto;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProdutoSpecificationBuilder {

    private List<Specification<Produto>> specificationList = new ArrayList<>();

    public ProdutoSpecificationBuilder withId(Long id){
        if(id != null){
            specificationList.add(ProdutoSpecifications.id(id));
        }
        return this;
    }

    public ProdutoSpecificationBuilder withNome(String nome){
        if(nome != null && !nome.isBlank()){
            specificationList.add(ProdutoSpecifications.nome(nome));
        }
        return this;
    }

    public ProdutoSpecificationBuilder withPreco(Double preco){
        if(preco != null){
            specificationList.add(ProdutoSpecifications.preco(preco));
        }
        return this;
    }

    public Specification<Produto> build(){
        if (specificationList.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        Specification<Produto> result = specificationList.getFirst();

        for (int i = 1; i < specificationList.size(); i++) {
            result = result.and(specificationList.get(i));
        }

        return result;
    }

}
