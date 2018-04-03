package com.example.user.aplikacija;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;


import java.net.URI;
import java.net.URL;

public class CreatePostActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    ImageView imageView;
    Button button;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

           Toolbar toolbar = (Toolbar) findViewById(R.id.createtoolbar);
//          setSupportActionBar(toolbar);
         ActionBar actionBar = getSupportActionBar();
         actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
         actionBar.setDisplayHomeAsUpEnabled(true);


        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()) {

                    case R.id.nav_posts_pocetna:
                        Intent pocetnaIntent = new Intent(CreatePostActivity.this, PostsActivity.class);
                        startActivity(pocetnaIntent);
                        break;

                    case R.id.nav_posts_prikaz_objava:
                        Intent readPost = new Intent(CreatePostActivity.this, ReadPostActivity.class);
                        startActivity(readPost);
                        break;
                    case R.id.nav_posts_pravljenje_objave:
                        Intent createPost = new Intent(CreatePostActivity.this, CreatePostActivity.class);
                        startActivity(createPost);
                        break;
                    case R.id.nav_posts_settings:
                        Intent settingsPost = new Intent(CreatePostActivity.this, SettingsActivity.class);
                        startActivity(settingsPost);
                        break;


                }
                return true;
            }

        });



        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openGallery();
            }
        });
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

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }





}
