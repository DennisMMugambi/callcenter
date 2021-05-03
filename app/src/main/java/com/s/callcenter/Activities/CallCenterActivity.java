package com.s.callcenter.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.messaging.FirebaseMessaging;
import com.s.callcenter.Fragments.ActiveRequestsFragment;
import com.s.callcenter.Fragments.ReviewsFragment;
import com.s.callcenter.Fragments.ServiceHistoryFragment;
import com.s.callcenter.Fragments.TechniciansFragment;
import com.s.callcenter.R;
import com.s.callcenter.Services.NotificationService;
import com.s.callcenter.Services.NotificationWorker;

import java.util.concurrent.TimeUnit;

public class CallCenterActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private DatabaseReference mRef;
    private RecyclerView mRecyclerView;
    private Toolbar toolbar;
    private DatabaseReference activeRequestsRef;
    IntentFilter mPoweredIntentFilter;
    PoweredBroadcastReceiver mPoweredReceiver;




    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.

    private ActionBarDrawerToggle drawerToggle;
    private WorkManager mWorkManager;
    PeriodicWorkRequest workRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_center);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        dl = (DrawerLayout)findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        mPoweredIntentFilter = new IntentFilter();
        mPoweredReceiver = new PoweredBroadcastReceiver();
        //mPoweredIntentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        //mPoweredIntentFilter.addAction(Intent.ACTION_SCREEN_ON);

       // Intent intent = new Intent(this, NotificationService.class);
        //startService(intent);



        // This will display an Up icon (<-), we will replace it with hamburger later
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nv);


        drawerToggle = setupDrawerToggle();


        // Setup toggle to display hamburger icon with nice animation

        drawerToggle.setDrawerIndicatorEnabled(true);

        drawerToggle.syncState();
        dl.setDrawerListener(drawerToggle);


       // startActivity(new Intent(CallCenterActivity.this, TechniciansFragment.class));
        TechniciansFragment techniciansFragment = new TechniciansFragment();
        setFragment(techniciansFragment);
       // mWorkManager = WorkManager.getInstance(getApplicationContext());
       // workRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 5, TimeUnit.SECONDS).build();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {

        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it

        // and will not render the hamburger icon without it.

        return new ActionBarDrawerToggle(this, dl, toolbar, R.string.drawer_open,  R.string.drawer_close);

    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override

                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.sign_out){
                            AlertDialog.Builder builder = new AlertDialog.Builder(CallCenterActivity.this);
                            builder.setTitle("Sign out")
                                    .setMessage("Confirm you wish to sign out")
                                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    }).setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(CallCenterActivity.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                                    .setCancelable(false);
                            AlertDialog dialog = builder.create();
                            dialog.setOnShowListener(dialogInterface -> {
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                                        .setTextColor(getResources().getColor(R.color.colorAccent));
                            });
                            dialog.show();
                        }

                        selectDrawerItem(menuItem);

                        return true;

                    }

                });

    }

    public void selectDrawerItem(MenuItem menuItem) {

        // Create a new fragment and specify the fragment to show based on nav item clicked

        Fragment fragment = null;

        Class fragmentClass = null;

        switch(menuItem.getItemId()) {

            case R.id.technicians:

                fragmentClass = TechniciansFragment.class;

                break;

            case R.id.reviews:

                fragmentClass = ReviewsFragment.class;

                break;

            case R.id.history:

                fragmentClass = ServiceHistoryFragment.class;

                break;

            case R.id.active_requests:

                fragmentClass = ActiveRequestsFragment.class;

                break;

            default:

                fragmentClass = TechniciansFragment.class;

        }


        try {

            fragment = (Fragment) fragmentClass.newInstance();

        } catch (Exception e) {

            e.printStackTrace();

        }



        // Insert the fragment by replacing any existing fragment

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();



        // Highlight the selected item has been done by NavigationView

        menuItem.setChecked(true);

        // Set action bar title

        setTitle(menuItem.getTitle());

        // Close the navigation drawer

        dl.closeDrawers();

    }




    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {

            return true;

        }

        return super.onOptionsItemSelected(item);

    }
    @Override

    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        // Sync the toggle state after onRestoreInstanceState has occurred.

        drawerToggle.syncState();

    }



    @Override

    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        // Pass any configuration change to the drawer toggles

        drawerToggle.onConfigurationChanged(newConfig);

    }

    public void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.flContent, fragment)
                .commit();
    }

    private class PoweredBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
           // boolean isPowered = (action.equals(Intent.ACTION_POWER_DISCONNECTED));
           // mWorkManager.enqueue(workRequest);
        }
    }
}