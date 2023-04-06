package com.example.cryptoparser.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyInfoResponseDto {
    private String currency;
    private Double maxPrice;
    private Double minPrice;
}
