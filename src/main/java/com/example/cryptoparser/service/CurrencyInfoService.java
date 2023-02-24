package com.example.cryptoparser.service;

import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.ResponseCurrencyInfoDto;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface CurrencyInfoService {
    CurrencyInfo getCurrencyInfoWithMinLastPrice(String currencyMainName);

    CurrencyInfo getCurrencyInfoWithMaxLastPrice(String currencyMainName);

    List<CurrencyInfo> getByCurrencyMainName(String currencyMainName, PageRequest pageRequest);

    void save();

    List<ResponseCurrencyInfoDto> getReport();

}
