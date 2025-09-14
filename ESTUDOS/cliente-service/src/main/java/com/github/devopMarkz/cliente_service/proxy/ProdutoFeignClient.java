package com.github.devopMarkz.cliente_service.proxy;

import com.github.devopMarkz.cliente_service.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "produto-service")
public interface ProdutoFeignClient {

    @GetMapping("/produtos/{id}")
    ProdutoDTO buscarPorId(@PathVariable Long id);

    @PutMapping("/produtos/{id}/decrease-stock")
    void retirarEstoque(@PathVariable Long id, @RequestBody Map<String, Integer> obj);

    @PutMapping("/produtos/{id}/return-stock")
    void reporEstoque(@PathVariable Long id, @RequestBody Map<String, Integer> obj);

}
