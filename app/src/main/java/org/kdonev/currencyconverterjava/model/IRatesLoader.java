package org.kdonev.currencyconverterjava.model;

import org.kdonev.currencyconverterjava.modelDB.Currency;

import java.util.List;

public interface IRatesLoader {
    void loadRates(Consumer<List<Currency>> onSuccess, Consumer<Exception> onError);
}
