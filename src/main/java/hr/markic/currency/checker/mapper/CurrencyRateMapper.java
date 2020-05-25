package hr.markic.currency.checker.mapper;


import hr.markic.currency.checker.domain.CurrencyRate;
import hr.markic.currency.checker.dto.CurrencyRateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CurrencyMapper.class})
public interface CurrencyRateMapper {

    @Mapping(source = "fromCurrency.code", target = "fromCurrencyCode")
    @Mapping(source = "toCurrency.code", target = "toCurrencyCode")
    CurrencyRateDTO toDTO(CurrencyRate currencyRate);

////    @Mapping(source = "userId", target = "user.id")
//    Currency toEntity(CurrencyRateDTO currencyRateDTO);
}
