package org.kdonev.currencyconverterjava.modelDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity
public class Currency {
    @PrimaryKey @NonNull @ColumnInfo(name="name")
    public String name;

    @ColumnInfo(name="rate")
    public double rate;

    public Currency(String name, double rate)
    {
        this.name = name;
        this.rate = rate;
    }
}
