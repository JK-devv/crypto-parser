package com.example.cryptoparser.model.mapper;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.CurrencyInfoAggregate;
import com.example.cryptoparser.model.dto.CurrencyInfoApiDto;
import org.springframework.stereotype.Component;

@Component
public class CurrencyInfoMapper {
    public CurrencyInfo mapToModel(CurrencyInfoApiDto currencyInfoApiDto) {
        return CurrencyInfo.builder()
                .currencyName(Currency.valueOf(currencyInfoApiDto.getCurrency()))
                .lastPrice(currencyInfoApiDto.getPrice())
                .build();
    }

    public CurrencyInfo mapToModel(CurrencyInfoAggregate currencyInfoAggregate) {
        return CurrencyInfo.builder()
                .currencyName(Currency.valueOf(currencyInfoAggregate.getId()))
                .lastPrice(currencyInfoAggregate.getPrice())
                .build();
    }
}
