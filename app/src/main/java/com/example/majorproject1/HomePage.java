package com.example.majorproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
       requestWindowFeature(Window.FEATURE_NO_TITLE);
       this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_home_page);

        toolbar =findViewById(R.id.appbar);
        drawerLayout = findViewById(R.id.drawlayout);
        navigationView=findViewById(R.id.navigationview);
        CardView diabeticsCardView = findViewById(R.id.Predict);
        CardView monitoringCardView = findViewById(R.id.Monitor);
        CardView recoveryCardView = findViewById(R.id.Recovery);
        CardView classifyCardView = findViewById(R.id.Classify);

        setSupportActionBar(toolbar);
       ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawerLayout.addDrawerListener(toggle);
        //toolbar.setLogo(R.drawable.menu);
        toggle.syncState();

        // Set up click listeners for various UI elements
        diabeticsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomePage.this,Prediction.class);
                startActivity(intent);
            }
        });

        monitoringCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Monitoring
                Toast.makeText(HomePage.this, "Monitoring Clicked", Toast.LENGTH_SHORT).show();
                // Add your logic here
            }
        });

        recoveryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Recovery
                Toast.makeText(HomePage.this, "Recovery Clicked", Toast.LENGTH_SHORT).show();
                // Add your logic here
            }
        });

        classifyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click on Classify
                Toast.makeText(HomePage.this, "Classify Clicked", Toast.LENGTH_SHORT).show();
                // Add your logic here
            }
        });

        //Bottom Menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    int itemId = item.getItemId();

                    if (itemId == R.id.homeitem) {

                        return true;
                    } else if (itemId == R.id.bellitem) {
                        Intent intent =new Intent(HomePage.this,notifications_Activity.class);
                        startActivity(intent);
                        return true;
                    } else if (itemId == R.id.profileitem) {
                        Intent intent1 =new Intent(HomePage.this,UserProfile.class);
                        startActivity(intent1);
                        return true;
                    }
                    return false;
            }
        });
    }
}