package org.kdonev.currencyconverterjava.model;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import android.content.Context;
import android.util.Log;

import org.kdonev.currencyconverterjava.modelDB.AppDatabase;
import org.kdonev.currencyconverterjava.modelDB.Currency;

import java.util.List;

public class Currencies implements ICurrencies{
    private final Context _context;
    private final IRatesLoader _ratesLoader;
    private final AppDatabase _database;

    public Currencies(Context context, IRatesLoader ratesLoader) {
        _context = context;
        _ratesLoader = ratesLoader;
        _database = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabase.class,
            "currenices_db"
        ).allowMainThreadQueries().build();
    }

    @Override
    public LiveData<List<Currency>> all() {
       return _database.currenciesDao().getAll();
    }

    @Override
    public void syncRates(final Consumer<Boolean> onFinish) {
        _ratesLoader.loadRates(
            new Consumer<List<Currency>>() {
                @Override
                public void accept(List<Currency> currencies) {
                    _database.currenciesDao().updateAll(currencies);
                    onFinish.accept(true);
                }
            },
            new Consumer<Exception>() {
                @Override
                public void accept(Exception e) {
                    Log.e("Currencies", e.toString());
                    onFinish.accept(false);
                }
            });
    }
}
