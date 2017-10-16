package com.usc.zsurani.grubmate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

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
    private NotificationAdapter adapter;

    public static final int RESULT_SAVE_NOTIF = 111;
    public static final int RESULT_CANCEL_NOTIF = -111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notification);

        adapter = new NotificationAdapter(getApplicationContext(), R.layout.layout_notification_row, getNotificationList());
        notificationList = (ListView) findViewById(R.id.list_notifications);
        notificationList.setAdapter(adapter);

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
                adapter = new NotificationAdapter(getApplicationContext(), R.layout.layout_notification_row, getNotificationList());
                notificationList.setAdapter(adapter);
                break;
            case RESULT_CANCEL_NOTIF:
                // Don't do anything
                break;
        }
    }

    private List<Notifications> getNotificationList() {
        String fbId = Profile.getCurrentProfile().getId();
        UserRepo up = new UserRepo(getApplicationContext());
        final int userId = up.getId(fbId);

        List<Notifications> notifList = new ArrayList<>();

        NotificationsRepo repo = new NotificationsRepo(getApplicationContext());
        List<String> notifStrings = repo.getNotifications(userId);
        for (String id : notifStrings) {
            notifList.add(repo.getNotification(Integer.valueOf(id)));
        }

        return notifList;
    }

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
            final Notifications t = getItem(position);

            if (t != null) {
                TextView textName = (TextView) v.findViewById(R.id.label_notification_name);
                TextView textInfo = (TextView) v.findViewById(R.id.label_notification_description);
                Button buttonEnd = (Button) v.findViewById(R.id.button_notification);

                String timeStart = t.getBeginTime();
                String timeEnd = t.getEndTime();
                String tags = t.getTags().toString();
                if (tags.length() > 0) {
                    tags = tags.substring(1, tags.length() - 1);
                } else {
                    tags = "none";
                }
                String categories = t.getCategory().toString();
                if (categories.length() > 0) {
                    categories = categories.substring(1, categories.length()-1);
                } else {
                    categories = "none";
                }

                textName.setText(t.getName());
                textInfo.setText(String.format(getResources().getString(R.string.text_notification_description), timeStart, timeEnd, tags, categories));

                //if time is passed, button is disabled; else it's enabled
                DateFormat df = new SimpleDateFormat("hh:mm a");
                Date end;
                try {
                   end = df.parse(timeEnd);
                } catch (Exception e) {
                    end = Calendar.getInstance().getTime();
                }
                Date now = Calendar.getInstance().getTime();

                if (now.before(end)) {
                    buttonEnd.setEnabled(false);
                } else {
                    buttonEnd.setEnabled(true);
                }

                // on click listener for the "End Notification" button
                buttonEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NotificationsRepo repo = new NotificationsRepo(getApplicationContext());
                        repo.deleteNotification(String.valueOf(t.getId()));

                        // have to update adapter?

                    }
                });
            }

            return v;
        }
    }
}

