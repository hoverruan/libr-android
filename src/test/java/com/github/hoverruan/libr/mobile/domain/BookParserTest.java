package com.github.hoverruan.libr.mobile.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * @author Hover Ruan
 */
public class BookParserTest {

    private BookParser bookParser;

    @Before
    public void setUp() {
        bookParser = new BookParser();
    }

    @Test
    public void should_parse_BookList_from_JSON() throws Exception {
        String content = readResource("/com/github/hoverruan/libr/mobile/domain/books.json");
        BookList bookList = bookParser.parseBookList(content);

        assertThat(bookList, is(notNullValue()));
        assertThat(bookList.getCurrentPage(), is(1));
        assertThat(bookList.getTotalPage(), is(11));
        assertThat(bookList.getTotalCount(), is(106));

        List<Book> books = bookList.getBooks();
        assertThat(books.size(), is(10));

        Book firstBook = books.get(0);
        assertThat(firstBook.getAuthor(), is(nullValue()));
        assertThat(firstBook.getCreatedAt(), is(notNullValue()));
        assertThat(firstBook.getUpdatedAt(), is(notNullValue()));
        assertThat(firstBook.getName(), is("金矿"));
        assertThat(firstBook.getId(), is(26L));
        assertThat(firstBook.getIsbn(), is("9787111316657"));
        assertThat(firstBook.getImage(), is("http://img3.douban.com/mpic/s4483293.jpg"));
    }

    protected String readResource(String path) throws Exception {
        BufferedReader reader = null;
        StringWriter result;
        try {
            InputStream inputStream = getClass().getResourceAsStream(path);
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            result = new StringWriter();
            PrintWriter out = new PrintWriter(result);

            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return result.toString();
    }
}
