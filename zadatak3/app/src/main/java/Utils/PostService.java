package Utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import model.Post;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface PostService {


    @GET("/api/posts")
    Call<JsonArray> doGetPosts();


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();





}
