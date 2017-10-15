package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                String[] split = tags.split(", ");
                Set<String> t = new HashSet<String>(Arrays.asList(split));

                //checks to see what checkboxs are checked and then add the
                //name of the checkbox to an array
                List<String> cate = new ArrayList<String>();
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
                String fbId = Profile.getCurrentProfile().getId();
                UserRepo up = new UserRepo(getApplicationContext());
                int userId = up.getId(fbId); //need to add to use repo
                Log.d("USERID", Integer.toString(userId));

                Notifications n = new Notifications(name, t, cate, start, end, "POSTING", userId); //INPUTING A TEMP USER ID
                n.setActiveStatus(true);



                if (name.isEmpty() || start.isEmpty() || end.isEmpty() || tags.isEmpty()) { // add category
                    Toast.makeText(getApplicationContext(), "Please fill out all fields and try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                NotificationsRepo np = new NotificationsRepo(getApplicationContext());
                np.insert(n);

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
