package com.example.cryptoparser.service;

import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.CurrencyInfoResponseDto;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface CurrencyInfoService {
    CurrencyInfo getWithMinLastPrice(String currencyMainName);

    CurrencyInfo getWithMaxLastPrice(String currencyMainName);

    List<CurrencyInfo> getByCurrencyName(String currencyName, PageRequest pageRequest);

    void save();

    List<CurrencyInfoResponseDto> getReport();

}
