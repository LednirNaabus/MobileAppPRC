package com.example.mobileappprc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class side_Home extends AppCompatActivity  {

    private DrawerLayout drawer;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidehome);
        Toolbar toolbar = findViewById(R.id.sidetoolbar);
        setSupportActionBar(toolbar);
        button = findViewById(R.id.side_layout);
        drawer = findViewById(R.id.side_layout);

        NavigationView navigationView = findViewById(R.id.nav_sideview);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //code to remedy rotation of device
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_sidecontainer, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_sideview);
        }
    }
/*
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_home:
                //FOR APPLICATION
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
*\
 */
    //THIS IS SHIT THAT HAVING PROBLEM CONNECTING THE BUTTON XD
    public void openCloseNavigationDrawer() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            drawer.openDrawer(GravityCompat.START);
        }
    }

}
