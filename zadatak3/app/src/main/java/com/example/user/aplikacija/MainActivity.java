package com.example.user.aplikacija;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;

import Utils.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
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

    public void btnPostsActivity(View view) {



        UserService userTestService = UserService.retrofit.create(UserService.class);
        final Call<JsonObject> call =
                userTestService.doGetUsers();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                String result = response.body().toString();

                JsonObject jsonObject = response.body();

                JsonArray usersJsonArray = jsonObject.get("users").getAsJsonArray();

                EditText editTextUsername = (EditText) findViewById(R.id.input_email);
                EditText editTextPassword = (EditText) findViewById(R.id.input_password);

                String usernameForValidation = editTextUsername.getText().toString();
                String passwordForValidation = editTextPassword.getText().toString();

                String userFound = "Not Found";

                for (JsonElement jsonElement : usersJsonArray) {
                    //
                    JsonObject user = jsonElement.getAsJsonObject();
                    String username = user.get("username").getAsString();
                    String password = user.get("password").getAsString();

                    if (username.equals(usernameForValidation) && password.equals(passwordForValidation)) {
                        userFound = "User Found";


                        Toast.makeText(MainActivity.this, userFound, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                        startActivity(intent);

                        finish();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                String result = t.getMessage();
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }

        });
    }

}
