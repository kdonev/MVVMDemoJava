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
    public void loadRates(final Consumer<List<Currency>> onSuccess, Consumer<Exception> onError) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Currency> result = new ArrayList<>();
                result.add(new Currency("EUR", 1.0));
                result.add(new Currency("USD", nextDouble(1.1, 1.3)));
                result.add(new Currency("BGN", nextDouble(1.5, 2.5)));

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                onSuccess.accept(result);
            }
        }).start();
    }
}
