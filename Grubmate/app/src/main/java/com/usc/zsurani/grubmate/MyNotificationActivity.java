package com.usc.zsurani.grubmate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MyNotificationActivity extends AppCompatActivity {

    private ListView notificationList;
    private Button createNotification;

    public static final int RESULT_SAVE_NOTIF = 111;
    public static final int RESULT_CANCEL_NOTIF = -111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);

        // TODO change this to be list of notifications from DB
        ArrayList<Notifications> notifList = new ArrayList<>();

        notificationList = (ListView) findViewById(R.id.list_notifications);
        notificationList.setAdapter(new NotificationAdapter(getApplicationContext(), R.layout.layout_notification_row, notifList));

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
//                saveNewNotification(data);
                break;
            case RESULT_CANCEL_NOTIF:
                // Don't do anything
                break;
        }
    }

//    private void saveNewNotification(Intent data) {
//        // TODO get info and save it
//        String name = data.getStringExtra(CreateNotificationActivity.NOTIFICATION_NAME);
//        String start = data.getStringExtra(CreateNotificationActivity.NOTIFICATION_START);
//        String end = data.getStringExtra(CreateNotificationActivity.NOTIFICATION_END);
//        HashSet<String> tags = (HashSet<String>) data.getSerializableExtra(CreateNotificationActivity.NOTIFICATION_TAGS);
//        ArrayList<String> categories = (ArrayList<String>) data.getSerializableExtra(CreateNotificationActivity.NOTIFICATION_CATEGORY);
//
//        // SAVE IT
//    }

    /*
     * The custom adapter for the Notifications list view.
     */
    private class NotificationAdapter extends ArrayAdapter<Notifications> {
        public NotificationAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public NotificationAdapter(Context context, int textViewResourceId, List<Notifications> items) {
            super(context, textViewResourceId, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;

            // If the View to convert doesn't exist, inflate a new one with the correct layout
            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getApplicationContext());
                v = vi.inflate(R.layout.layout_notification_row, null);
            }

            // Get the Notifications object, and if it isn't null, populate the layout with its data
            Notifications t = getItem(position);

            if (t != null) {
                TextView textName = (TextView) v.findViewById(R.id.label_notification_name);
                TextView textInfo = (TextView) v.findViewById(R.id.label_notification_description);
                Button buttonEnd = (Button) v.findViewById(R.id.button_notification);

                String timeStart = t.getBeginTime();
                String timeEnd = t.getEndTime();

                textName.setText(t.getName());
                textInfo.setText(String.format(getResources().getString(R.string.text_notification_description), timeStart, timeEnd));

                //if time is passed, button is disabled; else it's enabled
                DateFormat df = new SimpleDateFormat("hh:mm");
                Date end;
                try {
                   end = df.parse(timeEnd);
                } catch (Exception e) {
                    end = Calendar.getInstance().getTime();
                }
                Date now = Calendar.getInstance().getTime();

                if (now.before(end)) {
                    buttonEnd.setEnabled(true);
                } else {
                    buttonEnd.setEnabled(false);
                }

                // on click listener for the "End Notification" button
                buttonEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO update notification / remove it from DB
                    }
                });
            }

            return v;
        }
    }
}

