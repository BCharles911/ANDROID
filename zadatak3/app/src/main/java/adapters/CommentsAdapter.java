package adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.user.aplikacija.Gadgets;
import com.example.user.aplikacija.MainActivity;
import com.example.user.aplikacija.R;

import Utils.CommentService;
import Utils.PostService;
import Utils.RetrofitObject;
import model.Comment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsAdapter extends ArrayAdapter<Comment> {

    private Comment comment;
    private Context context;
    private ImageButton btnLike;
    private boolean btnLikePressed;
    private ImageButton btnDislike;
    private boolean btnDislikePressed;
    private TextView commentPopularity;
    //private int userId;
    private SharedPreferences sharedPreferences;

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Comment comment = getItem(position);





        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R
                    .layout.content_detail_comment, parent, false);
        }




        // Lookup view for data population

//        this.userId = sharedPreferences.getInt("userId", 0);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvDescription = convertView.findViewById(R.id.tvDescription);
        TextView tvDate = convertView.findViewById(R.id.tvPostDate);
        commentPopularity = convertView.findViewById(R.id.tvPopularity);
        // Populate the data into the template view using the data object
         tvTitle.setText(comment.getTitle());
         tvDescription.setText(comment.getDescription());


        btnLike = convertView.findViewById(R.id.buttonLike);
        btnDislike = convertView.findViewById(R.id.buttonDislike);
        commentPopularity = (TextView) convertView.findViewById(R.id.tvPopularity);



        commentPopularity.setTextColor((comment.getPopularity() >= 0) ? Color.GREEN : Color.RED);
        commentPopularity.setText(Integer.toString(comment.getPopularity()));




        btnLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int userId = getContext().getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("userId", 1);
                if(userId == comment.getAuthor().getId()){
                    Gadgets.showSimpleOkDialog(getContext(), "Ne mozete lajkovati svoj komentar!!!");
                    return;
                }

                if(btnLike.getBackground().getConstantState() == context.getResources().getDrawable(R.drawable.like_pressed).getConstantState()){
                    removeLike();
                    return;
                }

                if(btnLike.getBackground().getConstantState() == getContext().getResources().getDrawable(R.drawable.like).getConstantState()){
                    CommentService commentService = RetrofitObject.retrofit.create(CommentService.class);
                    Call<ResponseBody> call = commentService.updateLikes(comment.getId());
                    // ProgressDialog
                    //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            response.body().close();

                            comment.like();
                            commentPopularity.setText(Integer.toString(comment.getPopularity()));
                            // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                            commentPopularity.setTextColor((comment.getPopularity() >= 0) ? Color.GREEN : Color.RED);
                            btnLike.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.like_pressed));

                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int userId = getContext().getSharedPreferences("sp", Context.MODE_PRIVATE).getInt("userId", 1);
                if(userId == comment.getAuthor().getId()){
                    Gadgets.showSimpleOkDialog(getContext(), "Ne mozete dislajkovati svoj komentar!!!");
                    return;
                }
                if(btnDislike.getBackground().getConstantState() == context.getResources().getDrawable(R.drawable.dislike_pressed).getConstantState()){
                    removeDislike();
                    return;
                }

                if(btnDislike.getBackground().getConstantState() == getContext().getResources().getDrawable(R.drawable.dislike).getConstantState()){
                    CommentService commentService = RetrofitObject.retrofit.create(CommentService.class);
                    Call<ResponseBody> call = commentService.updateDislikes(comment.getId());
                    // ProgressDialog
                    //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            response.body().close();

                            comment.dislike();
                            commentPopularity.setText(Integer.toString(comment.getPopularity()));
                            // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                            commentPopularity.setTextColor((comment.getPopularity() >= 0) ? Color.GREEN : Color.RED);
                            btnDislike.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.dislike_pressed));
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        return convertView;
    }





    private void removeLike() {


        if (!btnDislikePressed) {
            CommentService commentService = RetrofitObject.retrofit.create(CommentService.class);
            Call<ResponseBody> call = commentService.updateLikes(comment.getId());
            // ProgressDialog
            //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    response.body().close();

                    comment.dislike();
                    commentPopularity.setText(Integer.toString(comment.getPopularity()));
                    // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                    commentPopularity.setTextColor((comment.getPopularity() >= 0) ? Color.GREEN : Color.RED);

                    btnLikePressed = false;
                    btnLike.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.like));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    private void removeDislike() {
        if (!btnLikePressed) {
            CommentService commentService = RetrofitObject.retrofit.create(CommentService.class);
            Call<ResponseBody> call = commentService.updateDislikes(comment.getId());
            // ProgressDialog
            //final ProgressDialog progressDialog = Gadgets.getProgressDialog(getContext());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    response.body().close();

                    comment.like();
                    commentPopularity.setText(Integer.toString(comment.getPopularity()));
                    // Ako je popularnost veca ili jednaka nuli bojimo u zeleno, ako ne crvenimo ga
                    commentPopularity.setTextColor((comment.getPopularity() >= 0) ? Color.GREEN : Color.RED);

                    btnDislikePressed = false;
                    btnDislike.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.dislike));
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }
            });


        }


    }


}
