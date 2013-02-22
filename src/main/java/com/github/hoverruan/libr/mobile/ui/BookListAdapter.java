package com.github.hoverruan.libr.mobile.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.hoverruan.libr.mobile.R;
import com.github.hoverruan.libr.mobile.domain.Book;

import java.io.File;
import java.util.List;

/**
* @author Hover Ruan
*/
class BookListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Book> books;
    private File cacheDir;

    BookListAdapter(LayoutInflater layoutInflater, File cacheDir, List<Book> books) {
        this.cacheDir = cacheDir;
        this.books = books;
        this.inflater = layoutInflater;
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
        new BookCoverLoader(cacheDir, book, bookImageView).load();

        TextView bookNameTextView = (TextView) convertView.findViewById(R.id.book_name);
        bookNameTextView.setText(book.getName());

        TextView bookAuthorTextView = (TextView) convertView.findViewById(R.id.book_author);
        bookAuthorTextView.setText("作者: " + (book.getAuthor() == null ? "无" : book.getAuthor()));

        return convertView;
    }
}
