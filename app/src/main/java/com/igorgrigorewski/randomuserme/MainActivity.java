package com.igorgrigorewski.randomuserme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.igorgrigorewski.randomuserme.api.RetroClient;
import com.igorgrigorewski.randomuserme.content.RandomUserMe;
import com.igorgrigorewski.randomuserme.content.Result;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ListView users_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        users_listView = (ListView) findViewById(R.id.users_list_view);

        updateData();
    }

    private void updateData(){
        RetroClient.getApiService().getRandomUsers().enqueue(new Callback<RandomUserMe>() {
            @Override
            public void onResponse(Call<RandomUserMe> call, Response<RandomUserMe> response) {
                if (response.isSuccessful()){
                    RandomUserMe randomUserMe = response.body();

                    String[] from = {
                            "title"
                    };

                    int[] to = {
                            R.id.users_list_item_textView
                    };

                    final ArrayList<HashMap<String, Object>> dataMaps = new ArrayList<>();
                    for (Result result :
                            randomUserMe.getResults()){
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("title", result.getName().getFirst() + " " + result.getName().getLast());
                        dataMaps.add(map);
                    }

                    SimpleAdapter adapter =
                            new SimpleAdapter( getApplicationContext(), dataMaps, R.layout.users_list_item, from, to);

                    users_listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<RandomUserMe> call, Throwable t) {
                Log.d("ERROR", "api response isn't successful");
            }
        });
    }
}
