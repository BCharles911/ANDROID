package adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.aplikacija.R;

import model.Post;
import tools.Mockup;

public class PostsAdapter extends BaseAdapter {
    private Activity activity;

    public PostsAdapter(Activity activity) { this.activity = activity;}

    @Override
    public int getCount() {
        return Mockup.getPosts().size();
    }

    @Override
    public Object getItem(int position) {
        return Mockup.getPosts().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        Post post = Mockup.getPosts().get(position);

        if(convertView==null)
            vi = activity.getLayoutInflater().inflate(R.layout.posts_list, null);

        TextView title = (TextView)vi.findViewById(R.id.title);
        TextView description = (TextView)vi.findViewById(R.id.description);
        ImageView image = (ImageView)vi.findViewById(R.id.item_icon);

        title.setText(post.getTitle());
        description.setText(post.getDescription());

        if (post.getAvatar() != -1){
            image.setImageResource(post.getAvatar());
        }

        return  vi;
    }

}
