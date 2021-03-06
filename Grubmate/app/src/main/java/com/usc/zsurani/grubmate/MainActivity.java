package com.usc.zsurani.grubmate;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import com.facebook.login.LoginManager;

import com.facebook.login.widget.LoginButton;

import com.usc.zsurani.grubmate.activity_and_fragment.AddGroupMembersFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.CreatePostActivity;
import com.usc.zsurani.grubmate.activity_and_fragment.EnterLocationFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.MyGroupFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.MyNotificationFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.NewsFeedFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.NotificationCenterFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.ProfileFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.RatingReviewFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.SearchResultsFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.SortFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.SortResultFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.TransactionHistoryFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.ViewGroupFragment;
import com.usc.zsurani.grubmate.activity_and_fragment.ViewPostFragment;
import com.usc.zsurani.grubmate.adapters.DrawerItemCustomAdapter;
import com.usc.zsurani.grubmate.base_classes.Profiles;
import com.usc.zsurani.grubmate.base_classes.User;
import com.usc.zsurani.grubmate.com.usc.zsurani.grubmate.repos.UserRepo;
import com.usc.zsurani.grubmate.databases.AndroidDatabaseManager;
import com.usc.zsurani.grubmate.databases.DatabaseHandler;
import com.usc.zsurani.grubmate.other_UI.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    LoginButton loginButton;
    TextView textView;
    CallbackManager callbackManager;
    DatabaseHandler dbHandler;
    SQLiteDatabase db;
    List<String> fbfriends = new ArrayList<>();
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private boolean mHasFragment = false;


    private static final String KEY_HAS_FRAGMENT = "grubmate.main_activity.has_fragment";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mHasFragment = savedInstanceState.getBoolean(KEY_HAS_FRAGMENT);
        }

        dbHandler = new DatabaseHandler(this);
        db = dbHandler.getReadableDatabase();
//        dbHandler.delete(db);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

        DataModel[] drawerItem = new DataModel[7];

        drawerItem[0] = new DataModel("My Profile");
        drawerItem[1] = new DataModel("My Newsfeed");
        drawerItem[2] = new DataModel("My Groups");
        drawerItem[3] = new DataModel("My Notifications");
        drawerItem[4] = new DataModel("My Transactions");
        drawerItem[5] = new DataModel("Notification Center");
        drawerItem[6] = new DataModel("Log Out");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();

        if (Profile.getCurrentProfile() == null) {
            Button tv = (Button) findViewById(R.id.button2);

            tv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Intent dbmanager = new Intent(getApplicationContext(), AndroidDatabaseManager.class);
                    startActivity(dbmanager);
                }
            });

            loginButton = (LoginButton) findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList("user_friends"));
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
                        Profiles p = new Profiles();
                        p.setName(Profile.getCurrentProfile().getName());
                        p.setId(Profile.getCurrentProfile().getId());
                        p.setUri(Profile.getCurrentProfile().getProfilePictureUri(125, 125));
                        userRepo.insertProfile(p);
                    }
                    loginButton.setVisibility(View.INVISIBLE);
                    findViewById(R.id.button2).setVisibility(View.INVISIBLE);

                    Fragment fragment = null;
                    fragment = new NewsFeedFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(mNavigationDrawerItemTitles[1]).commit();

