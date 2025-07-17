package io.github.devopMarkz.book_service.controller;

import io.github.devopMarkz.book_service.dto.ExchangeDTO;
import io.github.devopMarkz.book_service.environment.InstanceInformationService;
import io.github.devopMarkz.book_service.model.Book;
import io.github.devopMarkz.book_service.proxy.ExchangeProxy;
import io.github.devopMarkz.book_service.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "Book Endpoint")
@RestController
@RequestMapping("/book-service")
public class BookController {

    @Autowired
    private InstanceInformationService instanceInformationService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ExchangeProxy exchangeProxy;

    @Operation(summary = "Find a specific book by your id")
    @GetMapping(value = "/{id}/{currency}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {
        String port = instanceInformationService.retrieveServerPort();

        var book = bookRepository.findById(id)
                .orElseThrow();

        ExchangeDTO exchangeDTO = exchangeProxy.getExchange(BigDecimal.valueOf(book.getPrice()), "USD", currency);

        book.setEnvironment("BOOK PORT:" + port + " EXCHANGE PORT: " + exchangeDTO.getEnvironment());
        book.setPrice(exchangeDTO.getConversionValue().doubleValue());
        book.setCurrency(currency);

        return book;
    }
}
