package com.example.cryptoparser.controller;

import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.service.CsvService;
import com.example.cryptoparser.service.CurrencyInfoService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptocurrencies")
public class CurrencyInfoController {
    private final CurrencyInfoService service;
    private final CsvService csvService;
    private final String DEFAULT_SORT_BY = "lastPrice";

    @GetMapping("/minprice")
    public CurrencyInfo getWithMinLastPrice(@RequestParam(value = "currencyName")
                                            String currencyName) {
        return service.getWithMinLastPrice(currencyName);
    }

    @GetMapping("/maxprice")
    public CurrencyInfo getWithMaxLastPrice(@RequestParam(value = "currencyName")
                                            String currencyName) {
        return service.getWithMaxLastPrice(currencyName);
    }

    @GetMapping
    public List<CurrencyInfo> getCurrency(@RequestParam(value = "currencyName")
                                          String currencyName,
                                          @RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return service.getByCurrencyName(currencyName,
                PageRequest.of(page, size, Sort.by(
                        Sort.Direction.ASC, DEFAULT_SORT_BY)));
    }

    @SneakyThrows
    @GetMapping("/csv")
    public void getCsv(HttpServletResponse httpServletResponse) {
        csvService.getCsvBeanWriter(service.getReport(), httpServletResponse);
    }
}
