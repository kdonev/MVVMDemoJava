package org.kdonev.currencyconverterjava.model;

import org.kdonev.currencyconverterjava.modelDB.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRates implements IRatesLoader {
    private final Random _random = new Random();

    private double nextDouble(double min, double max)
    {
        return min + (max - min) * _random.nextDouble();
    }

    @Override
    public void loadRates(Consumer<List<Currency>> onSuccess, Consumer<Exception> onError) {
        ArrayList<Currency> result = new ArrayList<>();
        result.add(new Currency("EUR", 1.0));
        result.add(new Currency("USD", nextDouble(1.1, 1.3)));
        result.add(new Currency("BGN", nextDouble(1.5, 2.5)));

        onSuccess.accept(result);
    }
}
