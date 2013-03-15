package com.github.hoverruan.libr.mobile.ui;

import com.github.hoverruan.libr.mobile.R;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Hover Ruan
 */
@RunWith(RobolectricTestRunner.class)
public class BookListActivityTest {
    @Test
    public void should_got_AppName() {
        String appName = new BookListActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("Libr"));
    }
}
