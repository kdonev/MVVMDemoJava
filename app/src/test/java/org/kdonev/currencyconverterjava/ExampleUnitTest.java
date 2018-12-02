package org.kdonev.currencyconverterjava;

import org.junit.Rule;
import org.junit.Test;
import org.kdonev.currencyconverterjava.model.ICurrencies;
import org.kdonev.currencyconverterjava.viewModel.CurrenciesVM;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Test
    public void syncUpdateAmounts()
    {
        ICurrencies model = new TestModel();
        CurrenciesVM vm = new CurrenciesVM(model, null);

        vm.setEurAmount(10.0);

        assertEquals(vm.getUsdAmount(), 15.0, 0.01);
        assertEquals(vm.getBgnAmount(), 19.5, 0.01);

        vm.sync();

        assertEquals(vm.getEurAmount(), 10.0, 0.01);
        assertEquals(vm.getUsdAmount(), 16.0, 0.01);
        assertEquals(vm.getBgnAmount(), 17.0, 0.01);
    }
}