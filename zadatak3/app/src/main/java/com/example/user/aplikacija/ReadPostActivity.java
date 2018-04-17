package com.example.user.aplikacija;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ReadPostActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                switch (item.getItemId()){

                    case R.id.nav_posts_pocetna:
                        Intent pocetnaIntent = new Intent(ReadPostActivity.this, PostsActivity.class);
                        startActivity(pocetnaIntent);
                        break;


                    case R.id.nav_posts_prikaz_objava:
                        Intent prIntent = new Intent(ReadPostActivity.this, ReadPostActivity.class);
                        startActivity(prIntent);
                        break;
                    case R.id.nav_posts_pravljenje_objave:
                        Intent createIntent = new Intent(ReadPostActivity.this, CreatePostActivity.class);
                        startActivity(createIntent);
                        break;
                    case R.id.nav_posts_settings:
                        Intent settingsIntent = new Intent(ReadPostActivity.this, SettingsActivity.class);
                        startActivity(settingsIntent);
                        break;

                }



                Toast.makeText(ReadPostActivity.this, item.getTitle(),Toast.LENGTH_LONG).show();
                return true;
            }
        });


        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        TextView tvDescription = (TextView)findViewById(R.id.tvDescription);
        ImageView ivPhoto = (ImageView)findViewById(R.id.ivPhoto);

        tvTitle.setText(getIntent().getStringExtra("title"));
        tvDescription.setText(getIntent().getStringExtra("description"));
        

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.read_post_toolbar, menu);
        return true;
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

}
