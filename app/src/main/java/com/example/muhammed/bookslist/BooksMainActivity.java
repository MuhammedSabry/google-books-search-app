package com.example.muhammed.bookslist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BooksMainActivity extends AppCompatActivity {
    //BroadcastReceiver broadcastReceiver;
    public static String BOOK_INTENT_EXTRA = "Book";
    TextView searchTextView;
    Button searchButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booksmain_activity);

//          broadcastReceiver = new BroadcastReceiver() {
//             @Override
//             public void onReceive(Context context, Intent intent) {

//                    NotificationCompat.Builder notibuilder = new NotificationCompat.Builder(BooksMainActivity.this)
//                            .setContentText("Hello there")
//                            .setContentTitle("Mine")
//                            .setSmallIcon(R.drawable.ic_stat_new_message);
//                    NotificationManager manager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                    manager.notify(0,notibuilder.build());

//                 Toast.makeText(context,"BooksApp Notified",Toast.LENGTH_SHORT).show();
//             }
//         };
        // IntentFilter filter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        // registerReceiver(broadcastReceiver, filter);

        searchButton =  findViewById(R.id.search);
        searchTextView = findViewById(R.id.booksearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = searchTextView.getText().toString();

                if(text.trim().length()>0 )
                {
                    Intent intent = new Intent(BooksMainActivity.this,BooksListActivity.class);
                    intent.putExtra(BOOK_INTENT_EXTRA,text);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(BooksMainActivity.this,"Invalid Book name",Toast.LENGTH_SHORT).show();
                    searchTextView.setText("");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregisterReceiver(broadcastReceiver);
    }
}
