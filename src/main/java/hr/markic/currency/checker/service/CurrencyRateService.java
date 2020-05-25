package hr.markic.currency.checker.service;

import hr.markic.currency.checker.domain.CurrencyRate;
import hr.markic.currency.checker.dto.RateRequestDTO;
import hr.markic.currency.checker.enums.CurrencyRateCodeEnum;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyRateService {

    CurrencyRate getLatestCurrencyRate(RateRequestDTO currencyRateRequest);

    List<CurrencyRate> getHistoricalCurrencyRateData(RateRequestDTO rateRequest);

    void saveCurrencyRate(CurrencyRate currencyRate);

    void saveCurrencyRateForData(CurrencyRateCodeEnum fromCurrency,
                                 CurrencyRateCodeEnum toCurrency,
                                 BigDecimal amnt);
}
