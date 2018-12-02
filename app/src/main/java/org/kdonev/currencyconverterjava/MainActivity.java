package org.kdonev.currencyconverterjava;

import android.app.Activity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;

import org.kdonev.currencyconverterjava.databinding.ActivityMainBinding;
import org.kdonev.currencyconverterjava.model.Currencies;
import org.kdonev.currencyconverterjava.model.RandomRates;
import org.kdonev.currencyconverterjava.viewModel.CurrenciesVM;

public class MainActivity extends Activity implements LifecycleOwner {
    private LifecycleRegistry _lifecycleRegistry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _lifecycleRegistry = new LifecycleRegistry(this);
        _lifecycleRegistry.markState(Lifecycle.State.CREATED);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // FixerRates rates = new FixerRates(this);
        RandomRates rates = new RandomRates();
        Currencies model = new Currencies(this, rates);
        binding.setCurrencies(new CurrenciesVM(model, this));
    }

    @Override
    public void onStart() {
        super.onStart();
        _lifecycleRegistry.markState(Lifecycle.State.STARTED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return _lifecycleRegistry;
    }
}
