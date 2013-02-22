package com.github.hoverruan.libr.mobile.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import static com.github.hoverruan.libr.mobile.LibrConstants.TAG;
import com.github.hoverruan.libr.mobile.R;
import com.github.hoverruan.libr.mobile.domain.Book;
import com.github.hoverruan.libr.mobile.domain.BookList;
import com.github.hoverruan.libr.mobile.domain.BookParser;
import com.github.hoverruan.libr.mobile.util.DownloadImageTask;
import com.github.hoverruan.libr.mobile.util.DownloadJsonTask;
import static com.github.hoverruan.libr.mobile.util.FileUtils.newFile;
import com.github.hoverruan.libr.mobile.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hover Ruan
 */
public class MainActivity extends SherlockListActivity {

    private List<Book> books = new ArrayList<Book>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (books == null || books.size() == 0) {
            if (isConnected()) {
                new DownloadBooksInfo().execute("http://libr.herokuapp.com/api/books");
            } else {
                ToastUtils.show(this, R.string.failed_not_connected);
            }
        } else {
            showBookList();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(getString(R.string.search))
                .setIcon(R.drawable.ic_search)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(getString(R.string.refresh))
                .setIcon(R.drawable.ic_refresh)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Book book = books.get(position);
        Log.i(TAG, book.getName() + " selected");
    }

    private class BookListAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        private BookListAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return books.size();
        }

        public Object getItem(int position) {
            return books.get(position);
        }

        public long getItemId(int position) {
            return books.get(position).getId();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.book_item, null);
            }

            Book book = (Book) getItem(position);

            ImageView bookImageView = (ImageView) convertView.findViewById(R.id.book_image);
            new BookCoverLoader(book, bookImageView).load();

            TextView bookNameTextView = (TextView) convertView.findViewById(R.id.book_name);
            bookNameTextView.setText(book.getName());

            TextView bookAuthorTextView = (TextView) convertView.findViewById(R.id.book_author);
            bookAuthorTextView.setText("作者: " + (book.getAuthor() == null ? "无" : book.getAuthor()));

            return convertView;
        }
    }

    private class DownloadBooksInfo extends DownloadJsonTask {

        @Override
        protected void onPostExecute(String booksInfoText) {
            if (booksInfoText != null) {
                BookList bookList = new BookParser().parseBookList(booksInfoText);
                if (bookList != null) {
                    books = bookList.getBooks();
                    showBookList();
                }
            }
        }
    }

    private class BookCoverLoader {
        private Book book;
        private ImageView bookImageView;

        public BookCoverLoader(Book book, ImageView bookImageView) {
            this.book = book;
            this.bookImageView = bookImageView;
        }

        public void load() {
            File coverFile = newFile(MainActivity.this.getCacheDir(), "cover", book.buildFilename());
            if (!coverFile.exists()) {
                new DownloadImageTask(coverFile) {
                    protected void onPostExecute(File downloadedImageFile) {
                        if (downloadedImageFile != null) {
                            loadImage(downloadedImageFile);
                        }
                    }
                }.execute(book.getImage());
            } else {
                loadImage(coverFile);
            }
        }

        private void loadImage(File imageFile) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());
            bookImageView.setImageBitmap(bitmap);
        }
    }

    private void showBookList() {
        setListAdapter(new BookListAdapter(this));
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
