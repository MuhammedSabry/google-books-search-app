package com.example.muhammed.bookslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Muhammed on 9/28/2017.
 * Books Array Adapter to provide the ListView with the
 * Books information from the Book class
 */

public class BooksListAdapter extends ArrayAdapter<Book> {

    public BooksListAdapter(Context context, ArrayList<Book> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.adapterlist, parent, false);
        }

        //getting the single book data
        Book book = getItem(position);

        TextView title = convertView.findViewById(R.id.booktitle);
        TextView author = convertView.findViewById(R.id.author);
        ImageView image = convertView.findViewById(R.id.image);

        //setting the views with the data
        title.setText(book.getTitle());
        author.setText("By " + book.getAuthor());
        image.setImageBitmap(book.getImg());

        return convertView;
    }
}
