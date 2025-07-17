package io.github.devopMarkz.book_service.controller;

import io.github.resilience4j.retry.annotation.Retry;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Tag(name = "Foo Bar Endpoint")
@RestController
@RequestMapping("/book-service")
public class FooBarController {

    private Logger log = LoggerFactory.getLogger(FooBarController.class);

    @GetMapping("/foo-bar")
    @Retry(name = "default", fallbackMethod = "fallbackM")
    public String fooBar(){
        log.info("Request to foo-bar is received!");
        var response = new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);
        // return "Foo Bar";
        return response.getBody();
    }

    public String fallbackM(Throwable e){
        log.info("Request to fallback-m is received!");
        return "Eu tenteeeei!";
    }

}
