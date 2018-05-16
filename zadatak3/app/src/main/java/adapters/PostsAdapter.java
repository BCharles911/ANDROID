package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.aplikacija.R;
import com.example.user.aplikacija.ReadPostActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Post;
import tools.Mockup;

public class PostsAdapter extends ArrayAdapter<Post> {
    private Context context;
    private int resource;
    ArrayList<Post> posts = new ArrayList<Post>();


    public PostsAdapter(@NonNull Context context, int resource, ArrayList<Post> posts) {
        super(context, resource, posts);
        this.context = context;
        this.resource = resource;
        this.posts = posts;
    }


    @Override
    public int getCount() {
        return Mockup.getPosts().size();
    }



    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        final Post post = Mockup.getPosts().get(position);

        if(convertView==null)
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);


        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView description = (TextView)convertView.findViewById(R.id.description);
        ImageView image = (ImageView)convertView.findViewById(R.id.item_icon);
        TextView tvDate = (TextView)convertView.findViewById(R.id.date);
        TextView tvPopularity = (TextView) convertView.findViewById(R.id.popularity);

        title.setText(post.getTitle());
        description.setText(post.getDescription());

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


        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(post.getDate());
        tvDate.setText(strDate);


        String popularity = Integer.toString(post.getPopularity());
        if (post.getPopularity() > 0) {
            tvPopularity.setTextColor(Color.GREEN);
        } else if (post.getPopularity() < 0) {
            tvPopularity.setTextColor(Color.RED);
        }
        tvPopularity.setText(popularity);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReadPostActivity.class);
                intent.putExtra("post", post);
                context.startActivity(intent);
            }
        });


        return  convertView;
    }

}
