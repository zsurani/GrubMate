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

import org.json.JSONArray;
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
    List<String> fbfriends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("DEBUG", "hola");

        dbHandler = new DatabaseHandler(this);
        db = dbHandler.getReadableDatabase();
        dbHandler.delete(db);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);


        Button tv =(Button)findViewById(R.id.button2);

        tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(getApplicationContext(),AndroidDatabaseManager.class);
                startActivity(dbmanager);
            }
        });

        Button notTest = (Button) findViewById(R.id.button3);
        notTest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CreateNotificationActivity.class);
                startActivity(i);
            }
        });

        Button getTransactionFromUser = (Button) findViewById(R.id.button4);
        getTransactionFromUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TransactionRepo nr = new TransactionRepo(getApplicationContext());
                List<String> note = nr.getTransactionsId(1);
                for(String l : note)
                {
                    Log.d("T", l);
                    Transaction n = nr.getTransaction(Integer.parseInt(l));
                    Log.d("TRANSACTION", n.getStatus());
                }
            }
        });


        loginButton = (LoginButton)findViewById(R.id.login_button);
        dbHandler = new DatabaseHandler(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                fbfriends = getFriendsList();
                String id = Profile.getCurrentProfile().getId();
                UserRepo userRepo = new UserRepo(getApplicationContext());
                boolean status = userRepo.newUser(id);
                if (status) {
                    String name = Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName();
                    User user = new User(name, id);
                    userRepo.insert(user);
                }
                Intent intent = new Intent(getApplicationContext(), CreatePostActivity.class);
                startActivity(intent);
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

    private List<String> getFriendsList(){
        final List<String> friendslist = new ArrayList<String>();
        new GraphRequest(AccessToken.getCurrentAccessToken(),"me/invitable_friends", null, HttpMethod.GET, new GraphRequest.Callback() {
            public void onCompleted(GraphResponse response) {
                /* handle the result */
                Log.e("Friends List: 1", response.toString());
                try {
                    JSONObject responseObject = response.getJSONObject();
                    JSONArray dataArray = responseObject.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject dataObject = dataArray.getJSONObject(i);
                        String fbId = dataObject.getString("id");
                        String fbName = dataObject.getString("name");
                        Log.e("FbID", fbId);
                        Log.e("FBName", fbName);
                        friendslist.add(fbId);
                    }
                    Log.e("fbfriendslist", friendslist.toString());
                    List<String> list = friendslist;
                    String friends = "";
                    if (list != null && list.size() > 0) {
                        friends = list.toString();
                        if (friends.contains("[")) {
                            friends = (friends.substring(1, friends.length() - 1));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
//                    hideLoadingProgress();
                }
            }
        }).executeAsync();
        return friendslist;
    }

}

