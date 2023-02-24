package com.example.cryptoparser.service;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.dto.ApiResponseCurrencyInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class HttpClient {
    private static final String URL = "https://cex.io/api/last_price/{param}/USD";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public List<ApiResponseCurrencyInfo> getInfoFromApi() {

        Currency[] values = Currency.values();
        List<ApiResponseCurrencyInfo> apiResponseCurrencyInfos = new ArrayList<>();
        for (Currency currency : values) {

            Map<String, String> params = new HashMap<>();
            params.put("param", currency.name());
            String uriString = UriComponentsBuilder.fromUriString(URL)
                    .buildAndExpand(params).toUriString();
            HttpGet request = new HttpGet(uriString);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                apiResponseCurrencyInfos.add(objectMapper.readValue(
                        response.getEntity().getContent(),
                        ApiResponseCurrencyInfo.class));
            } catch (IOException e) {
                throw new RuntimeException("Can`t fetch into from URL " + uriString);
            }
        }
        return apiResponseCurrencyInfos;
    }
}
