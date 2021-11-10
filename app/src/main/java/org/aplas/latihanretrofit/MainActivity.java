package org.aplas.latihanretrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import org.aplas.latihanretrofit.databinding.ActivityMainBinding;
import org.aplas.latihanretrofit.models.Repo;
import org.aplas.latihanretrofit.services.GitHubService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private GitHubService service;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(GitHubService.class);
        binding.setUsername("RizFad");
    }

    public void handleSend(View view){
        Call<List<Repo>> repositoriesCall = service.listRepos(binding.getUsername());


        repositoriesCall.enqueue(new Callback<List<Repo>>(){
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response){
                List<Repo> results = response.body();
                if (results != null && response.isSuccessful()){
                binding.setRepo(results.get(0));
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t){
                Log.e(MainActivity.class.getCanonicalName(), t.getMessage());
            }
        });

    }

}