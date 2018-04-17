package tools;

import com.example.user.aplikacija.R;

import java.util.ArrayList;
import java.util.List;

import model.Post;

public class Mockup {

    public static List<Post> getPosts(){
        ArrayList<Post> posts = new ArrayList<Post>();
        Post p1 = new Post("Post o filmovima1", "ovo je tekst o filmovima bla bla bla1", R.drawable.about);
        Post p2 = new Post("Post o filmovima2", "ovo je tekst o filmovima bla bla bla2", R.drawable.about);
        Post p3 = new Post("Post o filmovima3", "ovo je tekst o filmovima bla bla bla3", R.drawable.about);

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);

        return posts;
    }
}
