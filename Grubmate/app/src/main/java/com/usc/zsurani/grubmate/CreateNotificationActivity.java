package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNotificationActivity extends AppCompatActivity {

    private EditText editNotifName;
    private EditText editNotifTimeStart;
    private EditText editNotifTimeEnd;
    private EditText editNotifTags;
    //private EditText editNotifCategory;
    private Button saveNotification;

    public static final String NOTIFICATION_NAME = "grubmate.notification.create.name";
    public static final String NOTIFICATION_START = "grubmate.notification.create.start_time";
    public static final String NOTIFICATION_END = "grubmate.notification.create.end_time";
    public static final String NOTIFICATION_TAGS = "grubmate.notification.create.tags";
//    public static final String NOTIFICATION_CATEGORY = "grubmate.notification.create.category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        editNotifName = (EditText) findViewById(R.id.edit_notification_name);
        editNotifTimeStart = (EditText) findViewById(R.id.edit_notification_begin_time);
        editNotifTimeEnd = (EditText) findViewById(R.id.edit_notification_end_time);
        editNotifTags = (EditText) findViewById(R.id.edit_notification_tags);

        saveNotification = (Button) findViewById(R.id.button_save_new_notification);

        saveNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO TEST: if you don't put anything in, will this crash with null ptr??
                String name = editNotifName.getText().toString();
                String start = editNotifTimeStart.getText().toString();
                String end = editNotifTimeEnd.getText().toString();
                String tags = editNotifTags.getText().toString();
                // category

                if (name.isEmpty() || start.isEmpty() || end.isEmpty() || tags.isEmpty()) { // add category
                    Toast.makeText(getApplicationContext(), "Please fill out all fields and try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent dummy = new Intent();
                dummy.putExtra(NOTIFICATION_NAME, name);
                dummy.putExtra(NOTIFICATION_START, start);
                dummy.putExtra(NOTIFICATION_END, end);
                dummy.putExtra(NOTIFICATION_TAGS, tags);
                setResult(MyNotificationActivity.RESULT_SAVE_NOTIF, dummy);
                finish();
            }
        });
    }
}
