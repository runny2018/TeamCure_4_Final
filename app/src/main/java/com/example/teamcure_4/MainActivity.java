package com.example.teamcure_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;



public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Whatever";

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomNavigationView bottomNav=findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);


        //last line
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AlloutFragment())
                .commit();


    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;

                    switch(menuItem.getItemId()){
                        case R.id.nav_allout:
                            selectedFragment=new AlloutFragment();
                            break;

                        case R.id.nav_android:
                            selectedFragment=new AndroidFragment();
                            break;

                        case R.id.nav_announce:
                            selectedFragment=new AnnounceFragment();
                            break;

                        case R.id.nav_apps:
                            selectedFragment=new AppsFragment();
                            break;

                        case R.id.nav_archive:
                            selectedFragment=new ArchiveFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment)
                            .commit();

                    return true;
                }
            };
}
