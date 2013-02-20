package com.github.hoverruan.libr.mobile.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;
import com.github.hoverruan.libr.mobile.R;
import com.github.hoverruan.libr.mobile.domain.Book;
import com.github.hoverruan.libr.mobile.domain.BookList;
import com.github.hoverruan.libr.mobile.domain.BookParser;
import com.github.hoverruan.libr.mobile.util.DownloadTask;

import java.util.List;

/**
 * @author Hover Ruan
 */
public class MainActivity extends SherlockActivity {

    private TextView responseContentView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.main);

        responseContentView = (TextView) findViewById(R.id.response_content);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setSupportProgressBarIndeterminateVisibility(true);
                new DownloadBooksInfo().execute("http://libr.herokuapp.com/api/books");
            }
        });
    }

    private class DownloadBooksInfo extends DownloadTask {
        private DownloadBooksInfo() {
            super(MainActivity.this);
        }

        @Override
        protected void onPostExecute(String booksInfoText) {
            if (booksInfoText != null) {
                BookList bookList = new BookParser().parseBookList(booksInfoText);
                responseContentView.setText(String.format("Got %d books:\n%s", bookList.getTotalCount(),
                        getAllBooksName(bookList.getBooks())));
            }
            setSupportProgressBarIndeterminateVisibility(false);
        }

        private String getAllBooksName(List<Book> books) {
            StringBuilder buf = new StringBuilder();
            for (Book book : books) {
                buf.append(book.getName()).append('\n');
            }
            buf.append("...\n");
            return buf.toString();
        }
    }
}
