package hr.markic.currency.checker.service;

import hr.markic.currency.checker.domain.Currency;

public interface CurrencyService {

    Currency findByCode(String code);

}
