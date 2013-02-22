package com.github.hoverruan.libr.mobile.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import com.github.hoverruan.libr.mobile.domain.Book;
import com.github.hoverruan.libr.mobile.util.DownloadImageTask;
import static com.github.hoverruan.libr.mobile.util.FileUtils.newFile;

import java.io.File;

/**
* @author Hover Ruan
*/
class BookCoverLoader {
    private Book book;
    private ImageView bookImageView;
    private File cacheDir;

    public BookCoverLoader(File cacheDir, Book book, ImageView bookImageView) {
        this.cacheDir = cacheDir;
        this.book = book;
        this.bookImageView = bookImageView;
    }

    public void load() {
        File coverFile = newFile(cacheDir, "cover", book.buildFilename());
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
