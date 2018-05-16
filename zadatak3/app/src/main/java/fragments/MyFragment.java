package fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aplikacija.PostsActivity;
import com.example.user.aplikacija.R;
import com.example.user.aplikacija.ReadPostActivity;

import adapters.PostsAdapter;
import model.Post;
import tools.Mockup;


public class MyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View myInflatedView = inflater.inflate(R.layout.content_detail, container,false);
////////////
        Post post = (Post) getArguments().getSerializable("post");
////////////////
        TextView postTitle = (TextView) myInflatedView.findViewById(R.id.tvTitle);
        postTitle.setText(post.getTitle());

        TextView postDescription = (TextView) myInflatedView.findViewById(R.id.tvDescription);
        postDescription.setText(post.getDescription());

        ImageView image = (ImageView) myInflatedView.findViewById(R.id.ivPhoto);

        TextView postDate = (TextView) myInflatedView.findViewById(R.id.tvDate);
        String dateString = post.getDate().toString().substring(3, 16);
        postDate.setText(dateString);


        if (post.getAvatar() == -1){
            image.setImageResource(R.drawable.einstein);
        }
        if (post.getAvatar() == -2){
            image.setImageResource(R.drawable.tesla);
        }
        if (post.getAvatar() == -3){
            image.setImageResource(R.drawable.ramanujan);
        }
        if (post.getAvatar() == -4){
            image.setImageResource(R.drawable.newton);
        }
        if (post.getAvatar() == -5){
            image.setImageResource(R.drawable.kepler);
        }

        return myInflatedView;
    }


    //Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();

        //Dodaje se adapter
       // PostsAdapter adapter = new PostsAdapter(getActivity());
       // setListAdapter(adapter);
    }
/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.activity_itemdetail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_refresh){
            Toast.makeText(getActivity(), "Refresh App", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.action_new){
            Toast.makeText(getActivity(), "Create Text", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }*/
