package org.kdonev.currencyconverterjava.viewModel;

import androidx.annotation.NonNull;
import androidx.databinding.InverseMethod;

public class Converters {
    @InverseMethod("convertStringToDouble")
    @NonNull public static String convertDoubleToString(double d){
        return String.format("%.2f", d);
    }

    public static double convertStringToDouble(String s){
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 1.0;
        }
    }
}
