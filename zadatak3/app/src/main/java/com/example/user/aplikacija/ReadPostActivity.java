package com.example.user.aplikacija;

import android.content.Intent;
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

import fragments.CommentFragment;
import fragments.MyFragment;
import model.Post;

public class ReadPostActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;



    private SectionsPagerAdapter mSectionsPagerAdapter;


    private Post activityPost;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

       // setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);


        // odavde je novo dodato
        Post post = (Post) getIntent().getSerializableExtra("post");
        this.activityPost = post;

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs1);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));






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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // The post that the adapter is showing
        public Post fragmentPost;

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
                    MyFragment rpf = new MyFragment();
                    rpf.setArguments(bundle);
                    return rpf;
                case 1:
                    CommentFragment rcf = new CommentFragment();
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
