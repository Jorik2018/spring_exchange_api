package org.demo.exchange.controller;

import org.demo.exchange.model.ExchangeRequest;
import org.demo.exchange.model.entity.ExchangeRate;
import org.demo.exchange.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exchangerate")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @PostMapping
    public ResponseEntity<ExchangeRate> createExchangeRate(@RequestBody ExchangeRate exchangeRate) {
        ExchangeRate savedExchangeRate = exchangeRateService.saveExchangeRate(exchangeRate);
        return new ResponseEntity<>(savedExchangeRate, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExchangeRate>> getAllExchangeRates() {
        List<ExchangeRate> exchangeRates = exchangeRateService.getAllExchangeRates();
        return new ResponseEntity<>(exchangeRates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRate> getExchangeRateById(@PathVariable Integer id) {
        Optional<ExchangeRate> exchangeRate = exchangeRateService.getExchangeRateById(id);
        return exchangeRate.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRate> updateExchangeRate(@PathVariable Integer id, @RequestBody ExchangeRate exchangeRate) {
        Optional<ExchangeRate> updatedExchangeRate = exchangeRateService.updateExchangeRate(id, exchangeRate);
        return updatedExchangeRate.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExchangeRate(@PathVariable Integer id) {
        boolean deleted = exchangeRateService.deleteExchangeRate(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/convert")
    public ResponseEntity<Double> convertCurrency(@RequestBody ExchangeRequest exchangeRequest) {
        double convertedValue = exchangeRateService.convert(exchangeRequest);
        return new ResponseEntity<>(convertedValue, HttpStatus.OK);
    }
}
