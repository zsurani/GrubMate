package com.usc.zsurani.grubmate.activity_and_fragment;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.Profile;
import com.usc.zsurani.grubmate.R;
import com.usc.zsurani.grubmate.base_classes.Post;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.GroupRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.PostRepo;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatePostActivity extends AppCompatActivity {

    public static final int RESULT_POST_CREATED = 222;

    private EditText editName;
    private EditText editDesc;
    private EditText editNumAvailable;
    private EditText editBeginTime;
    private EditText editEndTime;
    private EditText editLocation;
    private EditText editTags;
    private Button buttonSave;
    private Button selectGroup;
    private Button buttonDelete;
    private CheckBox checkbox1;
    private CheckBox checkbox2;
    private CheckBox checkbox3;
    private CheckBox checkbox4;
    private CheckBox checkbox5;
    private CheckBox checkbox6;
    private CheckBox checkbox7;
    private CheckBox checkbox8;
    private CheckBox checkbox9;
    private CheckBox checkbox10;
    private CheckBox checkbox11;
    private CheckBox checkbox12;
    private CheckBox checkbox13;
    private CheckBox checkbox14;
    private CheckBox checkbox15;
    private CheckBox checkbox16;
    private RadioButton homemade;
    private RadioButton restaurant;
    private String num_requests;
    private String groupname = "";

    int postId;

    private ImageView viewImage;
    private Bitmap yourbitmap;
    int postID;


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
        selectGroup = (Button) findViewById(R.id.button_select_group);
        buttonSave = (Button) findViewById(R.id.button_save_new_post);
        buttonDelete = (Button) findViewById(R.id.button_delete_post);
        viewImage = (ImageView) findViewById(R.id.viewImage);

        checkbox1 = (CheckBox) findViewById(R.id.american);
        checkbox2 = (CheckBox) findViewById(R.id.mexican);
        checkbox3 = (CheckBox) findViewById(R.id.fastFood);
        checkbox4 = (CheckBox) findViewById(R.id.burger);
        checkbox5 = (CheckBox) findViewById(R.id.pizza);
        checkbox6 = (CheckBox) findViewById(R.id.asian);
        checkbox7 = (CheckBox) findViewById(R.id.coffee);
        checkbox8 = (CheckBox) findViewById(R.id.italian);
        checkbox9 = (CheckBox) findViewById(R.id.sandwich);
        checkbox10 = (CheckBox) findViewById(R.id.sushi);
        checkbox11 = (CheckBox) findViewById(R.id.breakfast);
        checkbox12 = (CheckBox) findViewById(R.id.dessert);
        checkbox13 = (CheckBox) findViewById(R.id.bakery);
        checkbox14 = (CheckBox) findViewById(R.id.boba);
        checkbox15 = (CheckBox) findViewById(R.id.thai);
        checkbox16 = (CheckBox) findViewById(R.id.indian);

        homemade = (RadioButton) findViewById(R.id.radio_homemade);
        restaurant = (RadioButton) findViewById(R.id.radio_restaurant);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            postID = -1;
        } else {
            postID = extras.getInt("postID");
            PostRepo postRepo = new PostRepo(getApplicationContext());
            final Post post = postRepo.getPost(postID);
            editName.setText(post.getFood());
            editDesc.setText(post.getDescription());
            editNumAvailable.setText(post.getNum_requests());
            editBeginTime.setText(post.getBeginTime());
            editEndTime.setText(post.getEndTime());
            editLocation.setText(post.getLocation());
            if (post.getHomemade().equals("homemade")) homemade.setChecked(true);
            else restaurant.setChecked(true);
            editTags.setText(post.getTag());
            byte[] images = post.getPhoto_image();
            Bitmap images2 = BitmapFactory.decodeByteArray(images, 0, images.length);
            viewImage.setImageBitmap(images2);
            String groups = post.getGroupString();
            if (!groups.equals("")) {
                GroupRepo groupRepo = new GroupRepo(getApplicationContext());
                List<String> groupList = Arrays.asList(groups.split(","));
                List<String> groupNames = new ArrayList<>();
                for (int i=0; i<groupList.size(); i++) {
                    groupNames.add(groupRepo.getName(groupList.get(i)));
                }
                String separator = ", ";
                int total = groupNames.size() * separator.length();
                for (String s : groupNames) {
                    total += s.length();
                }

                StringBuilder sb = new StringBuilder(total);
                for (String s : groupNames) {
                    sb.append(separator).append(s);
                }

                groupname = sb.substring(separator.length());
            }
        }

        viewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        selectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int post = createPost();
                Intent i = new Intent(CreatePostActivity.this, AddGroupToPostActivity.class);
                i.putExtra("groupname", groupname);
                startActivityForResult(i, 0);


            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postID != -1) {
                    PostRepo postRepo = new PostRepo(getApplicationContext());
                    postRepo.deletePost(postID);
                }

                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String description = editDesc.getText().toString();
                String owner;
                UserRepo userRepo = new UserRepo(getApplicationContext());
                if (Profile.getCurrentProfile() == null) {
                    owner = userRepo.getProfile().getId();
                } else {
                    owner = Profile.getCurrentProfile().getId();
                }
                final String food = editName.getText().toString();
                // images
                num_requests = editNumAvailable.getText().toString(); // error check for words? TODO
                final String tags = editTags.getText().toString();
                final String beginTime = editBeginTime.getText().toString();
                final String endTime = editEndTime.getText().toString();
                final String location = editLocation.getText().toString();
                final String active = "true";
                // groups
                final String homemade_tag;
                if (homemade.isChecked()) homemade_tag = "homemade";
                else homemade_tag = "restaurant";
                // all friends can view

                String category = ""; // change to dynamic if time TODO

                if (checkbox1.isChecked()) {
                    category += checkbox1.getText();
                    category += ", ";
                }
                if (checkbox2.isChecked()) {
                    category += checkbox2.getText();
                    category += ", ";
                }
                if (checkbox3.isChecked()) {
                    category += checkbox3.getText();
                    category += ", ";
                }
                if (checkbox4.isChecked()) {
                    category += checkbox4.getText();
                    category += ", ";
                }
                if (checkbox5.isChecked()) {
                    category += checkbox5.getText();
                    category += ", ";
                }
                if (checkbox6.isChecked()) {
                    category += checkbox6.getText();
                    category += ", ";
                }
                if (checkbox7.isChecked()) {
                    category += checkbox7.getText();
                    category += ", ";
                }
                if (checkbox8.isChecked()) {
                    category += checkbox8.getText();
                    category += ", ";
                }
                if (checkbox9.isChecked()) {
                    category += checkbox9.getText();
                    category += ", ";
                }
                if (checkbox10.isChecked()) {
                    category += checkbox10.getText();
                    category += ", ";
                }
                if (checkbox11.isChecked()) {
                    category += checkbox11.getText();
                    category += ", ";
                }
                if (checkbox12.isChecked()) {
                    category += checkbox12.getText();
                    category += ", ";
                }
                if (checkbox13.isChecked()) {
                    category += checkbox13.getText();
                    category += ", ";
                }
                if (checkbox14.isChecked()) {
                    category += checkbox14.getText();
                    category += ", ";
                }
                if (checkbox15.isChecked()) {
                    category += checkbox15.getText();
                    category += ", ";
                }
                if (checkbox16.isChecked()) {
                    category += checkbox16.getText();
                }

                final String categories = category;
                final String users = "";

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //GETTING A NULL POINTER BELOW THIS

                // if any field is not filled out, the user will not be able to save it
                if (description.isEmpty() || owner.isEmpty() || food.isEmpty() ||
                        food.isEmpty() || beginTime.isEmpty() ||
                        endTime.isEmpty() || location.isEmpty() || homemade_tag.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields and try again", Toast.LENGTH_SHORT).show();
                    return;
                }

                //TODO: this doesn't work unless you input a picture (because it says that postID is null)

                byte[] image;
                if (yourbitmap == null) {
                    Bundle extras = getIntent().getExtras();
                    PostRepo postRepo = new PostRepo(getApplicationContext());
                    postID = extras.getInt("postID");
                    final Post post = postRepo.getPost(postID);
                    image = post.getPhoto_image();
                } else {
                    yourbitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
                    image = stream.toByteArray();
                }

                Log.d("debug", Integer.toString(image.length));


                // images in between food and num_requests
                // groups in between active and usersRequested
                // allFriendsCanView at end
                Post post = new Post(description, owner, food, image, num_requests, categories, tags,
                        beginTime, endTime, location, active, users, users, homemade_tag);

                PostRepo postRepo = new PostRepo(getApplicationContext());

                GroupRepo gr = new GroupRepo(getApplicationContext());
                if (!groupname.equals("")) {
                    String groupID = gr.getGroupID(groupname);
                    post.setGroupString(groupID);
                } else {
                    post.setGroupString("");
                }

                if (postID != -1) {
                    post.setId(postID);
                    postRepo.update(post);
                    postId = postID;
                } else postId = postRepo.insert(post);

                Intent dummy = new Intent();
                dummy.putExtra("PostID", postId);
                setResult(CreatePostActivity.RESULT_POST_CREATED, dummy);
                finish();

            }
        });

    }


    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePostActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    // TODO probably need to change this if we're making it a fragment
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == 0){
                groupname = data.getStringExtra("groupname");
            }
            if (requestCode == 1) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                byte[] BYTE;
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                viewImage.setImageBitmap(photo);
                yourbitmap = photo;

            } else if (requestCode == 2) {

                Uri selectedImageUri = data.getData();
                if (Build.VERSION.SDK_INT < 19) {
                    String selectedImagePath = getPath(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath);
                    yourbitmap = bitmap;
                    viewImage.setImageBitmap(bitmap);

                } else {
                    ParcelFileDescriptor parcelFileDescriptor;
                    try {
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(selectedImageUri, "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                        parcelFileDescriptor.close();
                        viewImage.setImageBitmap(image);
                        yourbitmap = image;

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
//                viewImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));


            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getPath(Uri selectedImageUri) {
        // Will return "image:x*"
        String wholeID = DocumentsContract.getDocumentId(selectedImageUri);

// Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

// where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        column, sel, new String[]{ id }, null);

        String filePath = "";

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }

        cursor.close();

        return filePath;
    }
}