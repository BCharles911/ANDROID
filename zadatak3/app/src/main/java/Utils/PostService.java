package Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import model.Post;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostService {


    @GET(value = "api/posts/")
    Call<List<Post>> getPosts(


    );

    @GET("api/posts/{postId}/")
    Call<Post> getOne (

            @Path("postId") int postId
    );


    @POST("api/posts")
    @Headers("Content-type: application/json")
    Call<Post> createPost (
            @Body Post post
    );


    @PATCH("api/posts/{postId}/like")
    Call<ResponseBody> updateLikes(
            @Path("postId") int postId
    );

    @PATCH("api/posts/{postId}/dislike")
    Call<ResponseBody> updateDislikes(
            @Path("postId") int postId
    );

    @DELETE("api/posts/{postId}/")
    Call<ResponseBody> deletePost(@Path("postId") int postId);

}
