package com.github.hoverruan.libr.mobile.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.actionbarsherlock.view.Window;
import static com.github.hoverruan.libr.mobile.LibrConstants.TAG;
import com.github.hoverruan.libr.mobile.R;
import com.github.hoverruan.libr.mobile.domain.Book;
import com.github.hoverruan.libr.mobile.domain.BookList;
import com.github.hoverruan.libr.mobile.domain.BookParser;
import com.github.hoverruan.libr.mobile.util.DownloadImageTask;
import com.github.hoverruan.libr.mobile.util.DownloadJsonTask;

import java.io.File;
import java.util.List;

/**
 * @author Hover Ruan
 */
public class MainActivity extends SherlockListActivity {

    private List<Book> books;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setSupportProgressBarIndeterminateVisibility(true);
        new DownloadBooksInfo().execute("http://libr.herokuapp.com/api/books");
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
        private DownloadBooksInfo() {
            super(MainActivity.this);
        }

        @Override
        protected void onPostExecute(String booksInfoText) {
            if (booksInfoText != null) {
                BookList bookList = new BookParser().parseBookList(booksInfoText);
                if (bookList != null) {
                    books = bookList.getBooks();
                    setListAdapter(new BookListAdapter(MainActivity.this));
                }
            }
            setSupportProgressBarIndeterminateVisibility(false);
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
            File imageFile = DownloadImageTask.calculateImageFile(MainActivity.this, "cover", book.getIsbn());
            if (!imageFile.exists()) {
                new DownloadImageTask(MainActivity.this, "cover", book.getIsbn()) {
                    protected void onPostExecute(File downloadedImageFile) {
                        loadImage(downloadedImageFile);
                    }
                }.execute(book.getImage());
            } else {
                loadImage(imageFile);
            }
        }

        private void loadImage(File imageFile) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getPath());
            bookImageView.setImageBitmap(bitmap);
        }
    }
}
