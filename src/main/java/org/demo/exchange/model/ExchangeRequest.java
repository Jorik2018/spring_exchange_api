package org.demo.exchange.model;

import lombok.Data;

@Data
public class ExchangeRequest {
    private String typeOrigin;
    private String typeDestiny;
    private double value;
}
