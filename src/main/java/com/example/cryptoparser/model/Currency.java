package com.example.cryptoparser.model;

public enum Currency {
    BTC("BTC"),
    ETH("ETH"),
    XRP("XRP");
    private String value;

    Currency(String value) {
        this.value = value;
    }
}
