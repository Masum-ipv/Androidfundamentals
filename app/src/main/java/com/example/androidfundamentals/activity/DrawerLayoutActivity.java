package com.example.androidfundamentals.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.androidfundamentals.R;
import com.example.androidfundamentals.fragment.AppWithSettingsFragment;
import com.example.androidfundamentals.fragment.AsyncTaskFragment;
import com.example.androidfundamentals.fragment.BatteryLevelFragment;
import com.example.androidfundamentals.fragment.CheckBoxesFragment;
import com.example.androidfundamentals.fragment.ClickableImagesFragment;
import com.example.androidfundamentals.fragment.CountFragment;
import com.example.androidfundamentals.fragment.CustomDialogFragment;
import com.example.androidfundamentals.fragment.HelloSharedPrefsFragment;
import com.example.androidfundamentals.fragment.KeyboardDialPhoneFragment;
import com.example.androidfundamentals.fragment.MaterialMeFragment;
import com.example.androidfundamentals.fragment.NotificationSchedulerFragment;
import com.example.androidfundamentals.fragment.NotifyMeFragment;
import com.example.androidfundamentals.fragment.ParagraphFragment;
import com.example.androidfundamentals.fragment.PowerReceiverFragment;
import com.example.androidfundamentals.fragment.RecyclerViewFragment;
import com.example.androidfundamentals.fragment.RoomWordsSampleFragment;
import com.example.androidfundamentals.fragment.ScorekeeperFragment;
import com.example.androidfundamentals.fragment.StandUpFragment;
import com.example.androidfundamentals.fragment.TabExpFragment;
import com.example.androidfundamentals.fragment.TwoActivitiesFragment;
import com.example.androidfundamentals.fragment.WebPageFragment;
import com.example.androidfundamentals.fragment.WhoWroteItFragment;
import com.google.android.material.navigation.NavigationView;

public class DrawerLayoutActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) { //Will not change selected fragment after screen rotate
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CountFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.count_layout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CountFragment()).commit();
        } else if (item.getItemId() == R.id.text_layout) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ParagraphFragment()).commit();
        } else if (item.getItemId() == R.id.two_activities) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TwoActivitiesFragment()).commit();
        } else if (item.getItemId() == R.id.images) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ClickableImagesFragment()).commit();
        } else if (item.getItemId() == R.id.dial_phone) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new KeyboardDialPhoneFragment()).commit();
        } else if (item.getItemId() == R.id.checkbox_menu) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CheckBoxesFragment()).commit();
        } else if (item.getItemId() == R.id.dialog) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CustomDialogFragment()).commit();
        } else if (item.getItemId() == R.id.tab_experiment) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TabExpFragment()).commit();
        } else if (item.getItemId() == R.id.recyclerview) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new RecyclerViewFragment()).commit();
        } else if (item.getItemId() == R.id.score) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ScorekeeperFragment()).commit();
        } else if (item.getItemId() == R.id.battery) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new BatteryLevelFragment()).commit();
        } else if (item.getItemId() == R.id.material) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MaterialMeFragment()).commit();
        } else if (item.getItemId() == R.id.async_task) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AsyncTaskFragment()).commit();
        } else if (item.getItemId() == R.id.who_wrote_it) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WhoWroteItFragment()).commit();
        } else if (item.getItemId() == R.id.web_page) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WebPageFragment()).commit();
        } else if (item.getItemId() == R.id.power_receiver) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PowerReceiverFragment()).commit();
        } else if (item.getItemId() == R.id.notify_me) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new NotifyMeFragment()).commit();
        } else if (item.getItemId() == R.id.stand_up) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new StandUpFragment()).commit();
        } else if (item.getItemId() == R.id.notification_scheduler) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new NotificationSchedulerFragment()).commit();
        } else if (item.getItemId() == R.id.shared_pref) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HelloSharedPrefsFragment()).commit();
        } else if (item.getItemId() == R.id.app_settings) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AppWithSettingsFragment()).commit();
        } else if (item.getItemId() == R.id.room_words) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new RoomWordsSampleFragment()).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}