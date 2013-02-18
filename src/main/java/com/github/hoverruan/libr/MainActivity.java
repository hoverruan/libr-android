package com.github.hoverruan.libr;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Hover Ruan
 */
public class MainActivity extends Activity {

    public static final String LIBR = "LIBR";

    private TextView responseContentView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        responseContentView = (TextView) findViewById(R.id.responseContent);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!isConnected()) {
                    return;
                }

                new DownloadBooksInfo().execute("http://libr.herokuapp.com/api/books");
            }
        });
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    private class DownloadBooksInfo extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls) {
            try {
                return downloadFromUrl(urls[0]);
            } catch (IOException e) {
                Log.e(LIBR, "Unable to retrieve books info");
                return "";
            }
        }

        private String downloadFromUrl(String myurl) throws IOException {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.d(LIBR, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                return readIt(is, len);
            } finally {
                if (is != null) {
                    is.close();
                }
            }
        }

        private String readIt(InputStream stream, int len) throws IOException {
            Reader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }

        protected void onPostExecute(String booksInfoText) {
            responseContentView.setText(booksInfoText);
        }
    }
}
