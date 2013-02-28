package com.github.hoverruan.libr.mobile.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.github.hoverruan.libr.mobile.LibrConstants;
import com.github.hoverruan.libr.mobile.R;
import com.github.hoverruan.libr.mobile.domain.Book;

/**
 * @author Hover Ruan
 */
public class ViewBookActivity extends Activity {
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
        Log.i(LibrConstants.TAG, "Got isbn: " + isbn);
    }
}
