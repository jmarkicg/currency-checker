package hr.markic.currency.checker.mapper;


import hr.markic.currency.checker.domain.Currency;
import hr.markic.currency.checker.dto.CurrencyDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDTO toDTO(Currency currency);

    Currency toEntity(CurrencyDTO currencyDTO);
}
