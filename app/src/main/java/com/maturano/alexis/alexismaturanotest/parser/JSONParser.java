package com.maturano.alexis.alexismaturanotest.parser;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by alexismaturano on 5/26/17.
 */

public class JSONParser{

    private static final String MAIN_URL = "http://api.cinepolis.com.mx/Consumo.svc/json/ObtenerCiudades";
    public static final String TAG = "TAG";
    private static final String KEY_ID_STATE = "IdEstado";
    private static Response response;

    public static String getDataFromServer() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(MAIN_URL)
                    .build();
            response = client.newCall(request).execute();
            return  response.body().string();
        } catch (@NonNull IOException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
}

