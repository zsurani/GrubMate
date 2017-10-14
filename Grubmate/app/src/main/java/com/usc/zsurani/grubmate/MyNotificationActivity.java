package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MyNotificationActivity extends AppCompatActivity {

    private ListView notificationList;
    private Button createNotification;

    public static final int RESULT_SAVE_NOTIF = 111;
    public static final int RESULT_CANCEL_NOTIF = -111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);

        notificationList = (ListView) findViewById(R.id.list_notifications);

        createNotification = (Button) findViewById(R.id.button_add_notification);

        createNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start new page for creating a notification
                Intent i = new Intent(MyNotificationActivity.this, CreateNotificationActivity.class);
                startActivityForResult(i, 0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_SAVE_NOTIF:
                saveNewNotification(data);
                break;
            case RESULT_CANCEL_NOTIF:
                // Don't do anything
                break;
        }
    }

    private void saveNewNotification(Intent data) {
        // TODO get info and save it

        String name = data.getStringExtra(CreateNotificationActivity.NOTIFICATION_NAME);
        String start = data.getStringExtra(CreateNotificationActivity.NOTIFICATION_START);
        String end = data.getStringExtra(CreateNotificationActivity.NOTIFICATION_END);
        String tags = data.getStringExtra(CreateNotificationActivity.NOTIFICATION_TAGS);
        // category too

        // SAVE IT
    }
}
