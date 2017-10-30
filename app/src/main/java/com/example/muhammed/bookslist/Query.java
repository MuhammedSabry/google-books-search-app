package com.example.muhammed.bookslist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammed on 9/28/2017.
 * Query class to help doing the network operations
 * and getting back the required Books information
 */

public class Query {
    //the ArrayList that will contain all the BooksListActivity info
    // to be restored to the adapter
    private static ArrayList<Book> booklist;

    public static List<Book> getList(String url) {
        booklist = new ArrayList<>();

        //creating booksURL of the passed url
        URL booksURL = createURL(url);
        String array = connectTO(booksURL);
        booklist = requiredArray(array);

        return booklist;
    }

    private static URL createURL(String url) {
        URL createdURL = null;
        try {
            createdURL = new URL(url);
        } catch (MalformedURLException e) {
        }
        return createdURL;
    }


    private static String connectTO(URL url) {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsn = "";
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsn = readStream(inputStream);
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return jsn;
    }

    private static String readStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(reader);
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
        }
        return builder.toString();
    }

    private static ArrayList<Book> requiredArray(String s) {
        ArrayList<Book> book = new ArrayList<>();
        try {
            //holding the author and title of the book
            String title, author;

            if (s.equals("Not Found"))
                return null;
            //holding the main JSON OBJECT
            JSONObject MasterObject = new JSONObject(s);

            //holding the books details array
            JSONArray MasterArray = MasterObject.getJSONArray("items");

            for (int i = 0; i < MasterArray.length(); i++) {

                //holding each book's information
                JSONObject bookinfo = MasterArray.getJSONObject(i);
                JSONObject volinfo = bookinfo.getJSONObject("volumeInfo");

                //getting the title of the book from the voliume info
                title = volinfo.getString("title");

                //get authors array
                JSONArray authorsJSN = volinfo.getJSONArray("authors");

                //get first author
                author = authorsJSN.get(0).toString();

                //get image source object
                JSONObject images = volinfo.getJSONObject("imageLinks");
                Bitmap image = getBitmap(images.getString("thumbnail"));

                //instance of the book class holding book's information
                Book jsonbook = new Book(title, author, image);

                //adding the jsonbook to the ArrayList of books 
                book.add(jsonbook);
            }
        } catch (JSONException e) {
        }
        return book;
    }


    private static Bitmap getBitmap(String s) {
        Bitmap img = null;
        try {
            InputStream mapStream = new java.net.URL(s).openStream();
            img = BitmapFactory.decodeStream(mapStream);
        } catch (IOException e) {
        }
        return img;
    }


}
