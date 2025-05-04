package io.github.devopMarkz.book_service.controller;

import io.github.devopMarkz.book_service.model.Book;
import io.github.devopMarkz.book_service.proxy.CambioProxy;
import io.github.devopMarkz.book_service.repository.BookRepository;
import io.github.devopMarkz.book_service.response.Cambio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CambioProxy cambioProxy;

    @GetMapping("/{id}/{currency}")
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        var book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);

        var port = environment.getProperty("local.server.port");

        book.setEnvironment(port);

        book.setPrice(cambio.getConvertedValue());

        return book;
    }

    /* @GetMapping("/{id}/{currency}")
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        var book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        HashMap<String, String> params = new HashMap<>();
        params.put("amount", book.getPrice().toString());
        params.put("from", "USD");
        params.put("to", currency);

        var response = new RestTemplate().getForEntity("http://localhost:8000/cambio-service/{amount}/{from}/{to}",
                Cambio.class,
                params);

        var cambio = response.getBody();

        var port = environment.getProperty("local.server.port");

        book.setEnvironment(port);

        book.setPrice(cambio.getConvertedValue());

        return book;
    }*/

}
