//package com.usc.zsurani.grubmate;
//
//
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.AlertDialog;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.TextView;
//
//import com.facebook.Profile;
//
//import java.io.ByteArrayOutputStream;
//
//public class CreatePostFragment extends Fragment {
//
//    private static final String KEY_POST_ID = "createpost.postid";
//
//    private EditText editName;
//    private EditText editDesc;
//    private EditText editNumAvailable;
//    private EditText editBeginTime;
//    private EditText editEndTime;
//    private EditText editLocation;
//    private EditText editTags;
//    private Button buttonSave;
//    private Button selectGroup;
//    private Button buttonDelete;
//    private CheckBox checkbox1;
//    private CheckBox checkbox2;
//    private CheckBox checkbox3;
//    private CheckBox checkbox4;
//    private CheckBox checkbox5;
//    private CheckBox checkbox6;
//    private CheckBox checkbox7;
//    private CheckBox checkbox8;
//    private CheckBox checkbox9;
//    private CheckBox checkbox10;
//    private CheckBox checkbox11;
//    private CheckBox checkbox12;
//    private CheckBox checkbox13;
//    private CheckBox checkbox14;
//    private CheckBox checkbox15;
//    private CheckBox checkbox16;
//    private RadioButton homemade;
//    private RadioButton restaurant;
//    private String num_requests;
//    private String groupname;
//
//    int postId;
//
//    ImageView viewImage;
//    private Bitmap yourbitmap;
//    int postID;
//
//
//    public CreatePostFragment() {
//        // Required empty public constructor
//    }
//
//    public static CreatePostFragment newInstance(int arg1) {
//        CreatePostFragment fragment = new CreatePostFragment();
//        Bundle args = new Bundle();
//        args.putInt(KEY_POST_ID, arg1);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.activity_create_post, null);
//
//        editName = (EditText) v.findViewById(R.id.edit_post_name);
//        editDesc = (EditText) v.findViewById(R.id.edit_post_desc);
//        editNumAvailable = (EditText) v.findViewById(R.id.edit_post_max_requests);
//        editBeginTime = (EditText) v.findViewById(R.id.edit_post_begin_time);
//        editEndTime = (EditText) v.findViewById(R.id.edit_post_end_time);
//        editLocation = (EditText) v.findViewById(R.id.edit_post_location);
//        editTags = (EditText) v.findViewById(R.id.edit_post_tags);
//        selectGroup = (Button) v.findViewById(R.id.button_select_group);
//        buttonSave = (Button) v.findViewById(R.id.button_save_new_post);
//        buttonDelete = (Button) v.findViewById(R.id.button_delete_post);
//        viewImage = (ImageView) v.findViewById(R.id.viewImage);
//
//        checkbox1 = (CheckBox) v.findViewById(R.id.american);
//        checkbox2 = (CheckBox) v.findViewById(R.id.mexican);
//        checkbox3 = (CheckBox) v.findViewById(R.id.fastFood);
//        checkbox4 = (CheckBox) v.findViewById(R.id.burger);
//        checkbox5 = (CheckBox) v.findViewById(R.id.pizza);
//        checkbox6 = (CheckBox) v.findViewById(R.id.asian);
//        checkbox7 = (CheckBox) v.findViewById(R.id.coffee);
//        checkbox8 = (CheckBox) v.findViewById(R.id.italian);
//        checkbox9 = (CheckBox) v.findViewById(R.id.sandwich);
//        checkbox10 = (CheckBox) v.findViewById(R.id.sushi);
//        checkbox11 = (CheckBox) v.findViewById(R.id.breakfast);
//        checkbox12 = (CheckBox) v.findViewById(R.id.dessert);
//        checkbox13 = (CheckBox) v.findViewById(R.id.bakery);
//        checkbox14 = (CheckBox) v.findViewById(R.id.boba);
//        checkbox15 = (CheckBox) v.findViewById(R.id.thai);
//        checkbox16 = (CheckBox) v.findViewById(R.id.indian);
//
//        homemade = (RadioButton) v.findViewById(R.id.radio_homemade);
//        restaurant = (RadioButton) v.findViewById(R.id.radio_restaurant);
//
//        if (getArguments().getInt(KEY_POST_ID) == -1) {
//            postID = 0;
//        } else {
//            postID = getArguments().getInt(KEY_POST_ID);
//            PostRepo postRepo = new PostRepo(getActivity().getApplicationContext());
//            final Post post = postRepo.getPost(postID);
//            editName.setText(post.getFood());
//            editDesc.setText(post.getDescription());
//            editNumAvailable.setText(post.getNum_requests());
//            editBeginTime.setText(post.getBeginTime());
//            editEndTime.setText(post.getEndTime());
//            editLocation.setText(post.getLocation());
//            if (post.getHomemade().equals("homemade")) homemade.setChecked(true);
//            else restaurant.setChecked(true);
//            editTags.setText(post.getTag());
//            byte[] images = post.getPhoto_image();
//            Bitmap images2 = BitmapFactory.decodeByteArray(images, 0, images.length);
//            viewImage.setImageBitmap(images2);
//        }
//
//
//
//        viewImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectImage();
//            }
//        });
//
//        selectGroup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                int post = createPost();
//                Intent i = new Intent(CreatePostActivity.this, AddGroupToPostActivity.class);
////                i.putExtra("postID", post);
//                startActivityForResult(i, 0);
//            }
//        });
//
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (postID != 0) {
//                    PostRepo postRepo = new PostRepo(getApplicationContext());
//                    postRepo.deletePost(postID);
//                }
//
//                finish();
//            }
//        });
//
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                final String description = editDesc.getText().toString();
//                String owner;
//                UserRepo userRepo = new UserRepo(getApplicationContext());
//                if (Profile.getCurrentProfile() == null) {
//                    owner = userRepo.getProfile().getId();
//                } else {
//                    owner = Profile.getCurrentProfile().getId();
//                }
//                final String food = editName.getText().toString();
//                // images
//                num_requests = editNumAvailable.getText().toString(); // error check for words? TODO
//                final String tags = editTags.getText().toString();
//                final String beginTime = editBeginTime.getText().toString();
//                final String endTime = editEndTime.getText().toString();
//                final String location = editLocation.getText().toString();
//                final String active = "true";
//                // groups
//                final String homemade_tag;
//                if (homemade.isChecked()) homemade_tag = "homemade";
//                else homemade_tag = "restaurant";
//                // all friends can view
//
//                String category = ""; // change to dynamic if time TODO
//
//                if (checkbox1.isChecked()) {
//                    category += checkbox1.getText();
//                    category += ", ";
//                }
//                if (checkbox2.isChecked()) {
//                    category += checkbox2.getText();
//                    category += ", ";
//                }
//                if (checkbox3.isChecked()) {
//                    category += checkbox3.getText();
//                    category += ", ";
//                }
//                if (checkbox4.isChecked()) {
//                    category += checkbox4.getText();
//                    category += ", ";
//                }
//                if (checkbox5.isChecked()) {
//                    category += checkbox5.getText();
//                    category += ", ";
//                }
//                if (checkbox6.isChecked()) {
//                    category += checkbox6.getText();
//                    category += ", ";
//                }
//                if (checkbox7.isChecked()) {
//                    category += checkbox7.getText();
//                    category += ", ";
//                }
//                if (checkbox8.isChecked()) {
//                    category += checkbox8.getText();
//                    category += ", ";
//                }
//                if (checkbox9.isChecked()) {
//                    category += checkbox9.getText();
//                    category += ", ";
//                }
//                if (checkbox10.isChecked()) {
//                    category += checkbox10.getText();
//                    category += ", ";
//                }
//                if (checkbox11.isChecked()) {
//                    category += checkbox11.getText();
//                    category += ", ";
//                }
//                if (checkbox12.isChecked()) {
//                    category += checkbox12.getText();
//                    category += ", ";
//                }
//                if (checkbox13.isChecked()) {
//                    category += checkbox13.getText();
//                    category += ", ";
//                }
//                if (checkbox14.isChecked()) {
//                    category += checkbox14.getText();
//                    category += ", ";
//                }
//                if (checkbox15.isChecked()) {
//                    category += checkbox15.getText();
//                    category += ", ";
//                }
//                if (checkbox16.isChecked()) {
//                    category += checkbox16.getText();
//                }
//
//                final String categories = category;
//                final String users = "";
//
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                //GETTING A NULL POINTER BELOW THIS
//
//                byte[] image;
//                if (yourbitmap == null) {
//                    Bundle extras = getIntent().getExtras();
//                    postID = extras.getInt("postID");
//                    PostRepo postRepo = new PostRepo(getApplicationContext());
//                    final Post post = postRepo.getPost(postID);
//                    image = post.getPhoto_image();
//                } else {
//                    yourbitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
//                    image = stream.toByteArray();
//                }
//
//                Log.d("debug", Integer.toString(image.length));
//                // if any field is not filled out, the user will not be able to save it
////                if (description.isEmpty() || owner.isEmpty() || food.isEmpty() ||
////                        food.isEmpty() || image.length == 0 || beginTime.isEmpty() ||
////                        endTime.isEmpty() || location.isEmpty() || active.isEmpty() ||
////                        users.isEmpty() || homemade_tag.isEmpty()) {
////                    Toast.makeText(getApplicationContext(), "Please fill out all fields and try again", Toast.LENGTH_SHORT).show();
////                    return;
////                }
//
//
//                // images in between food and num_requests
//                // groups in between active and usersRequested
//                // allFriendsCanView at end
//                Post post = new Post(description, owner, food, image, num_requests, categories, tags,
//                        beginTime, endTime, location, active, users, users, homemade_tag);
//
//                PostRepo postRepo = new PostRepo(getApplicationContext());
//
//                GroupRepo gr = new GroupRepo(getApplicationContext());
//                int groupID = gr.getGroupID(groupname);
//                post.addGroup(Integer.toString(groupID));
//
//                if (postID != 0) {
//                    post.setId(postID);
//                    postRepo.update(post);
//                    postId = postID;
//                }
//                else postId = postRepo.insert(post);
//
////                Intent intent = new Intent(CreatePostActivity.this, ViewPostActivity.class);
////                intent.putExtra("postID", postId);
////                startActivity(intent);
//
//                // TODO CHANGE THIS TO FRAGMENT AND GO TO VIEW POST FRAGMENT
//            }
//        });
//
//        return v;
//    }
//
//    private void selectImage() {
//
//        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePostFragment.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (options[item].equals("Take Photo")) {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, 1);
//
//                } else if (options[item].equals("Choose from Gallery")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 2);
//
//                } else if (options[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }
//
//}
