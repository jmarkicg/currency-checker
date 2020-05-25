package hr.markic.currency.checker.repository;

import hr.markic.currency.checker.domain.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

//    @Query(value = "select c from currency_rate c " +
//                   "where c.from_currency_id = :fromCurrencyId and " +
//                   "c.to_currency_id = :toCurrencyId " +
//                   "order by c.date desc take 1", nativeQuery = true)
//    CurrencyRate findLatestCurrencyRate(@Param("fromCurrencyId") Long fromCurrencyId,
//                                        @Param("toCurrencyId") Long toCurrencyId);
//

    CurrencyRate findFirstByFromCurrencyCodeAndToCurrencyCodeOrderByDateDesc(String fromCurrency, String toCurrency);

    List<CurrencyRate> findAllByDateBetweenOrderByDateDesc(Date dateFrom, Date dateTo);
}
