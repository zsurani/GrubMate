package com.usc.zsurani.grubmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreatePostActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editDesc;
    private EditText editNumAvailable;
    private EditText editBeginTime;
    private EditText editEndTime;
    private EditText editLocation;
    private EditText editTags;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        editName = (EditText) findViewById(R.id.edit_post_name);
        editDesc = (EditText) findViewById(R.id.edit_post_desc);
        editNumAvailable = (EditText) findViewById(R.id.edit_post_max_requests);
        editBeginTime = (EditText) findViewById(R.id.edit_post_begin_time);
        editEndTime = (EditText) findViewById(R.id.edit_post_end_time);
        editLocation = (EditText) findViewById(R.id.edit_post_location);
        editTags = (EditText) findViewById(R.id.edit_post_tags);
        buttonSave = (Button) findViewById(R.id.button_save_new_post);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                //Have to go to select group page
                //Add this code to select group after passing post
                //Post(List<String> tags, Set<String> category, User provider,


                Post newPost = new Post(editTags,)
                //SQLiteDatabase db = dbHelper.getWritableDatabase();
                //ContentValues values = new ContentValues();

            }

        //values.put(Student.KEY_age, student.age);
        //values.put(Student.KEY_email,student.email);
        //values.put(Student.KEY_name, student.name);

            // Inserting Row
          //  long student_Id = db.insert(Student.TABLE, null, values);
        //db.close(); // Closing database connection
        //return (int) student_Id;
        });

    }
}
