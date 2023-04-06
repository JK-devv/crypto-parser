package com.example.cryptoparser.service;

import com.example.cryptoparser.model.dto.CurrencyInfoResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@Service
public class CsvServiceImpl implements CsvService {
    private final String[] CSV_HEADER = {"Currency", "MaxLastPrice", "MinLastPrice"};
    private final String[] NAME_MAPPING = {"currency", "maxPrice", "minPrice"};

    @Override
    @SneakyThrows
    public void getCsvBeanWriter(List<CurrencyInfoResponseDto> report,
                                 HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("text/csv");
        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(
                httpServletResponse.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        csvBeanWriter.writeHeader(CSV_HEADER);

        for (CurrencyInfoResponseDto dto : report) {
            csvBeanWriter.write(dto, NAME_MAPPING);
        }
        csvBeanWriter.close();
    }
}
