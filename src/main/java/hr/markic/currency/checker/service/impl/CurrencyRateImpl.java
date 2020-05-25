package hr.markic.currency.checker.service.impl;

import hr.markic.currency.checker.domain.CurrencyRate;
import hr.markic.currency.checker.dto.RateRequestDTO;
import hr.markic.currency.checker.enums.CurrencyRateCodeEnum;
import hr.markic.currency.checker.repository.CurrencyRateRepository;
import hr.markic.currency.checker.service.CurrencyRateService;
import hr.markic.currency.checker.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CurrencyRateImpl implements CurrencyRateService {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateImpl.class);

    private CurrencyRateRepository currencyRateRepository;

    private CurrencyService currencyService;

    public CurrencyRateImpl(CurrencyRateRepository currencyRateRepository,
                            CurrencyService currencyService){
        this.currencyRateRepository = currencyRateRepository;
        this.currencyService = currencyService;
    }

    @Override
    public CurrencyRate getLatestCurrencyRate(RateRequestDTO rateRequest) {
        return currencyRateRepository.findFirstByFromCurrencyCodeAndToCurrencyCodeOrderByDateDesc(
                rateRequest.getFromCurrencyCode(), rateRequest.getToCurrencyCode());
    }

    @Override
    public List<CurrencyRate> getHistoricalCurrencyRateData(RateRequestDTO historicalRateRequest) {
        return currencyRateRepository.findAllByDateBetweenOrderByDateDesc(
                historicalRateRequest.getDateFrom(), historicalRateRequest.getDateTo());
    }

    @Override
    public void saveCurrencyRate(CurrencyRate currencyRate) {
        currencyRateRepository.save(currencyRate);
    }

    @Override
    public void saveCurrencyRateForData(CurrencyRateCodeEnum fromCurrency,
                                        CurrencyRateCodeEnum toCurrency,
                                        BigDecimal amount) {
        
//        Double doubleValue = Double.parseDouble(amount);
//        BigDecimal bigDecimalValue = BigDecimal.valueOf(doubleValue);

        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setDate(new Date());
        currencyRate.setFromCurrency(currencyService.findByCode(CurrencyRateCodeEnum.BTC.name()));
        currencyRate.setToCurrency(currencyService.findByCode(CurrencyRateCodeEnum.USD.name()));
        currencyRate.setRate(amount);
        saveCurrencyRate(currencyRate);
    }
}
