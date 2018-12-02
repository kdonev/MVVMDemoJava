package org.kdonev.currencyconverterjava;

import org.junit.Test;
import org.kdonev.currencyconverterjava.model.Consumer;
import org.kdonev.currencyconverterjava.model.ICurrencies;
import org.kdonev.currencyconverterjava.modelDB.Currency;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TestModel implements ICurrencies {
    private final MutableLiveData<List<Currency>> _currencies;

    public TestModel()
    {
        _currencies = new MutableLiveData<>();

        ArrayList<Currency> list = new ArrayList<>();
        list.add(new Currency("eur", 1.0));
        list.add(new Currency("usd", 1.5));
        list.add(new Currency("bgn", 1.95));

        _currencies.setValue(list);
    }

    @Override
    public LiveData<List<Currency>> all() {
        return _currencies;
    }

    @Override
    public void syncRates(Consumer<Boolean> onFinish) {
        ArrayList<Currency> list = new ArrayList<>();
        list.add(new Currency("eur", 1.0));
        list.add(new Currency("usd", 1.6));
        list.add(new Currency("bgn", 1.7));

        _currencies.setValue(list);

        onFinish.accept(true);
    }
}
