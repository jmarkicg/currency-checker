package hr.markic.currency.checker.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.markic.currency.checker.dto.CexRequestDTO;
import hr.markic.currency.checker.dto.CexResponseDTO;
import hr.markic.currency.checker.enums.CurrencyRateCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

/**
 * Generic CEX web client. Could be used for multiple endpoints depending on the given parameters.
 */
@Component
public class CexWebClient {

    private final Logger log = LoggerFactory.getLogger(CexWebClient.class);

    private ObjectMapper objectMapper;

    @Value("${currency-cex.url}")
    private String cexURL;

    @Value("${currency-cex.path}")
    private String cexPath;

    @Autowired
    public CexWebClient(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    public Optional<Object> performPOSTRequest(Object requestData, Class responseDataClass, String path){
        try {
            String stringJson = objectMapper.writeValueAsString(requestData);

            HttpClient httpClient = HttpClient.newBuilder().build();
            HttpRequest request =  HttpRequest.newBuilder()
                        .uri(new URI(cexURL + path))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(stringJson))
                        .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            Object responseData = (new ObjectMapper()).readValue(body, responseDataClass);
            return Optional.of(responseData);
        } catch (Exception e) {
            log.error("Failed to retrieve the data from CEX API.", e);
            return Optional.empty();
        }

    }

    public Optional<CexResponseDTO> performCurrencyExchange(CurrencyRateCodeEnum fromCurrency, CurrencyRateCodeEnum toCurrency){
        String path = cexPath.replace("{fromCurrency}", fromCurrency.name());
        path = path.replace("{toCurrency}", toCurrency.name());
        Optional<Object> response = performPOSTRequest(new CexRequestDTO("1"), CexResponseDTO.class, path);
        if (response.isPresent()){
            return Optional.of((CexResponseDTO) response.get());
        } else {
            return Optional.empty();
        }
    }

}

