package org.kdonev.currencyconverterjava.viewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.annotation.Nullable;

import org.kdonev.currencyconverterjava.model.Consumer;
import org.kdonev.currencyconverterjava.model.ICurrencies;
import org.kdonev.currencyconverterjava.modelDB.Currency;
import org.kdonev.currencyconverterjava.BR;

import java.util.List;

public class CurrenciesVM extends BaseObservable {
    private final ICurrencies _model;

    private final LiveData<List<Currency>> _currencies;

    private double _eurAmount = 1.0;
    private boolean _syncInProgress = false;

    public CurrenciesVM(ICurrencies model, LifecycleOwner lifecycleOwner)
    {
        _model = model;

        _currencies = model.all();

        Observer<List<Currency>> observer = new Observer<List<Currency>>() {
            @Override
            public void onChanged(@Nullable List<Currency> currencies) {
                amountChanged(BR._all);
            }
        };

        if (lifecycleOwner != null)
            _currencies.observe(lifecycleOwner, observer);
        else
            _currencies.observeForever(observer);
    }

    private void amountChanged(int fromFieldId)
    {
        if (fromFieldId != BR.eurAmount)
            notifyPropertyChanged(BR.eurAmount);

        if (fromFieldId != BR.usdAmount)
            notifyPropertyChanged(BR.usdAmount);

        if (fromFieldId != BR.bgnAmount)
            notifyPropertyChanged(BR.bgnAmount);
    }

    private double rateFor(String name) {
        List<Currency> list = _currencies.getValue();
        if(list != null)
        {
            for (Currency val : list)
                if(val.name.equalsIgnoreCase(name))
                    return val.rate;
        }

        return 1.0;
    }

    private double amount(String name) {
        return getEurAmount() * rateFor(name);
    }

    private void updateAmount(double amount, String name, int fromFieldId)
    {
        updateEurAmount(amount / rateFor(name), fromFieldId);
    }

    private void updateEurAmount(double amount, int fromFieldId)
    {
        if (Math.abs(amount - _eurAmount) > 0.01)
        {
            _eurAmount = amount;
            amountChanged(fromFieldId);
        }
    }

    @Bindable
    public double getEurAmount()
    {
        return _eurAmount;
    }

    public void setEurAmount(double amount)
    {
        updateEurAmount(amount, BR.eurAmount);
    }

    @Bindable
    public double getUsdAmount()
    {
        return amount("usd");
    }

    public void setUsdAmount(double amount)
    {
        updateAmount(amount, "usd", BR.usdAmount);
    }

    @Bindable
    public double getBgnAmount()
    {
        return amount("bgn");
    }

    public void setBgnAmount(double amount)
    {
        updateAmount(amount, "bgn", BR.bgnAmount);
    }

    @Bindable
    public boolean getSyncInProgress()
    {
        return _syncInProgress;
    }

    private void setSyncInProgress(boolean sync)
    {
        if(_syncInProgress != sync)
        {
            _syncInProgress = sync;
            notifyPropertyChanged(BR.syncInProgress);
        }
    }

    public void sync()
    {
        setSyncInProgress(true);
        _model.syncRates(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean b) {
                setSyncInProgress(false);
            }
        });
    }
}
