package com.github.devopMarkz.produto_service.controller;

import com.github.devopMarkz.produto_service.model.Produto;
import com.github.devopMarkz.produto_service.services.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> buscarPorFiltro(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "nome", required = false) String nome
    ){
        return ResponseEntity.ok(produtoService.buscarPorFiltros(id, nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody Produto produto){
        Long id = produtoService.salvar(produto);
        URI location = URI.create("/produtos/" + id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}/decrease-stock")
    public ResponseEntity<Void> retirarEstoque(@PathVariable Long id, @RequestBody Map<String, Integer> obj){
        produtoService.decreaseStock(id, obj);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/return-stock")
    public ResponseEntity<Void> reporEstoque(@PathVariable Long id, @RequestBody Map<String, Integer> obj){
        produtoService.returnStock(id, obj);
        return ResponseEntity.noContent().build();
    }

}
