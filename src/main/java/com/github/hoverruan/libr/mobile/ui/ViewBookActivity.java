package com.github.hoverruan.libr.mobile.ui;

import android.app.Activity;
import android.os.Bundle;
import com.github.hoverruan.libr.mobile.R;

/**
 * @author Hover Ruan
 */
public class ViewBookActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_book);
    }
}
