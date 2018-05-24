package com.example.user.aplikacija;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Utils.PostService;
import Utils.RetrofitObject;
import de.hdodenhof.circleimageview.CircleImageView;
import fragments.CommentFragment;
import fragments.MyFragment;
import model.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tools.CommentDialog;

public class ReadPostActivity extends AppCompatActivity implements CommentDialog.CommentDialogListener {
    private DrawerLayout mDrawerLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    //private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    // post koji prikazuje
    private int postId;
    private Post activityPost;

    // id i mail ulogovanog user-a
    private int loggedInUserId;
    private String loggedInUserMail;

    // dugme za brisanje posta
    private MenuItem deleteButton;

    // dugme za dodavanje komentara
    private MenuItem addCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        postId = getIntent().getExtras().getInt("postId");

  /*
        Post post = (Post) getIntent().getSerializableExtra("post");
        this.activityPost = post;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
*/
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setIcon(R.drawable.ic_action_accept);
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
        }


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // izvlacim id i mail iz sharedPref sacuvane u login activity
        SharedPreferences sharedPreferences = getSharedPreferences("sp", MODE_PRIVATE);
        this.loggedInUserId = sharedPreferences.getInt("userId", 0);
        this.loggedInUserMail = sharedPreferences.getString("userEmail", null);

        // postavljam mail i sliku u header.. mora ovde nakon inicijalizacije navigationView-a
        TextView headerEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
        headerEmail.setText(loggedInUserMail);

        CircleImageView headerUserPicture = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.circleView);
        if (loggedInUserMail.equals("andrej")) {
            headerUserPicture.setImageDrawable(getResources().getDrawable(R.drawable.einstein));
        } else {
            headerUserPicture.setImageDrawable(getResources().getDrawable(R.drawable.newton));
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                mDrawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {

                    case R.id.nav_posts_prikaz_objava:
                        Intent i = new Intent(ReadPostActivity.this, PostsActivity.class);
                        startActivity(i);
                        break;

                    case R.id.nav_posts_pravljenje_objave:
                        Intent i1 = new Intent(ReadPostActivity.this, CreatePostActivity.class);
                        startActivity(i1);
                        break;

                    case R.id.navigation_settings:
                        Intent i2 = new Intent(ReadPostActivity.this, SettingsActivity.class);
                        startActivity(i2);
                        break;

                    case R.id.navigation_logout:
                        Intent i3 = new Intent(ReadPostActivity.this, MainActivity.class);
                        startActivity(i3);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        PostService postService = RetrofitObject.retrofit.create(PostService.class);
        Call<Post> call = postService.getOne(postId);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                activityPost = response.body();

                if (activityPost.getAuthor().getId() == loggedInUserId) {
                    deleteButton.setVisible(true);
                }

                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                mViewPager.setAdapter(mSectionsPagerAdapter);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to load post", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_itemdetail, menu);

        //MenuItem addItem = menu.findItem(R.id.action_new);
        MenuItem refreshItem = menu.findItem(R.id.action_refresh);
        MenuItem settingItem = menu.findItem(R.id.action_settings);
        MenuItem acceptItem = menu.findItem(R.id.action_accept);
        MenuItem cancelItem = menu.findItem(R.id.action_cancel);
        deleteButton = menu.findItem(R.id.action_delete);
        addCommentButton = menu.findItem(R.id.action_new);

        //addItem.setVisible(false);
        refreshItem.setVisible(false);
        settingItem.setVisible(false);
        acceptItem.setVisible(false);
        cancelItem.setVisible(false);
        deleteButton.setVisible(false);
        addCommentButton.setVisible(true);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.action_delete) {
            btnDeletePost();
        } else if (id == R.id.action_new) {
            openDialog();
        }

        return super.onOptionsItemSelected(menuItem);
    }


    private void openDialog() {
        CommentDialog commentDialog = new CommentDialog();
        commentDialog.show(getSupportFragmentManager(), "comment dialog");
    }

    @Override
    public void applyTexts(String title, String description) {

    }

    private void btnDeletePost() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete post?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletePost();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deletePost() {
        PostService postService = RetrofitObject.retrofit.create(PostService.class);
        Call<ResponseBody> call = postService.deletePost(postId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                response.body().close();
                Toast.makeText(getApplicationContext(), "Successful deleted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ReadPostActivity.this, PostsActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro while deleting", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // The post that the adapter is showing
        private Post fragmentPost;
        private MyFragment rpf;
        private CommentFragment rcf;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragmentPost = activityPost;
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Bundle bundle = new Bundle();
            bundle.putSerializable("post", this.fragmentPost);
            switch (position) {
                case 0:
                    rpf = new MyFragment();
                    rpf.setArguments(bundle);
                    return rpf;
                case 1:
                    rcf = new CommentFragment();
                    rcf.setArguments(bundle);
                    return rcf;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}