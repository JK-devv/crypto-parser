package com.example.cryptoparser.controller;

import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.ResponseCurrencyInfoDto;
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
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptocurrencies")
public class CurrencyInfoController {
    private final CurrencyInfoService service;
    private final String sortBy = "lastPrice";

    @GetMapping("/minprice")
    public CurrencyInfo getWithMinLastPrice(@RequestParam(value = "name")
                                            String currencyMainName) {
        return service.getCurrencyInfoWithMinLastPrice(currencyMainName);
    }

    @GetMapping("/maxprice")
    public CurrencyInfo getWithMaxLastPrice(@RequestParam(value = "name")
                                            String currencyMainName) {
        return service.getCurrencyInfoWithMaxLastPrice(currencyMainName);
    }

    @GetMapping
    public List<CurrencyInfo> getCurrency(@RequestParam(value = "name")
                                          String currencyMainName,
                                          @RequestParam(defaultValue = "0") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return service.getByCurrencyMainName(currencyMainName,
                PageRequest.of(page, size, Sort.by(
                        Sort.Direction.ASC, sortBy)));
    }

    @SneakyThrows
    @GetMapping("/csv")
    public void getCsv(HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("text/csv");
        List<ResponseCurrencyInfoDto> report = service.getReport();
        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(
                httpServletResponse.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Currency", "MaxLastPrice", "MinLastPrice"};
        String[] nameMapping = {"currency", "maxPrice", "minPrice"};
        csvBeanWriter.writeHeader(csvHeader);

        for (ResponseCurrencyInfoDto dto : report) {
            csvBeanWriter.write(dto, nameMapping);
        }
        csvBeanWriter.close();
    }
}
