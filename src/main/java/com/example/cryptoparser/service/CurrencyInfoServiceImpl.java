package com.example.cryptoparser.service;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.ResponseCurrencyInfoDto;
import com.example.cryptoparser.model.mapper.CurrencyInfoMapper;
import com.example.cryptoparser.repository.CurrencyInfoRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyInfoServiceImpl implements CurrencyInfoService {
    private final CurrencyInfoRepository repository;
    private final CurrencyInfoMapper mapper;
    private final HttpClient client;

    @Override
    public CurrencyInfo getCurrencyInfoWithMinLastPrice(
            String currencyMain) {
        return repository
                .findTopByCurrencyMainOrderByLastPrice(currencyMain);
    }

    @Override
    public CurrencyInfo getCurrencyInfoWithMaxLastPrice(
            String currencyMain) {
        return repository
                .findTopByCurrencyMainOrderByLastPriceDesc(currencyMain);
    }

    @Override
    public List<CurrencyInfo> getByCurrencyMainName(
            String currencyMain, PageRequest pageRequest) {
        return repository
                .findByCurrencyMain(currencyMain, pageRequest);
    }

    @Override
    @PostConstruct
    @Scheduled(cron = "*/10 * * * * *")
    public void save() {
        repository.saveAll(client.getInfoFromApi()
                .stream()
                .map(mapper::mapToModel)
                .collect(Collectors.toList()));
    }

    @Override
    public List<ResponseCurrencyInfoDto> getReport() {
        List<ResponseCurrencyInfoDto> result = new ArrayList<>();
        Currency[] currencies = Currency.values();
        for (Currency currency : currencies) {
            CurrencyInfo maxLastPrice = repository
                    .findTopByCurrencyMainOrderByLastPriceDesc(currency.name());
            CurrencyInfo minLastPrice = repository
                    .findTopByCurrencyMainOrderByLastPrice(currency.name());
            result.add(ResponseCurrencyInfoDto.builder()
                    .currency(currency.name())
                    .maxPrice(maxLastPrice.getLastPrice())
                    .minPrice(minLastPrice.getLastPrice())
                    .build());
        }
        return result;
    }
}
