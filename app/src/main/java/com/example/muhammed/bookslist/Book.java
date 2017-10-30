package com.example.muhammed.bookslist;

import android.graphics.Bitmap;

/**
 * Created by Muhammed on 9/28/2017.
 * Book class to store Book's information to be shown
 * in the BooksListActivity
 */

public class Book {

    private String author, title;
    private Bitmap img;

    public Book(String title, String author, Bitmap img) {
        this.title = title;
        this.author = author;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Bitmap getImg()
    {
        return img;
    }
}
