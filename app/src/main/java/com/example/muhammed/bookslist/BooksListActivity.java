package com.example.muhammed.bookslist;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BooksListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private ListView bookslist;
    private BooksListAdapter adapter;
    private String url;
    private TextView bartext;
    private SwipeRefreshLayout swipe;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookslist_activity);
        //getting the views
        bar = findViewById(R.id.bar);
        bartext = findViewById(R.id.bartext);
        swipe =  findViewById(R.id.swap);

        Intent bookintent = getIntent();
        String bookSearch = bookintent.getStringExtra(BooksMainActivity.BOOK_INTENT_EXTRA);

        url = "https://www.googleapis.com/books/v1/volumes?q=" + bookSearch;

        //initializing the list view with empty list
        bookslist = findViewById(R.id.bookslist);
        adapter = new BooksListAdapter(BooksListActivity.this, new ArrayList<Book>());
        //new loader manager to manage the loaders
        getLoaderManager().initLoader(0, null, this);

        //refreshing the books list
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLoaderManager().getLoader(0).forceLoad();
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {

        //returning a new Loader containing the BooksList
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> list) {
        //clear the adapter so we will refresh the list
        swipe.setRefreshing(false);

        //informing the user that the books are handled properly
        Toast toast = Toast.makeText(BooksListActivity.this, "Books List Fetched", Toast.LENGTH_SHORT);
        toast.show();

        //removing the loading progress bar
        bar.setVisibility(View.GONE);
        bartext.setVisibility(View.GONE);

        //clearing the adapter
        adapter.clear();
        adapter.addAll(list);
        //setting the adapter to the listView to restart the ui
        bookslist.setAdapter(adapter);
        bookslist.setEmptyView(findViewById(R.id.empty_text_view));
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        //clearing the adapter on the loader reset
        adapter.clear();
    }
}
