package com.example.user.aplikacija;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
 /*       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //item.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()){


                    case R.id.nav_posts_pocetna:
                        Intent pocetnaIntent = new Intent(SettingsActivity.this, PostsActivity.class);
                        startActivity(pocetnaIntent);
                        break;
                    case R.id.navigation_item_post:
                        Intent readIntent = new Intent(SettingsActivity.this, ReadPostActivity.class);
                        startActivity(readIntent);
                        break;
                    case R.id.navigation_create2_post:
                        Intent createIntent = new Intent(SettingsActivity.this, CreatePostActivity.class);
                        startActivity(createIntent);
                        break;
                    case R.id.navigation_settings:
                        mDrawerLayout.closeDrawers();
                        break;

                }


                Toast.makeText(SettingsActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });


    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home)
//            finish();
//        return super.onOptionsItemSelected(item);
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

/*    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){

            case R.id.navigation_item_post:
                Intent postIntent = new Intent(SettingsActivity.this, PostsActivity.class);
                startActivity(postIntent);
                break;
            case R.id.navigation_item_location:
                Intent settingsIntent = new Intent(SettingsActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;



    }*/




    public void selectItem(int position){
        Intent intent = null;
        switch(position){
            case 0:
                intent = new Intent(this, PostsActivity.class);
                break;
            case 1:
                intent = new Intent(this, SettingsActivity.class);
                break;
        }
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void btnBackOnPost(View view) {
        Intent postActivity = new Intent(this, PostsActivity.class);
        startActivity(postActivity);

    }
}



