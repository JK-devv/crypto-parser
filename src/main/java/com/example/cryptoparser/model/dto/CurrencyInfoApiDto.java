package com.example.cryptoparser.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyInfoApiDto {
    @JsonProperty(value = "curr1")
    private String currency;
    @JsonProperty(value = "lprice")
    private Double price;
}
