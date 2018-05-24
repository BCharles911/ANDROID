package Utils;

import model.Comment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentService {

    @POST("api/comments")
    @Headers("Content-type: application/json")
    Call<Comment> createComment(
        @Body Comment comment

            );

    @PATCH("api/comments/{commentId}/like")
    Call<ResponseBody> likeComment(
            @Path("commentId") int CommentId
    );

    @PATCH("api/comments/{commentId}/dislike")
    Call<ResponseBody> dislikeComment(

            @Path("commentId") int commentId
    );

}
