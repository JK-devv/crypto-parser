package com.example.cryptoparser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("currency_info")
public class CurrencyInfo {
    private String id;
    private Currency currencyName;
    @Builder.Default
    private String currency = "USD";
    private Double lastPrice;
}
