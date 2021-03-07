package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import model.Login;
import model.User;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import service.UserClient;

public class MainActivity extends AppCompatActivity {

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5000/api/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    UserClient userClient = retrofit.create(UserClient.class);


    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    private static  String token;


    public void login(View view) {
        Login login = new Login(
                email.getText().toString(),
                password.getText().toString()
        );

        if (email.length() != 0 || password.length() != 0) {
            Call<User> call = userClient.login(login);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MainActivity.this,"Success :"+response.body().getEmail(),Toast.LENGTH_SHORT).show();
                        token = response.body().getToken();
                        Log.d("token",token);

                    }else{
                        Toast.makeText(MainActivity.this,"Bad login",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(MainActivity.this,"error34",Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(MainActivity.this, "empty email/pass", Toast.LENGTH_SHORT).show();
        }

    }

    public void getPostss(){
        Call<ResponseBody> call = userClient.getSecret(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Toast.makeText(MainActivity.this,"Success :"+response.body().string(),Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Bad token",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPosts(View view) {
        getPostss();
    }

//    private void sendNetworkRequest(User user) {
//        //create Okhttp client
//       OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
//
//       HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//       logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//       //Remove on final
//       okhttpClientBuilder.addInterceptor(logging);
//
//       //create Retrofit instance
//       Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:5000/api/user/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okhttpClientBuilder.build());
//        Retrofit retrofit= builder.build();
//
//        //get client & call object for the request
//        UserClient client = retrofit.create(UserClient.class);
//        Call<User> call = client.login(user);
//
//        //execute network request
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//
//                Toast.makeText(MainActivity.this,"Logged in.:",Toast.LENGTH_LONG).show();
//                //Log.d("lolg: ",usrname);
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(MainActivity.this,"error:" +t,Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//    }
}
