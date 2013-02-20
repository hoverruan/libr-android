package com.github.hoverruan.libr.mobile.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import com.github.hoverruan.libr.mobile.LibrConstants;
import static com.github.hoverruan.libr.mobile.LibrConstants.TAG;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Hover Ruan
 */
public abstract class DownloadTask<Result> extends AsyncTask<String, Void, Result> {

    public static final String CACHE_DIR = "com.github.hoverruan/libr/";
    protected Context context;

    public DownloadTask(Context context) {
        this.context = context;
    }

    protected Result doInBackground(String... urls) {
        if (!isConnected()) {
            Log.w(LibrConstants.TAG, "No available connection");
            return null;
        }

        try {
            return downloadFromUrl(urls[0]);
        } catch (IOException e) {
            Log.e(LibrConstants.TAG, "Failed to download content");
            return null;
        }
    }

    protected abstract Result readIt(InputStream stream) throws IOException;

    private Result downloadFromUrl(String resourceUrl) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(resourceUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            return readIt(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
