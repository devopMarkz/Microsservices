package io.github.devopMarkz.book_service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class ExchangeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionFactor;
    private BigDecimal conversionValue;
    private String environment;

    public ExchangeDTO() {
    }

    public ExchangeDTO(Long id, String from, String to, BigDecimal conversionFactor,
                       BigDecimal conversionValue, String environment) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.conversionFactor = conversionFactor;
        this.conversionValue = conversionValue;
        this.environment = environment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public BigDecimal getConversionValue() {
        return conversionValue;
    }

    public void setConversionValue(BigDecimal conversionValue) {
        this.conversionValue = conversionValue;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }


}
