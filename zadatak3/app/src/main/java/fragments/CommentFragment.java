package fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aplikacija.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.CommentsAdapter;
import model.Comment;
import model.Post;

public class CommentFragment extends Fragment {

    private List<Comment> comments;
    private Post post;
    private ListView listView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myInflatedView = inflater.inflate(R.layout.comments_list, container,false);
        post = (Post) getArguments().getSerializable("post");

        ArrayList<Comment> comments = post.getComments();
        sortCommentsByPreference(comments);
        CommentsAdapter adapter = new CommentsAdapter(getActivity(), comments);
        listView = myInflatedView.findViewById(R.id.listViewComment);
        listView.setAdapter(adapter);


        return myInflatedView;
    }










    public void sortCommentsByPreference(List<Comment> comments){
        // PO cemu da sortiramo ???  ?? ? ?
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortPostsBy = sp.getString("lpSortCommentsBy", "default123");

        // Sortiramo
        if(sortPostsBy.equals("Date")) {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment comment2, Comment comment1) {
                    return comment1.getDate().compareTo(comment2.getDate());
                }
            });
        } else if (sortPostsBy.equals("Popularity")) {
            Collections.sort(comments, new Comparator<Comment>() {
                @Override
                public int compare(Comment comment2, Comment comment1) {
                    if(comment1.getPopularity() > comment2.getPopularity()){
                        return 1;
                    } else if (comment1.getPopularity() < comment2.getPopularity()){
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else {
            Toast.makeText(getActivity(), "Sorting went wrong, posts unsorted!\n" + sortPostsBy, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        post = (Post) getArguments().getSerializable("post");

        List<Comment> comments = post.getComments();






       // sortCommentsByPreference(comments);

      //  CommentsAdapter adapter = new CommentsAdapter(getActivity(), comments);
        //listView.setAdapter(adapter);
    }
















//
//    public void sortCommentsByPreference(List<Comment> comments){
//        // PO cemu da sortiramo ???  ?? ? ?
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        String sortPostsBy = sp.getString("pref_sort_comment", "default123");
//
//        // Sortiramo
//        if(sortPostsBy.equals("Date")) {
//            Collections.sort(comments, new Comparator<Comment>() {
//                @Override
//                public int compare(Comment comment2, Comment comment1) {
//                    return comment1.getDate().compareTo(comment2.getDate());
//                }
//            });
//        } else if (sortPostsBy.equals("Popularity")) {
//            Collections.sort(comments, new Comparator<Comment>() {
//                @Override
//                public int compare(Comment comment2, Comment comment1) {
//                    if(comment1.getPopularity() > comment2.getPopularity()){
//                        return 1;
//                    } else if (comment1.getPopularity() < comment2.getPopularity()){
//                        return -1;
//                    } else {
//                        return 0;
//                    }
//                }
//            });
//        } else {
//            Toast.makeText(getActivity(), "Sorting went wrong, posts unsorted!\n" + sortPostsBy, Toast.LENGTH_LONG).show();
//        }
//    }




}
