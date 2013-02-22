package com.github.hoverruan.libr.mobile.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

/**
 * @author Hover Ruan
 */
public class BookTest {
    @Test
    public void should_build_Filename_with_ISBN_and_image_url() {
        String suffix = ".jpg";
        String image = "http://img3.douban.com/mpic/s4483293" + suffix;
        String isbn = "9787111316657";

        Book book = new Book();
        book.setImage(image);
        book.setIsbn(isbn);

        String filename = book.buildFilename();
        assertThat(filename, is(isbn + suffix));
    }
}
