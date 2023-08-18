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
}
