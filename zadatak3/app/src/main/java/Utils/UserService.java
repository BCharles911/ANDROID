package Utils;

import com.google.gson.JsonObject;

import java.util.List;

import model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @GET(value = "/api/users/")
    Call<List<User>> getUsers (
            // implementirat TODO
    );

    @GET("/users/{userId}/")
    Call<User> getOne (
            @Path("userId") int userId
    );

    /**
     * A method used for logging in user. It searches for username and password matching, returns user if it exists, null if not
     * @return User
     */
    @FormUrlEncoded
    @POST("/users/login/")
    Call<User> checkLogin(
            @Field("username") String username,
            @Field("password") String password
    );

}






