package hr.markic.currency.checker.service.impl;

import hr.markic.currency.checker.domain.Currency;
import hr.markic.currency.checker.repository.CurrencyRepository;
import hr.markic.currency.checker.service.CurrencyService;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository){
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }
}
