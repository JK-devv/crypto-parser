package com.example.cryptoparser.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.CurrencyInfoAggregate;
import com.example.cryptoparser.model.dto.CurrencyInfoApiDto;
import com.example.cryptoparser.model.dto.CurrencyInfoResponseDto;
import com.example.cryptoparser.model.mapper.CurrencyInfoMapper;
import com.example.cryptoparser.repository.CurrencyInfoRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(classes = CurrencyInfoServiceImpl.class)
class CurrencyInfoServiceImplTest {
    @MockBean
    private CurrencyInfoRepository repository;
    @MockBean
    private CurrencyInfoMapper mapper;
    @MockBean
    private HttpClient client;
    @Autowired
    private CurrencyInfoServiceImpl service;

    @Test
    void getCurrencyInfoWithMinLastPrice() {
        CurrencyInfo expectedBtc = CurrencyInfo.builder()
                .currencyName(Currency.BTC)
                .lastPrice(23740.7)
                .build();
        CurrencyInfo expectedEth = CurrencyInfo.builder()
                .currencyName(Currency.ETH)
                .lastPrice(1614.0)
                .build();
        CurrencyInfo expectedXrp = CurrencyInfo.builder()
                .currencyName(Currency.XRP)
                .lastPrice(0.39008)
                .build();

        when(repository.findTopByCurrencyNameOrderByLastPrice("BTC"))
                .thenReturn(expectedBtc);
        when(repository.findTopByCurrencyNameOrderByLastPrice("ETH"))
                .thenReturn(expectedEth);
        when(repository.findTopByCurrencyNameOrderByLastPrice("XRP"))
                .thenReturn(expectedXrp);

        CurrencyInfo actualBtc =
                service.getWithMinLastPrice("BTC");
        CurrencyInfo actualEth =
                service.getWithMinLastPrice("ETH");
        CurrencyInfo actualXrp =
                service.getWithMinLastPrice("XRP");

        Assertions.assertEquals(expectedBtc.getLastPrice(), actualBtc.getLastPrice());
        Assertions.assertEquals(expectedEth.getLastPrice(), actualEth.getLastPrice());
        Assertions.assertEquals(expectedXrp.getLastPrice(), actualXrp.getLastPrice());

        verify(repository, times(3))
                .findTopByCurrencyNameOrderByLastPrice(any());
    }

    @Test
    void getCurrencyInfoWithMaxLastPrice() {
        CurrencyInfo expectedBtc = CurrencyInfo.builder()
                .currencyName(Currency.BTC)
                .lastPrice(24738.6)
                .build();
        CurrencyInfo expectedEth = CurrencyInfo.builder()
                .currencyName(Currency.ETH)
                .lastPrice(1684.28)
                .build();
        CurrencyInfo expectedXrp = CurrencyInfo.builder()
                .currencyName(Currency.XRP)
                .lastPrice(0.39389)
                .build();

        when(repository.findTopByCurrencyNameOrderByLastPriceDesc("BTC"))
                .thenReturn(expectedBtc);
        when(repository.findTopByCurrencyNameOrderByLastPriceDesc("ETH"))
                .thenReturn(expectedEth);
        when(repository.findTopByCurrencyNameOrderByLastPriceDesc("XRP"))
                .thenReturn(expectedXrp);

        CurrencyInfo actualBtc =
                service.getWithMaxLastPrice("BTC");
        CurrencyInfo actualEth =
                service.getWithMaxLastPrice("ETH");
        CurrencyInfo actualXrp =
                service.getWithMaxLastPrice("XRP");

        Assertions.assertEquals(expectedBtc.getLastPrice(), actualBtc.getLastPrice());
        Assertions.assertEquals(expectedEth.getLastPrice(), actualEth.getLastPrice());
        Assertions.assertEquals(expectedXrp.getLastPrice(), actualXrp.getLastPrice());

        verify(repository, times(3))
                .findTopByCurrencyNameOrderByLastPriceDesc(any());
    }

