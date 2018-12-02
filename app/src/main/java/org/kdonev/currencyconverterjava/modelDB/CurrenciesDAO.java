package org.kdonev.currencyconverterjava.modelDB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CurrenciesDAO {
    @Query("SELECT * FROM Currency")
    LiveData<List<Currency>> getAll();

    @Query("SELECT * FROM Currency WHERE name LIKE :name LIMIT 1")
    Currency findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateAll(List<Currency> list);
}
