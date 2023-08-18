package org.demo.exchange.service;

import java.util.List;
import java.util.Optional;

import org.demo.exchange.model.ExchangeRequest;
import org.demo.exchange.model.entity.ExchangeRate;
import org.demo.exchange.repository.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public double convert(ExchangeRequest request) {
        ExchangeRate originRate = exchangeRateRepository.findByType(request.getTypeOrigin());
        ExchangeRate destinyRate = exchangeRateRepository.findByType(request.getTypeDestiny());

        if (originRate == null || destinyRate == null) {
            throw new IllegalArgumentException("Invalid currency types");
        }

        double convertedValue = (request.getValue() / originRate.getValue()) * destinyRate.getValue();
        return convertedValue;
    }

    public boolean deleteExchangeRate(Integer id) {
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findById(id);
        if (optionalExchangeRate.isPresent()) {
            exchangeRateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<ExchangeRate> updateExchangeRate(Integer id, ExchangeRate exchangeRate) {
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepository.findById(id);
        if (optionalExchangeRate.isPresent()) {
            ExchangeRate existingExchangeRate = optionalExchangeRate.get();
            existingExchangeRate.setType(exchangeRate.getType());
            existingExchangeRate.setValue(exchangeRate.getValue());
            exchangeRateRepository.save(existingExchangeRate);
            return Optional.of(existingExchangeRate);
        }
        return Optional.empty();
    }

    public Optional<ExchangeRate> getExchangeRateById(Integer id) {
        return exchangeRateRepository.findById(id);
    }

    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateRepository.findAll();
    }

    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) {
        return exchangeRateRepository.save(exchangeRate);
    }
}
