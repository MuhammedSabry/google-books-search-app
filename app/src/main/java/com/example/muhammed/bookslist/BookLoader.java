package com.example.muhammed.bookslist;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Muhammed on 9/28/2017.
 * A loader to keep the network operations work in the background
 */

public class BookLoader extends AsyncTaskLoader {

    private String mUrl;
    public BookLoader(Context context, String url)
    {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }
    @Override
    public List<Book> loadInBackground()
    {
        return Query.getList(mUrl);
    }
}
