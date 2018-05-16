package com.example.user.aplikacija;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import Utils.PostService;
import Utils.UserService;
import adapters.CommentsAdapter;
import adapters.PostsAdapter;
import model.Comment;
import model.Post;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.Mockup;

public class PostsActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ArrayList<Post> posts;
    private ArrayList<Comment> comments;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        PostService postService = PostService.retrofit.create(PostService.class);
        final Call<JsonArray> call =
                postService.doGetPosts();

        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                List<Post> posts = Post.jsonArrayToList(response.body());
                Toast.makeText(PostsActivity.this, posts.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });









        setContentView(R.layout.activity_posts);
        ArrayList<Comment>  comments = new ArrayList<Comment>();
        Comment c1 = new Comment("Ajnstajn je krao od Mileve", "ajnstajn i teorija relativiteta", new Date(2016,12,12),12,2);
        comments.add(c1);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setIcon(R.drawable.ic_post);


        listView = (ListView) findViewById(R.id.listPostView);
       // posts = Mockup.getPosts();

        PostsAdapter postAdapter = new PostsAdapter(this, R.layout.posts_list, posts);


        listView.setAdapter(postAdapter);


        sortPostBy(posts);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_posts_pocetna:
                        Intent readPost = new Intent(PostsActivity.this, PostsActivity.class);
                        startActivity(readPost);
                        break;
                    case R.id.navigation_create2_post:
                        Intent createIntent = new Intent(PostsActivity.this, CreatePostActivity.class);
                        startActivity(createIntent);
                        break;
                    case R.id.navigation_settings:
                        Intent settIntent = new Intent(PostsActivity.this, SettingsActivity.class);
                        startActivity(settIntent);
                        break;



                }
                Toast.makeText(PostsActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
        });



/*
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.coordinator), "I'm a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(PostsActivity.this, "Snackbar Action", Toast.LENGTH_LONG).show();
                    }
                }).show();
            }
        });

*/
    //    FragmentTransition.to(MyFragment.newInstance(), this, false);


    }





    @Override
        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
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
                case R.id.action_settings:
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
        posts = Mockup.getPosts();
        sortPostBy(posts);

        PostsAdapter adapter = new PostsAdapter(this, R.layout.posts_list, posts);
        listView = findViewById(R.id.listPostView);
        listView.setAdapter(adapter);






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

    public void sortPostBy(ArrayList<Post> posts) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String sortPostBy = sp.getString("pref_sort_post", "default");


        if (sortPostBy.equals("Date")) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post1, Post post2) {
                    return post1.getDate().compareTo(post2.getDate());
                }
            });
        } else if (sortPostBy.equals("Popularity")) {
            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post post2, Post post1) {
                    if (post1.getPopularity() > post2.getPopularity()) {
                        return 1;
                    } else if (post1.getPopularity() < post2.getPopularity()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else {
            Toast.makeText(this, "Sorting went wrong, posts unsorted!\n" + sortPostBy, Toast.LENGTH_LONG).show();
        }
    }


    public void Izloguj_se(MenuItem item) {

        Intent createIntent = new Intent(PostsActivity.this, MainActivity.class);
        startActivity(createIntent);
        finish();

    }
}
