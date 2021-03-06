package com.usc.zsurani.grubmate.activity_and_fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Profile;
import com.usc.zsurani.grubmate.base_classes.Notifications;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.NotificationsRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CreateNotificationActivity extends AppCompatActivity {

    private EditText editNotifName;
    private EditText editNotifTimeStart;
    private EditText editNotifTimeEnd;
    private EditText editNotifTags;
    private Button saveNotification;

    public static final String NOTIFICATION_NAME = "grubmate.notification.create.name";
    public static final String NOTIFICATION_START = "grubmate.notification.create.start_time";
    public static final String NOTIFICATION_END = "grubmate.notification.create.end_time";
    public static final String NOTIFICATION_TAGS = "grubmate.notification.create.tags";
    public static final String NOTIFICATION_CATEGORY = "grubmate.notification.create.category";

    int notifId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        editNotifName = (EditText) findViewById(R.id.edit_notification_name);
        editNotifTimeStart = (EditText) findViewById(R.id.edit_notification_begin_time);
        editNotifTimeEnd = (EditText) findViewById(R.id.edit_notification_end_time);
        editNotifTags = (EditText) findViewById(R.id.edit_notification_tags);

        Bundle extras = getIntent().getExtras();
        if(extras == null){
            notifId = 0;
        }
        else{
            notifId = extras.getInt("notifID");
            NotificationsRepo notifrepo = new NotificationsRepo(getApplicationContext());
            final Notifications notif = notifrepo.getNotification(notifId);
            editNotifName.setText(notif.getName());
            editNotifTimeStart.setText(notif.getBeginTime());
            editNotifTimeEnd.setText(notif.getEndTime());
            editNotifTags.setText(notif.getTag());
            // TODO
            //where do the checkboxes go?
            //update postid with new info
        }

        saveNotification = (Button) findViewById(R.id.button_save_new_notification);

        saveNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO TEST: if you don't put anything in, will this crash with null ptr??
                String name = editNotifName.getText().toString();
                String start = editNotifTimeStart.getText().toString();
                String end = editNotifTimeEnd.getText().toString();
                String tags = editNotifTags.getText().toString();
                String[] split = tags.split(", ");
                HashSet<String> t = new HashSet<String>(Arrays.asList(split));
                HashSet<String> cate = new HashSet<String>();
              
                //checks to see what checkboxs are checked and then add the
                //name of the checkbox to an array
                CheckBox c = (CheckBox) findViewById(R.id.checkBoxAmerican);
                if (c.isChecked()) {
                    cate.add("American");
                }
                c = (CheckBox) findViewById(R.id.checkBoxMexican);
                if (c.isChecked())
                {
                    cate.add("Mexican");
                }
                c = (CheckBox) findViewById(R.id.checkBoxFastFood);
                if (c.isChecked())
                {
                    cate.add("Fast Food");
                }
                c = (CheckBox) findViewById(R.id.checkBoxBurger);
                if (c.isChecked())
                {
                    cate.add("Burger");
                }
                c = (CheckBox) findViewById(R.id.checkBoxPizza);
                if (c.isChecked())
                {
                    cate.add("Pizza");
                }
                c = (CheckBox) findViewById(R.id.checkBoxAsian);
                if (c.isChecked())
                {
                    cate.add("Asian");
                }
                c = (CheckBox) findViewById(R.id.checkBoxCoffee);
                if (c.isChecked())
                {
                    cate.add("Coffee");
                }
                c = (CheckBox) findViewById(R.id.checkBoxItalian);
                if (c.isChecked())
                {
                    cate.add("Italian");
                }
                c = (CheckBox) findViewById(R.id.checkBoxSandwich);
                if (c.isChecked())
                {
                    cate.add("Sandwich");
                }
                c = (CheckBox) findViewById(R.id.checkBoxSushi);
                if (c.isChecked())
                {
                    cate.add("Sushi");
                }
                c = (CheckBox) findViewById(R.id.checkBoxBreakfast);
                if (c.isChecked())
                {
                    cate.add("Breakfast Food");
                }
                c = (CheckBox) findViewById(R.id.checkBoxDessert);
                if (c.isChecked())
                {
                    cate.add("Dessert");
                }
                c = (CheckBox) findViewById(R.id.checkBoxBakery);
                if (c.isChecked())
                {
                    cate.add("Bakery");
                }
                c = (CheckBox) findViewById(R.id.checkBoxBoba);
                if (c.isChecked())
                {
                    cate.add("Boba");
                }
                c = (CheckBox) findViewById(R.id.checkBoxThai);
                if (c.isChecked())
                {
                    t.add("Thai");
                }
                c = (CheckBox) findViewById(R.id.checkBoxIndian);
                if (c.isChecked())
                {
                    cate.add("Indian");
                }
                c = (CheckBox) findViewById(R.id.checkBoxHomemade);
                if (c.isChecked())
                {
                    cate.add("Homemade");
                }
                c = (CheckBox) findViewById(R.id.checkBoxRestaurant);
                if (c.isChecked())
                {
                    cate.add("Restaurant");
                }

                //This uses the current facebook profile to get the use ID to save in database
                String fbId;
                UserRepo up = new UserRepo(getApplicationContext());
                if (Profile.getCurrentProfile() == null) {
                    fbId = up.getProfile().getId();
                } else {
                    fbId = Profile.getCurrentProfile().getId();
                }
                int userId = up.getId(fbId); //need to add to use repo
                Notifications n = new Notifications(name, t, cate, start, end, Notifications.TYPE_SUBSCRIPTION, String.valueOf(userId)); //INPUTING A TEMP USER ID
                n.setActiveStatus(true);

                // if any field is not filled out, the user will not be able to save it
                if (name.isEmpty() || start.isEmpty() || end.isEmpty() || tags.isEmpty() || cate.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields and try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                NotificationsRepo np = new NotificationsRepo(getApplicationContext());

                if(notifId != 0){
                    n.setId(notifId);
                    np.update(n);
                }
                else np.insert(n);

                List<String> cList = new ArrayList<String>(cate);
                String cString = TextUtils.join(", ", cList);

                Intent dummy = new Intent();
                dummy.putExtra(NOTIFICATION_NAME, name);
                dummy.putExtra(NOTIFICATION_START, start);
                dummy.putExtra(NOTIFICATION_END, end);
                dummy.putExtra(NOTIFICATION_TAGS, t);
                dummy.putExtra(NOTIFICATION_CATEGORY, cString);
                setResult(0, dummy);
                finish();
            }
        });
    }
}
