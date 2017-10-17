package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zsurani on 10/16/17.
 */

public class EnterLocationActivity extends AppCompatActivity {

    EditText location;
    Button send_button;

    int postID = 0;
    int userID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_location);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
        } else {
            postID = extras.getInt("postID");
            userID = extras.getInt("userID");
        }

        Log.d("location", Integer.toString(postID));
        Log.d("location", Integer.toString(userID));

        send_button = (Button) findViewById(R.id.send_button);
        location = (EditText) findViewById(R.id.location_text);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // sends notification to provider about receiving a request
                PostRepo pr = new PostRepo(getApplicationContext());
                Notifications n = new Notifications(postID, userID, pr.getProviderId(postID),
                        location.getText().toString(), "REQUEST");
                NotificationsRepo nr = new NotificationsRepo(getApplicationContext());
                nr.insertRequest(n);

                //Transaction t = new Transaction(pr.getProviderId(postID), userID,
                //pr.getLocation(postID), postID);
                //t.setStatus("OPEN");
                //TransactionRepo tr = new TransactionRepo(getApplicationContext());
                //tr.insert(t);
            }
        });

    }
}
