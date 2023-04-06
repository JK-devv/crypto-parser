package com.example.cryptoparser.service;

import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.CurrencyInfoResponseDto;
import com.example.cryptoparser.model.mapper.CurrencyInfoMapper;
import com.example.cryptoparser.repository.CurrencyInfoRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
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
    public CurrencyInfo getWithMinLastPrice(
            String currencyMain) {
        return repository
                .findTopByCurrencyNameOrderByLastPrice(currencyMain);
    }

    @Override
    public CurrencyInfo getWithMaxLastPrice(
            String currencyMain) {
        return repository
                .findTopByCurrencyNameOrderByLastPriceDesc(currencyMain);
    }

    @Override
    public List<CurrencyInfo> getByCurrencyName(
            String currencyName, PageRequest pageRequest) {
        return repository
                .findByCurrencyName(currencyName, pageRequest);
    }

    @Override
    @PostConstruct
    @Scheduled(cron = "*/10 * * * * *")
    public void save() {
        repository.saveAll(client.getInfoFromApi().stream()
                .map(mapper::mapToModel)
                .toList());
    }

    @Override
    public List<CurrencyInfoResponseDto> getReport() {
        List<CurrencyInfoResponseDto> result = new ArrayList<>();
        List<CurrencyInfo> minLastPrice = repository.findWithMinLastPrice()
                .stream()
                .map(mapper::mapToModel)
                .toList();
        List<CurrencyInfo> maxLastPrice = repository.findWithMaxLastPrice()
                .stream()
                .map(mapper::mapToModel)
                .toList();

        for (CurrencyInfo currencyInfo : minLastPrice) {
            CurrencyInfoResponseDto responseDto = new CurrencyInfoResponseDto();
            responseDto.setCurrency(currencyInfo.getCurrencyName().name());
            responseDto.setMinPrice(currencyInfo.getLastPrice());
            for (CurrencyInfo info : maxLastPrice) {
                if (currencyInfo.getCurrencyName().equals(info.getCurrencyName())) {
                    responseDto.setMaxPrice(info.getLastPrice());
                }
            }
            result.add(responseDto);
        }
        return result;
    }
}
