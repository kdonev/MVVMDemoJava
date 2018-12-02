package org.kdonev.currencyconverterjava.modelDB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Currency.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CurrenciesDAO currenciesDao();
}
