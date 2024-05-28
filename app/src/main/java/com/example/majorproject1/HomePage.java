package com.example.majorproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView nvDrawer = (NavigationView)findViewById(R.id.navigationview);

        // Set navigation item selection listener
        navigationView.setNavigationItemSelectedListener(this);


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
                Intent intent=new Intent(HomePage.this,DietGen.class);
                startActivity(intent);
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
                        Intent intent =new Intent(HomePage.this,medicine.class);
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

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String packename = getPackageName();
        int itemId = item.getItemId();
        if (itemId == R.id.MedRem) {
            startActivity(new Intent(HomePage.this, Prediction.class));
        } else if (itemId == R.id.Progress) {
            // Handle Track Progress click
            // Add your logic here
        } else if (itemId == R.id.Login) {
            clearUserLoggedInState();
            logoutUser();
        } else if (itemId == R.id.Logout) {
            clearUserLoggedInState();
            logoutUser();
        } else if (itemId == R.id.Share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"Check this App\n"+""+packename);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent,"Share this app"));
        } else if (itemId == R.id.Refer) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT,"Check this App\n"+""+packename);
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent,"Refer this app"));
        }

        // Close the drawer after selecting an item
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void clearUserLoggedInState() {
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("isLoggedIn");
        editor.apply();
    }

    private void logoutUser() {
        clearUserLoggedInState();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish current activity to prevent going back to logged-in state
    }
}