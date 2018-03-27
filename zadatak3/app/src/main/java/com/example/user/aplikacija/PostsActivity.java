package com.example.user.aplikacija;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.view.View;

public class PostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
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

    public void btnCreatePostActivity(View view) {
        Intent kreirajPostIntent = new Intent(this, CreatePostActivity.class);
        startActivity(kreirajPostIntent);
    }

    public void btnReadPostActivity(View view) {
        Intent procitajPostIntent = new Intent(this, ReadPostActivity.class);
        startActivity(procitajPostIntent);
    }

    public void btnSettingsPostActivity(View view) {
        Intent podesavanjaIntent = new Intent(this, SettingsActivity.class);
        startActivity(podesavanjaIntent);
    }
}
