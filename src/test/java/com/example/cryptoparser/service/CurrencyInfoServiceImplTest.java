package com.example.cryptoparser.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.cryptoparser.model.Currency;
import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.ApiResponseCurrencyInfo;
import com.example.cryptoparser.model.dto.ResponseCurrencyInfoDto;
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
                .currencyMain(Currency.BTC)
                .lastPrice(23740.7)
                .build();
        CurrencyInfo expectedEth = CurrencyInfo.builder()
                .currencyMain(Currency.ETH)
                .lastPrice(1614.0)
                .build();
        CurrencyInfo expectedXrp = CurrencyInfo.builder()
                .currencyMain(Currency.XRP)
                .lastPrice(0.39008)
                .build();

        when(repository.findTopByCurrencyMainOrderByLastPrice("BTC"))
                .thenReturn(expectedBtc);
        when(repository.findTopByCurrencyMainOrderByLastPrice("ETH"))
                .thenReturn(expectedEth);
        when(repository.findTopByCurrencyMainOrderByLastPrice("XRP"))
                .thenReturn(expectedXrp);

        CurrencyInfo actualBtc =
                service.getCurrencyInfoWithMinLastPrice("BTC");
        CurrencyInfo actualEth =
                service.getCurrencyInfoWithMinLastPrice("ETH");
        CurrencyInfo actualXrp =
                service.getCurrencyInfoWithMinLastPrice("XRP");

        Assertions.assertEquals(expectedBtc.getLastPrice(), actualBtc.getLastPrice());
        Assertions.assertEquals(expectedEth.getLastPrice(), actualEth.getLastPrice());
        Assertions.assertEquals(expectedXrp.getLastPrice(), actualXrp.getLastPrice());

        verify(repository, times(3))
                .findTopByCurrencyMainOrderByLastPrice(any());
    }

    @Test
    void getCurrencyInfoWithMaxLastPrice() {
        CurrencyInfo expectedBtc = CurrencyInfo.builder()
                .currencyMain(Currency.BTC)
                .lastPrice(24738.6)
                .build();
        CurrencyInfo expectedEth = CurrencyInfo.builder()
                .currencyMain(Currency.ETH)
                .lastPrice(1684.28)
                .build();
        CurrencyInfo expectedXrp = CurrencyInfo.builder()
                .currencyMain(Currency.XRP)
                .lastPrice(0.39389)
                .build();

        when(repository.findTopByCurrencyMainOrderByLastPriceDesc("BTC"))
                .thenReturn(expectedBtc);
        when(repository.findTopByCurrencyMainOrderByLastPriceDesc("ETH"))
                .thenReturn(expectedEth);
        when(repository.findTopByCurrencyMainOrderByLastPriceDesc("XRP"))
                .thenReturn(expectedXrp);

        CurrencyInfo actualBtc =
                service.getCurrencyInfoWithMaxLastPrice("BTC");
        CurrencyInfo actualEth =
                service.getCurrencyInfoWithMaxLastPrice("ETH");
        CurrencyInfo actualXrp =
                service.getCurrencyInfoWithMaxLastPrice("XRP");

        Assertions.assertEquals(expectedBtc.getLastPrice(), actualBtc.getLastPrice());
        Assertions.assertEquals(expectedEth.getLastPrice(), actualEth.getLastPrice());
        Assertions.assertEquals(expectedXrp.getLastPrice(), actualXrp.getLastPrice());

        verify(repository, times(3))
                .findTopByCurrencyMainOrderByLastPriceDesc(any());
    }

    @Test
    void getByCurrencyMainName() {
        List<CurrencyInfo> expected = new ArrayList<>();
        CurrencyInfo currencyInfo1 = CurrencyInfo.builder()
                .currencyMain(Currency.BTC)
                .lastPrice(23740.7)
                .build();
        CurrencyInfo currencyInfo2 = CurrencyInfo.builder()
                .currencyMain(Currency.BTC)
                .lastPrice(24006.5)
                .build();
        CurrencyInfo currencyInfo3 = CurrencyInfo.builder()
                .currencyMain(Currency.BTC)
                .lastPrice(24573.7)
                .build();
        expected.add(currencyInfo1);
        expected.add(currencyInfo2);
        expected.add(currencyInfo3);

        when(repository.findByCurrencyMain("BTC",
                PageRequest.of(0, 3)))
                .thenReturn(expected);
        List<CurrencyInfo> actual = service
                .getByCurrencyMainName("BTC",
                        PageRequest.of(0, 3));

        Assertions.assertEquals(expected.size(), actual.size());
        Assertions.assertEquals(expected.get(0), actual.get(0));

        verify(repository, times(1))
                .findByCurrencyMain(any(), any());
    }

    @Test
    void save() {
        List<ApiResponseCurrencyInfo> responseCurrencyInfos =
                new ArrayList<>();
        ApiResponseCurrencyInfo apiResponseCurrencyInfo =
                ApiResponseCurrencyInfo.builder()
                .currencyMain("BTC")
                .price(24738.5)
                .build();
        ApiResponseCurrencyInfo apiResponseCurrencyInfo1 =
                ApiResponseCurrencyInfo.builder()
                .currencyMain("ETH")
                .price(1684.28)
                .build();
        ApiResponseCurrencyInfo apiResponseCurrencyInfo2 =
                ApiResponseCurrencyInfo.builder()
                .currencyMain("XRP")
                .price(0.39389)
                .build();
        responseCurrencyInfos.add(apiResponseCurrencyInfo2);
        responseCurrencyInfos.add(apiResponseCurrencyInfo1);
        responseCurrencyInfos.add(apiResponseCurrencyInfo);
        List<CurrencyInfo> saved = new ArrayList<>();
        CurrencyInfo btc = CurrencyInfo.builder()
                .currencyMain(Currency.BTC)
                .lastPrice(24738.5)
                .build();
        CurrencyInfo xrp = CurrencyInfo.builder()
                .currencyMain(Currency.XRP)
                .lastPrice(0.39389)
                .build();
        CurrencyInfo eth = CurrencyInfo.builder()
                .currencyMain(Currency.ETH)
                .lastPrice(1684.28)
                .build();
        saved.add(btc);
        saved.add(eth);
        saved.add(xrp);
        when(repository.saveAll(any())).thenReturn(saved);
        when(client.getInfoFromApi()).thenReturn(responseCurrencyInfos);
        when(mapper.mapToModel(any())).thenCallRealMethod();
        service.save();
        verify(repository, times(1)).saveAll(any());
        verify(client, times(1)).getInfoFromApi();
        verify(mapper, times(3)).mapToModel(any());
    }

    @Test
    void getReport() {
        CurrencyInfo btcMax = CurrencyInfo.builder()
                .currencyMain(Currency.BTC)
                .lastPrice(24738.6)
                .build();
        CurrencyInfo btcMin = CurrencyInfo.builder()
                .currencyMain(Currency.BTC)
                .lastPrice(23740.7)
                .build();

        CurrencyInfo ethMax = CurrencyInfo.builder()
                .currencyMain(Currency.ETH)
                .lastPrice(1684.28)
                .build();
        CurrencyInfo ethMin = CurrencyInfo.builder()
                .currencyMain(Currency.ETH)
                .lastPrice(1614.0)
                .build();
        CurrencyInfo xrpMax = CurrencyInfo.builder()
                .currencyMain(Currency.XRP)
                .lastPrice(0.39389)
                .build();
        CurrencyInfo xrpMin = CurrencyInfo.builder()
                .currencyMain(Currency.XRP)
                .lastPrice(0.39008)
                .build();

        when(repository.findTopByCurrencyMainOrderByLastPriceDesc("BTC"))
                .thenReturn(btcMax);
        when(repository.findTopByCurrencyMainOrderByLastPrice("BTC"))
                .thenReturn(btcMin);
        when(repository.findTopByCurrencyMainOrderByLastPriceDesc("ETH"))
                .thenReturn(ethMax);
        when(repository.findTopByCurrencyMainOrderByLastPrice("ETH"))
                .thenReturn(ethMin);
        when(repository.findTopByCurrencyMainOrderByLastPrice("XRP"))
                .thenReturn(xrpMin);
        when(repository.findTopByCurrencyMainOrderByLastPriceDesc("XRP"))
                .thenReturn(xrpMax);

        List<ResponseCurrencyInfoDto> report = service.getReport();
        Assertions.assertNotNull(report);
        Assertions.assertEquals(report.get(0).getCurrency(), "BTC");
        Assertions.assertEquals(report.get(0).getMaxPrice(), 24738.6);
        Assertions.assertEquals(report.get(0).getMinPrice(), 23740.7);

        verify(repository, times(3))
                .findTopByCurrencyMainOrderByLastPrice(any());
        verify(repository, times(3))
                .findTopByCurrencyMainOrderByLastPriceDesc(any());

    }
}
