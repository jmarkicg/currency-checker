package hr.markic.currency.checker.scheduler;

import hr.markic.currency.checker.dto.CexResponseDTO;
import hr.markic.currency.checker.enums.CurrencyRateCodeEnum;
import hr.markic.currency.checker.rest.client.CexWebClient;
import hr.markic.currency.checker.service.CurrencyRateService;
import hr.markic.currency.checker.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CurrencyRateScheduler {

    private final Logger log = LoggerFactory.getLogger(CurrencyRateScheduler.class);

    CexWebClient cexWebClient;

    CurrencyRateService currencyRateService;

    CurrencyService currencyService;

    @Autowired
    public CurrencyRateScheduler(CurrencyRateService currencyRateService,
                                 CurrencyService currencyService,
                                 CexWebClient cexWebClient){
        this.currencyRateService = currencyRateService;
        this.currencyService = currencyService;
        this.cexWebClient = cexWebClient;
    }

    @Scheduled(fixedRateString = "${currency-cex.rate.ms}")
    public void importBitcoinToUSDRate(){
        Optional<CexResponseDTO> cexResponse = cexWebClient.performCurrencyExchange(
                CurrencyRateCodeEnum.BTC,
                CurrencyRateCodeEnum.USD);

        if (!cexResponse.isPresent() || Objects.isNull(cexResponse.get().getAmnt())){
            log.error("Failed to retrieve the exchange rate from BTC to USD");
            return;
        }

        currencyRateService.saveCurrencyRateForData(
                CurrencyRateCodeEnum.BTC, CurrencyRateCodeEnum.USD, cexResponse.get().getAmnt());
    }
}
