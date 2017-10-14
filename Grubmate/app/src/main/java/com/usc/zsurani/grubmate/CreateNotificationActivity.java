package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notification);

        final TextView name = (TextView) findViewById(R.id.edit_notification_name);
        final TextView timeStart = (TextView) findViewById(R.id.edit_notification_begin_time);
        final TextView timeEnd = (TextView) findViewById(R.id.edit_notification_end_time);
        final TextView listCategories = (TextView) findViewById(R.id.edit_notification_tags);
        String[] t = listCategories.getText().toString().split(",");
        final Set<String> tags = new HashSet<String>(Arrays.asList(t));
        final List<String> cate = new ArrayList<String>();


        final Button button = (Button) findViewById(R.id.button_save_new_notification);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //getting what the category is
                CheckBox cb = (CheckBox) findViewById(R.id.american);
                if(cb.isChecked())
                {
                    cate.add("American");
                }
                cb = (CheckBox) findViewById(R.id.mexican);
                if(cb.isChecked()) {
                    cate.add("Mexican");
                }
                cb = (CheckBox) findViewById(R.id.fastFood);
                if(cb.isChecked()) {
                    cate.add("Fast Food");
                }
                cb = (CheckBox) findViewById(R.id.burger);
                if(cb.isChecked()) {
                    cate.add("Burger");
                }
                cb = (CheckBox) findViewById(R.id.pizza);
                if(cb.isChecked()) {
                    cate.add("Pizza");
                }
                cb = (CheckBox) findViewById(R.id.asian);
                if(cb.isChecked()) {
                    cate.add("Asian");
                }
                cb = (CheckBox) findViewById(R.id.coffee);
                if(cb.isChecked()) {
                    cate.add("Coffee");
                }
                cb = (CheckBox) findViewById(R.id.italian);
                if(cb.isChecked()) {
                    cate.add("Italian");
                }
                cb = (CheckBox) findViewById(R.id.sandwich);
                if(cb.isChecked()) {
                    cate.add("Sandwich");
                }
                cb = (CheckBox) findViewById(R.id.sushi);
                if(cb.isChecked()) {
                    cate.add("Sushi");
                }
                cb = (CheckBox) findViewById(R.id.breakfast);
                if(cb.isChecked()) {
                    cate.add("Breakfast");
                }
                cb = (CheckBox) findViewById(R.id.dessert);
                if(cb.isChecked()) {
                    cate.add("Dessert");
                }
                cb = (CheckBox) findViewById(R.id.bakery);
                if(cb.isChecked()) {
                    cate.add("Bakery");
                }
                cb = (CheckBox) findViewById(R.id.boba);
                if(cb.isChecked()) {
                    cate.add("Boba");
                }
                cb = (CheckBox) findViewById(R.id.thai);
                if(cb.isChecked()) {
                    cate.add("Thai");
                }
                cb = (CheckBox) findViewById(R.id.indian);
                if(cb.isChecked()) {
                    cate.add("Indian");
                }
                cb = (CheckBox) findViewById(R.id.homemade);
                if(cb.isChecked()) {
                    cate.add("Homemade");
                }
                cb = (CheckBox) findViewById(R.id.restaurant);
                if(cb.isChecked()) {
                    cate.add("Restaurant");
                }

                Notifications n = new Notifications(name.getText().toString(), tags, cate,
                        timeStart.getText().toString(), timeEnd.getText().toString(), "CREATE");
                //User.addNotification(n.getId());

                //need to add stuff to the database


            }
        });
    }



}
