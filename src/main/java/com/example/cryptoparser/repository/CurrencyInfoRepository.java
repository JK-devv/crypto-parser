package com.example.cryptoparser.repository;

import com.example.cryptoparser.model.CurrencyInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyInfoRepository
        extends MongoRepository<CurrencyInfo, Long> {
    CurrencyInfo findTopByCurrencyMainOrderByLastPrice(String currencyMain);

    CurrencyInfo findTopByCurrencyMainOrderByLastPriceDesc(String currencyMain);

    List<CurrencyInfo> findByCurrencyMain(String currencyMain, Pageable pageable);
}
