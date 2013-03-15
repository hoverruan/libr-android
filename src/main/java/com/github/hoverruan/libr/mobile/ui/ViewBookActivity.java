package com.github.hoverruan.libr.mobile.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.github.hoverruan.libr.mobile.LibrConstants;
import static com.github.hoverruan.libr.mobile.LibrConstants.TAG;
import com.github.hoverruan.libr.mobile.R;
import com.github.hoverruan.libr.mobile.domain.Book;
import com.github.hoverruan.libr.mobile.domain.BookParser;
import com.github.hoverruan.libr.mobile.util.DownloadJsonTask;

/**
 * @author Hover Ruan
 */
public class ViewBookActivity extends Activity {
    private ProgressDialog progressDialog;
    private Book book;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_book);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        String isbn = intent.getStringExtra(Book.EXTRA_BOOK_ISBN);
        Log.i(TAG, "Got isbn: " + isbn);

        fetchBookDetails(isbn);
    }

    private void fetchBookDetails(String isbn) {
        new DownloadBookDetailsTask().execute(String.format(LibrConstants.API_BOOK_INFO, isbn));
    }

    private class DownloadBookDetailsTask extends DownloadJsonTask {
        @Override
        protected void onPostExecute(String bookInfo) {
            Book book = new BookParser().parseBook(bookInfo);
            if (book != null) {
                Log.i(TAG, "Fetched book info: " + bookInfo);
                ViewBookActivity.this.book = book;

                showBook();
            }
        }
    }

    private void showBook() {
        // TODO
    }
}
