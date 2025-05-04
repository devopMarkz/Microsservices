package br.com.erudio.cambio_service.controller;

import br.com.erudio.cambio_service.model.Cambio;
import br.com.erudio.cambio_service.repository.CambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/cambio-service")
public class CambioController {

    @Autowired
    private Environment environment;

    @Autowired
    private CambioRepository cambioRepository;

    @GetMapping("/{amount}/{from}/{to}")
    public Cambio getCambio(
            @PathVariable(value = "amount") BigDecimal amount,
            @PathVariable(value = "from") String from,
            @PathVariable(value = "to") String to
            ) {
        var cambio = cambioRepository.findByFromAndTo(from, to)
                .orElseThrow(() -> new RuntimeException("Cambio não encontrado"));
        var port = environment.getProperty("local.server.port");
        cambio.setEnvironment(port);
        BigDecimal conversionFactor = cambio.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        cambio.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        return cambio;
    }

}
