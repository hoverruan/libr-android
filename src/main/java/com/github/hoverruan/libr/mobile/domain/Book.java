package com.github.hoverruan.libr.mobile.domain;

import java.util.Date;

/**
 * @author Hover Ruan
 */
public class Book {
    private long id;
    private String author;
    private Date createdAt;
    private Date updatedAt;
    private String image;
    private String isbn;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String buildFilename() {
        StringBuilder builder = new StringBuilder(isbn);
        int pos = image.lastIndexOf('.');
        if (pos > 0) {
            String suffix = image.substring(pos);
            builder.append(suffix);
        }
        return builder.toString();
    }
}
