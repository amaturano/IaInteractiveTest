package com.maturano.alexis.alexismaturanotest.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by alexismaturano on 5/26/17.
 */

public class InternetConnection {
    public static boolean checkInternetConnection(Context context){
        return ((ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
