package com.example.cryptoparser.model.mapper;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.ApiResponseCurrencyInfo;
import org.springframework.stereotype.Component;

@Component
public class CurrencyInfoMapper {
    public CurrencyInfo mapToModel(ApiResponseCurrencyInfo apiResponseCurrencyInfo) {
        return CurrencyInfo.builder()
                .currencyMain(Currency.valueOf(apiResponseCurrencyInfo.getCurrencyMain()))
                .lastPrice(apiResponseCurrencyInfo.getPrice())
                .build();
    }
}
