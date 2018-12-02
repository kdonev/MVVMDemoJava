package org.kdonev.currencyconverterjava.model;

import androidx.lifecycle.LiveData;

import org.kdonev.currencyconverterjava.modelDB.Currency;

import java.util.List;

public interface ICurrencies {
    LiveData<List<Currency>> all();
    void syncRates(Consumer<Boolean> onFinish);
}
