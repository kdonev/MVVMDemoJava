package org.kdonev.currencyconverterjava.viewModel;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseMethod;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.widget.EditText;

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

    @BindingAdapter("android:text")
    public static void applyDoubleValue(EditText textView, double oldValue, double newValue)
    {
        double delta = newValue - oldValue;

        if (Math.abs(delta) > 0.01)
            textView.setText(convertDoubleToString(newValue));

        if (delta < -0.01)
            animateTextColor(textView, Color.RED);
        else if (delta > 0.01)
            animateTextColor(textView, Color.GREEN);
    }

    @InverseBindingAdapter( attribute="android:text")
    public static double getDoubleValue(@NonNull EditText textView)
    {
        return convertStringToDouble(textView.getText().toString());
    }

    private static void animateTextColor(EditText textView, int fromColor)
    {
        int defaultTextColor = Color.BLACK;
        ObjectAnimator animator = ObjectAnimator.ofObject(textView,
                "textColor",
                new ArgbEvaluator(),
                fromColor,
                defaultTextColor);
        animator.setDuration(1000);
        animator.start();
    }
}
