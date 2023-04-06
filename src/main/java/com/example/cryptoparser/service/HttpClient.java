package com.example.cryptoparser.service;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.dto.CurrencyInfoApiDto;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class HttpClient {
    @Value("${url}")
    private String url;
    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public List<CurrencyInfoApiDto> getInfoFromApi() {
        Currency[] values = Currency.values();
        List<CurrencyInfoApiDto> currencyInfoApiDtos = new ArrayList<>();
        for (Currency currency : values) {
            Map<String, String> params = new HashMap<>();
            params.put("param", currency.name());
            String uriString = UriComponentsBuilder.fromUriString(url)
                    .buildAndExpand(params).toUriString();
            HttpGet request = new HttpGet(uriString);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                currencyInfoApiDtos.add(objectMapper.readValue(
                        response.getEntity().getContent(),
                        CurrencyInfoApiDto.class));
            } catch (IOException e) {
                throw new RuntimeException("Can`t fetch into from URL " + uriString);
            }
        }
        return currencyInfoApiDtos;
    }
}
