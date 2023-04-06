package com.example.cryptoparser.service;

import com.example.cryptoparser.model.dto.CurrencyInfoResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public interface CsvService {
    void getCsvBeanWriter(List<CurrencyInfoResponseDto> report,
                          HttpServletResponse httpServletResponse);
}