//                Intent intent = new Intent(getApplicationContext(), NewsFeedFragment.class);
//                startActivity(intent);
                }

                @Override
                public void onCancel() {
//                textView.setText("Login Cancelled");
                    Log.d("MAIN ACTIVITY", "LOGIN CANCELLED");
                }

                @Override
                public void onError(FacebookException error) {

                }
            });


            // for screen rotation
            if (mHasFragment) {
                // hide the main activity layout
                loginButton.setVisibility(View.INVISIBLE);
                tv.setVisibility(View.INVISIBLE);
            } else {
                // show the main activity (facebook layout)
                loginButton.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
            }

        /*
        Integer intentFragment = getIntent().getExtras().getInt("frgToLoad");
        if(intentFragment != null)
        {
            selectItem(intentFragment);
        }
        */
        }
        else{
            Fragment fragment = null;
            fragment = new NewsFeedFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(mNavigationDrawerItemTitles[1]).commit();

            mDrawerList.setItemChecked(1, true);
            mDrawerList.setSelection(1);
            setTitle(mNavigationDrawerItemTitles[1]);
            mDrawerLayout.closeDrawer(mDrawerList);

            findViewById(R.id.button2).setVisibility(View.INVISIBLE);
            findViewById(R.id.login_button).setVisibility(View.INVISIBLE);

            mHasFragment = true;
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_HAS_FRAGMENT, mHasFragment);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new ProfileFragment();
                break;
            case 1:
                fragment = new NewsFeedFragment();
                break;
            case 2:
                fragment = new MyGroupFragment();
                break;
            case 3:
                fragment = new MyNotificationFragment();
                break;
            case 4:
                fragment = new TransactionHistoryFragment();
                break;
            case 5:
                fragment = new NotificationCenterFragment();
                break;
            case 6:
                //fragment = new NotificationCenterFragment();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(mNavigationDrawerItemTitles[position]).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

            findViewById(R.id.button2).setVisibility(View.INVISIBLE);
            findViewById(R.id.login_button).setVisibility(View.INVISIBLE);

            mHasFragment = true;
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void goToMainFragment(int fragId) {
        selectItem(fragId);
    }

    public void goToFragment(int fragId, int arg1, int arg2) {
        Fragment fragment = null;

        switch (fragId) {
            case 0: // rating review fragment
                fragment = RatingReviewFragment.newInstance(arg1);
                break;
            case 1: // enter location fragment
                fragment = EnterLocationFragment.newInstance(arg1, arg2);
                break;
            case 2: // view post fragment
                fragment = ViewPostFragment.newInstance(arg1);
                break;
            case 4: // view group fragment
                fragment = ViewGroupFragment.newInstance();
                break;
            case 3: // sort fragment
                fragment = SortFragment.newInstance();
                break;
            default:

                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            String title = getResources().getStringArray(R.array.frag_names_1)[fragId];

            ft.replace(R.id.content_frame, fragment).addToBackStack(title).commitAllowingStateLoss();
            setTitle(title);
            mHasFragment = true;

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public void goToFragment(int fragId, String arg1) {
        Fragment fragment = null;
        int stringPos = 0;

        switch (fragId) {
            case 10: // add group members
                fragment = AddGroupMembersFragment.newInstance(arg1);
                stringPos = 0;
                break;
            case 11: // search results
                fragment = SearchResultsFragment.newInstance(arg1);
                stringPos = 1;
                break;
            case 12: // sort results
                fragment = SortResultFragment.newInstance(arg1);
                stringPos = 2;
                break;
            case 13: //view post fragment
                fragment = ProfileFragment.newInstance(Integer.parseInt(arg1));
                break;
            default:

                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            String title = getResources().getStringArray(R.array.frag_names_string)[stringPos];

            ft.replace(R.id.content_frame, fragment).addToBackStack(title).commitAllowingStateLoss();
            setTitle(title);
            mHasFragment = true;
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.grubmate_title);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        FragmentManager fm = getSupportFragmentManager();
        Fragment current = fm.findFragmentById(R.id.content_frame);
        if (current instanceof MyNotificationFragment) {
            ((MyNotificationFragment) current).refresh();
        }

        if (resultCode == CreatePostActivity.RESULT_POST_CREATED) {
            int postId = data.getIntExtra("PostID", 0);
            goToFragment(2, postId, -1);
        }
    }

    private List<String> getFriendsList(){
        final List<String> friendslist = new ArrayList<String>();
        new GraphRequest(AccessToken.getCurrentAccessToken(),"me/friends", null, HttpMethod.GET, new GraphRequest.Callback() {
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
                        friendslist.add(fbName);
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
                    UserRepo userRepo = new UserRepo(getApplicationContext());
                    userRepo.insertFriends(friends);
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
//                    hideLoadingProgress();
                }
            }
        }).executeAsync();
        return friendslist;
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}

