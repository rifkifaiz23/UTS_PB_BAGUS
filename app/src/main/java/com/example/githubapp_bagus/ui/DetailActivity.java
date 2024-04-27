package com.example.githubapp_bagus.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.githubapp_bagus.data.response.GithubUser;
import com.example.githubapp_bagus.data.retrofit.ApiConfig;
import com.example.githubapp_bagus.data.retrofit.ApiService;
import com.example.githubapp_bagus.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends  AppCompatActivity{
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);
        progressBar = findViewById(R.id.progressBar);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String username = extras.getString("username");
            ApiService apiService = ApiConfig.getApiService();
            Call<GithubUser> userCall = apiService.getUser(username);

            TextView textView = findViewById(R.id.nama);
            TextView textView2 = findViewById(R.id.username);
            TextView textView3 = findViewById(R.id.bio);
            ImageView imageView = findViewById(R.id.gambar);
            showLoading(true);
            userCall.enqueue(new Callback<GithubUser>() {
                @Override
                public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                    if (response.isSuccessful()){
                        showLoading(false);
                        GithubUser users = response.body();
                        if (users != null){
                            String name = "NAME         : " + users.getName();
                            String usernames = "USER NAME : " + users.getUsername();
                            String bio = "BIO           : " + users.getBio();
                            String gambar = users.getAvatarUrl();
                            textView.setText(name);
                            textView2.setText(usernames);
                            textView3.setText(bio);
                            Picasso.get().load(gambar).into(imageView);
                        }else {
                            Toast.makeText(DetailActivity.this, "Failed to get user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<GithubUser> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
