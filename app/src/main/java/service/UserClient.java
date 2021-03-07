package service;
import model.Login;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @POST("user/login")
    Call<User> login(@Body Login login);

    @GET("posts")
    Call<ResponseBody> getSecret(@Header("auth-token") String authToken);

}
