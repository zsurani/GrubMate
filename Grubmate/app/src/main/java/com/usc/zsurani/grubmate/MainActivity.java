package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("DEBUG", "hola");

        dbHandler = new DatabaseHandler(this);
        db = dbHandler.getReadableDatabase();
        dbHandler.delete(db);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        Intent i = new Intent(getApplicationContext(), CreateNotificationActivity.class);
        startActivity(i);

        Button tv =(Button)findViewById(R.id.button2);

        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(getApplicationContext(),AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });



        loginButton = (LoginButton)findViewById(R.id.login_button);
        dbHandler = new DatabaseHandler(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                //startActivity(i);

                String id = Profile.getCurrentProfile().getId();
//                UserRepo userRepo = new UserRepo(getApplicationContext());
//                boolean status = userRepo.newUser(id);
//                if (status) {
//                    Log.d("DEBUG", "new");
//                }
//                else {
//                    Log.d("DEBUG", "nah");
//                }

            }

            @Override
            public void onCancel() {
                textView.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getUserDetailsFromFB() {

        Bundle parameters = new Bundle();
        parameters.putString("fields", "email,name,picture");

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        try {
                            String email = response.getJSONObject().getString("email");
//                            mEmailID.setText(email);
                            Log.d("DEBUG", "hola");
                            String name = response.getJSONObject().getString("name");
//                            mUsername.setText(name);

                            JSONObject picture = response.getJSONObject().getJSONObject("picture");
                            JSONObject data = picture.getJSONObject("data");
                            String pictureUrl = data.getString("url");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

    }

}

