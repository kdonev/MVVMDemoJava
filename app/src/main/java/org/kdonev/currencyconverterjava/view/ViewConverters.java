package org.kdonev.currencyconverterjava.view;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.widget.EditText;

import org.kdonev.currencyconverterjava.viewModel.Converters;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;

public class ViewConverters {
    @BindingAdapter("android:text")
    public static void applyDoubleValue(EditText textView, double oldValue, double newValue)
    {
        double delta = newValue - oldValue;

        if (Math.abs(delta) > 0.01)
            textView.setText(Converters.convertDoubleToString(newValue));

        if (delta < -0.01)
            animateTextColor(textView, Color.RED);
        else if (delta > 0.01)
            animateTextColor(textView, Color.GREEN);
    }

    @InverseBindingAdapter( attribute="android:text")
    public static double getDoubleValue(@NonNull EditText textView)
    {
        return Converters.convertStringToDouble(textView.getText().toString());
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