    @Test
    void getByCurrencyMainName() {
        List<CurrencyInfo> expected = new ArrayList<>();
        CurrencyInfo currencyInfo1 = CurrencyInfo.builder()
                .currencyName(Currency.BTC)
                .lastPrice(23740.7)
                .build();
        CurrencyInfo currencyInfo2 = CurrencyInfo.builder()
                .currencyName(Currency.BTC)
                .lastPrice(24006.5)
                .build();
        CurrencyInfo currencyInfo3 = CurrencyInfo.builder()
                .currencyName(Currency.BTC)
                .lastPrice(24573.7)
                .build();
        expected.add(currencyInfo1);
        expected.add(currencyInfo2);
        expected.add(currencyInfo3);

        when(repository.findByCurrencyName("BTC",
                PageRequest.of(0, 3)))
                .thenReturn(expected);
        List<CurrencyInfo> actual = service
                .getByCurrencyName("BTC",
                        PageRequest.of(0, 3));

        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0), actual.get(0));

        verify(repository, times(1))
                .findByCurrencyName(any(), any());
    }

    @Test
    void save() {
        List<CurrencyInfoApiDto> responseCurrencyInfos =
                new ArrayList<>();
        CurrencyInfoApiDto currencyInfoApiDto =
                CurrencyInfoApiDto.builder()
                        .currency("BTC")
                        .price(24738.5)
                        .build();
        CurrencyInfoApiDto currencyInfoApiDto1 =
                CurrencyInfoApiDto.builder()
                        .currency("ETH")
                        .price(1684.28)
                        .build();
        CurrencyInfoApiDto currencyInfoApiDto2 =
                CurrencyInfoApiDto.builder()
                        .currency("XRP")
                        .price(0.39389)
                        .build();
        responseCurrencyInfos.add(currencyInfoApiDto2);
        responseCurrencyInfos.add(currencyInfoApiDto1);
        responseCurrencyInfos.add(currencyInfoApiDto);
        List<CurrencyInfo> saved = new ArrayList<>();
        CurrencyInfo btc = CurrencyInfo.builder()
                .currencyName(Currency.BTC)
                .lastPrice(24738.5)
                .build();
        CurrencyInfo xrp = CurrencyInfo.builder()
                .currencyName(Currency.XRP)
                .lastPrice(0.39389)
                .build();
        CurrencyInfo eth = CurrencyInfo.builder()
                .currencyName(Currency.ETH)
                .lastPrice(1684.28)
                .build();
        saved.add(btc);
        saved.add(eth);
        saved.add(xrp);
        when(repository.saveAll(any())).thenReturn(saved);
        when(client.getInfoFromApi()).thenReturn(responseCurrencyInfos);
        when(mapper.mapToModel(any(CurrencyInfoApiDto.class))).thenCallRealMethod();
        service.save();
        verify(repository, times(1)).saveAll(any());
        verify(client, times(1)).getInfoFromApi();
        verify(mapper, times(3)).mapToModel(any(CurrencyInfoApiDto.class));
    }

    @Test
    void getReport() {
        CurrencyInfoAggregate btcMax = CurrencyInfoAggregate.builder()
                .id(Currency.BTC.name())
                .price(24738.6)
                .build();
        CurrencyInfoAggregate ethMax = CurrencyInfoAggregate.builder()
                .id(Currency.ETH.name())
                .price(1684.28)
                .build();
        CurrencyInfoAggregate xrpMax = CurrencyInfoAggregate.builder()
                .id(Currency.XRP.name())
                .price(0.39389)
                .build();
        List<CurrencyInfoAggregate> max = new ArrayList<>();
        max.add(btcMax);
        max.add(ethMax);
        max.add(xrpMax);
        CurrencyInfoAggregate btcMin = CurrencyInfoAggregate.builder()
                .id(Currency.BTC.name())
                .price(23740.7)
                .build();
        CurrencyInfoAggregate ethMin = CurrencyInfoAggregate.builder()
                .id(Currency.ETH.name())
                .price(1614.0)
                .build();
        CurrencyInfoAggregate xrpMin = CurrencyInfoAggregate.builder()
                .id(Currency.XRP.name())
                .price(0.39008)
                .build();
        List<CurrencyInfoAggregate> min = new ArrayList<>();
        min.add(xrpMin);
        min.add(ethMin);
        min.add(btcMin);

        when(repository.findWithMaxLastPrice()).thenReturn(max);
        when(repository.findWithMinLastPrice()).thenReturn(min);
        when(mapper.mapToModel(any(CurrencyInfoAggregate.class))).thenCallRealMethod();

        List<CurrencyInfoResponseDto> report = service.getReport();
        Assertions.assertNotNull(report);
        Assertions.assertEquals(report.get(0).getCurrency(), "XRP");
        Assertions.assertEquals(report.get(0).getMaxPrice(), 0.39389);
        Assertions.assertEquals(report.get(0).getMinPrice(), 0.39008);

        verify(repository, times(1))
                .findWithMinLastPrice();
        verify(repository, times(1))
                .findWithMaxLastPrice();
        verify(mapper, times(6))
                .mapToModel(any(CurrencyInfoAggregate.class));
    }
}
