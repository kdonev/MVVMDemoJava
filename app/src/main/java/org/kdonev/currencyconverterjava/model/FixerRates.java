package org.kdonev.currencyconverterjava.model;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.kdonev.currencyconverterjava.modelDB.Currency;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FixerRates implements IRatesLoader {

    private final String _apiUrl = "http://data.fixer.io/api/latest?access_key=5f21f2f65f0f9dd11d6c0b3ca9cb33e0";
    private final RequestQueue _requests;

    public FixerRates(Context context)
    {
        _requests = Volley.newRequestQueue(context);
    }

    @Override
    public void loadRates(final Consumer<List<Currency>> onSuccess, final Consumer<Exception> onError) {
        JsonObjectRequest req = new JsonObjectRequest(
                _apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                JSONObject rates = response.getJSONObject("rates");
                                List<Currency> currencies = new ArrayList<>();

                                Iterator<String> keys = rates.keys();
                                for (; keys.hasNext(); ) {
                                    String k = keys.next();
                                    currencies.add(new Currency(k, rates.getDouble(k)));
                                }

                                onSuccess.accept(currencies);
                            }
                        } catch (Exception e) {
                            onError.accept(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onError.accept(error);
                    }
                });

        _requests.add(req);
    }
}
