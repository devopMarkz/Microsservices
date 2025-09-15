package com.github.devopMarkz.produto_service.controller;

import com.github.devopMarkz.produto_service.model.Produto;
import com.github.devopMarkz.produto_service.services.ProdutoService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
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

//    @Retry(name = "default")
//    @TimeLimiter(name = "timelimiter-busca-id", fallbackMethod = "fallbackBuscaId")
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

    //@Retry(name = "default")
    @PutMapping("/{id}/decrease-stock")
    public ResponseEntity<Void> retirarEstoque(@PathVariable Long id, @RequestBody Map<String, Integer> obj){
        produtoService.decreaseStock(id, obj);
        return ResponseEntity.noContent().build();
    }

   // @Retry(name = "default")
    @PutMapping("/{id}/return-stock")
    public ResponseEntity<Void> reporEstoque(@PathVariable Long id, @RequestBody Map<String, Integer> obj){
        produtoService.returnStock(id, obj);
        return ResponseEntity.noContent().build();
    }

    public String fallbackBuscaId(Throwable throwable){
        return "Fallback: Tempo limite da requisição estourado";
    }

}
