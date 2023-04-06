package com.example.cryptoparser.repository;

import com.example.cryptoparser.model.CurrencyInfo;
import com.example.cryptoparser.model.dto.CurrencyInfoAggregate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyInfoRepository
        extends MongoRepository<CurrencyInfo, String> {
    CurrencyInfo findTopByCurrencyNameOrderByLastPrice(String currencyName);

    CurrencyInfo findTopByCurrencyNameOrderByLastPriceDesc(String currencyName);

    List<CurrencyInfo> findByCurrencyName(String currencyName, Pageable pageable);

    @Aggregation(pipeline = {"{$group: { _id:'$currencyName', price: { $min: '$lastPrice' }}})"})
    List<CurrencyInfoAggregate> findWithMinLastPrice();

    @Aggregation(pipeline = {"{$group: { _id:'$currencyName', price: { $max: '$lastPrice' }}})"})
    List<CurrencyInfoAggregate> findWithMaxLastPrice();
}

