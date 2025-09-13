package com.github.devopMarkz.produto_service.services;

import com.github.devopMarkz.produto_service.model.Produto;
import com.github.devopMarkz.produto_service.repositories.ProdutoRepository;
import com.github.devopMarkz.produto_service.repositories.specs.ProdutoSpecificationBuilder;
import jakarta.ws.rs.NotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarTodos(){
        return produtoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Produto buscarPorId(Long id){
        return produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Produto> buscarPorFiltros(Long id, String nome){
        Specification<Produto> specification = new ProdutoSpecificationBuilder()
                .withId(id)
                .withNome(nome)
                .build();

        return produtoRepository.findAll(specification);
    }

    @Transactional
    public Long salvar(Produto produto){
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoSalvo.getId();
    }

    @Transactional
    public void decreaseStock(Long id, Map<String, Integer> obj){
        if(!obj.containsKey("quantidade")){
            throw new IllegalArgumentException("Campo 'quantidade' precisa estar presente no corpo da requisição.");
        }

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado."));

        produto.retirarEstoque(obj.get("quantidade"));
    }

}
