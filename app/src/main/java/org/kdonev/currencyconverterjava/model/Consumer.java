package org.kdonev.currencyconverterjava.model;

public interface Consumer<T> {
    void accept(T t);
}
