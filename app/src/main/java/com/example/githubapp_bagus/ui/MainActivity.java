package com.example.githubapp_bagus.ui;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.githubapp_bagus.data.response.GithubResponse;
import com.example.githubapp_bagus.data.response.GithubUser;
import com.example.githubapp_bagus.data.retrofit.ApiConfig;
import com.example.githubapp_bagus.data.retrofit.ApiService;
import com.example.githubapp_bagus.R;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.githubapp_bagus.R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        ApiService apiService = ApiConfig.getApiService();
        Call<GithubResponse> call = apiService.searchUsers("bagus");
        call.enqueue(new Callback<GithubResponse>() {
            @Override
            public void onResponse(Call<GithubResponse> call, Response<GithubResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<GithubUser> users = response.body().getUsers();
                    adapter = new UserAdapter(users);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get users", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<GithubResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}