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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Hover Ruan
 */
public class DownloadTask extends AsyncTask<String, Void, String> {

    private Context context;

    public DownloadTask(Context context) {
        this.context = context;
    }

    protected String doInBackground(String... urls) {
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

    private String downloadFromUrl(String resourceUrl) throws IOException {
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

    private String readIt(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[1024];
        StringBuilder builder = new StringBuilder();
        int len;
        while ((len = reader.read(buffer)) > 0) {
            builder.append(buffer, 0, len);
        }
        return builder.toString();
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
