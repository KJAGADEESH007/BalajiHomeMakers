package com.bca.balajihomcare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class homescreen extends AppCompatActivity {

    private ImageButton sell, buy;
    private ImageButton btnhome, btnsolution, btnchat, btnlog, btnprofile;
    public DrawerArrowDrawable drawerArrowDrawable;
    ImageView drawerIndicator;
    public float offset;
    public boolean flipped;
    public DrawerLayout drawer;
    protected FrameLayout mDrawerLayout, actionBar;
    private FirebaseAuth auth;
    FrameLayout viewContainer;
    LayoutInflater inflater;
    View contentView;
    TextView nameTV,emailTV;
    ImageView actionBarLogo;

    BottomNavigationView bottomNavigation;
    private AppBarConfiguration mAppBarConfiguration;
    //Navigation Menu and Header
    private NavigationView navigationView;
    private RelativeLayout drawerHead;
    public static String email;
    public static String name;
    private ImageView profileImageIV;
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "UserPref" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homescreen);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        email=auth.getCurrentUser().getEmail();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        nameTV=(TextView) headerView.findViewById(R.id.nameTV);
        emailTV=(TextView) headerView.findViewById(R.id.emailTV);
        emailTV.setText(email);
        nameTV.setVisibility(View.GONE);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                auth.signOut();
                Intent i=new Intent(homescreen.this,loginActivity.class);
                startActivity(i);
                finish();
                return true;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}


