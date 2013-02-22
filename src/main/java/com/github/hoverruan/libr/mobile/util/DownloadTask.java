package com.github.hoverruan.libr.mobile.util;

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

    public DownloadTask() {
    }

    protected Result doInBackground(String... urls) {
        try {
            return downloadFromUrl(urls[0]);
        } catch (IOException e) {
            Log.e(LibrConstants.TAG, "Failed to download content", e);
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
}
