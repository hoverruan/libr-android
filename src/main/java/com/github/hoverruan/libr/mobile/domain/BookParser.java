package com.github.hoverruan.libr.mobile.domain;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Hover Ruan
 */
public class BookParser {

    public BookList parseBookList(String json) {
        return createGson().fromJson(json, BookList.class);
    }

    public Book parseBook(String json) {
        return createGson().fromJson(json, Book.class);
    }

    private Gson createGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return builder.create();
    }
}
