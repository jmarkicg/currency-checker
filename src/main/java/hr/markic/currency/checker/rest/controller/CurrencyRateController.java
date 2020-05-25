package hr.markic.currency.checker.rest.controller;

import hr.markic.currency.checker.domain.CurrencyRate;
import hr.markic.currency.checker.dto.CurrencyRateDTO;
import hr.markic.currency.checker.dto.RateRequestDTO;
import hr.markic.currency.checker.mapper.CurrencyRateMapper;
import hr.markic.currency.checker.rest.exception.InvalidRequestDataException;
import hr.markic.currency.checker.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/currency-rate")
public class CurrencyRateController {

    private CurrencyRateService currencyRateService;

    private CurrencyRateMapper currencyRateMapper;

    @Autowired
    CurrencyRateController(CurrencyRateService currencyRateService,
                           CurrencyRateMapper currencyRateMapper){
        this.currencyRateService = currencyRateService;
        this.currencyRateMapper = currencyRateMapper;
    }

    @GetMapping("/latest")
    public ResponseEntity<CurrencyRateDTO> getLatestRate(@RequestBody RateRequestDTO rateRequestDTO)
            throws InvalidRequestDataException {
        if (Objects.isNull(rateRequestDTO.getFromCurrencyCode()) ||
                Objects.isNull(rateRequestDTO.getToCurrencyCode())){
            throw new InvalidRequestDataException("From / to currency code not available.");
        }
        CurrencyRate currencyRate = currencyRateService.getLatestCurrencyRate(rateRequestDTO);
        return ResponseEntity.ok().body(currencyRateMapper.toDTO(currencyRate));
    }

    @GetMapping("/historical")
    public ResponseEntity<List<CurrencyRateDTO>> getHistoricalRates(@RequestBody RateRequestDTO rateRequestDTO)
            throws InvalidRequestDataException {
        if (Objects.isNull(rateRequestDTO.getFromCurrencyCode()) ||
                Objects.isNull(rateRequestDTO.getToCurrencyCode())){
            throw new InvalidRequestDataException("From / to currency code not available.");
        }
        if ( Objects.isNull(rateRequestDTO.getDateFrom()) ||
                Objects.isNull(rateRequestDTO.getDateTo())){
            throw new InvalidRequestDataException("Date from / to not available.");
        }
        List<CurrencyRate> currencyList = currencyRateService.getHistoricalCurrencyRateData(rateRequestDTO);
        List<CurrencyRateDTO> currencyRateDTOList = currencyList.stream().
                map(contactType -> currencyRateMapper.toDTO(contactType)).collect(Collectors.toList());

        return ResponseEntity.ok().body(currencyRateDTOList);
    }

}

