package com.igorgrigorewski.randomuserme.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.igorgrigorewski.randomuserme.R;
import com.igorgrigorewski.randomuserme.api.RetroClient;
import com.igorgrigorewski.randomuserme.content.RandomUserMe;
import com.igorgrigorewski.randomuserme.content.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by igorg on 6/29/2017.
 */

public class users_list extends Fragment {
    private ListView users_listView;
    private FloatingActionButton fab;

    private List<Result> currentResults;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_list, container, false);

        users_listView = (ListView) view.findViewById(R.id.users_list_view);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        users_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();

                String firstName = currentResults.get(position).getName().getFirst();
                String lastName = currentResults.get(position).getName().getLast();
                String fullName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1) +
                        " " + lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

                bundle.putString("fullname", fullName);
                bundle.putString("phone", currentResults.get(position).getPhone());
                bundle.putString("email", currentResults.get(position).getEmail());
                bundle.putString("address", currentResults.get(position).getLocation().getCity());
                bundle.putString("birthday", currentResults.get(position).getRegistered());
                bundle.putString("photo", currentResults.get(position).getPicture().getLarge());

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main_container, User.getInstance(bundle))
                        .addToBackStack(null)
                        .commit();

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
        if (currentResults != null)
            updateData();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentResults = null;
        getData();
    }

    private void getData(){
        RetroClient.getApiService().getRandomUsers().enqueue(new Callback<RandomUserMe>() {
            @Override
            public void onResponse(Call<RandomUserMe> call, Response<RandomUserMe> response) {
                if (response.isSuccessful()) {
                    currentResults = response.body().getResults();
                    updateData();
                }
            }

            @Override
            public void onFailure(Call<RandomUserMe> call, Throwable t) {
                Log.d("ERROR", "api response isn't successful");
            }
        });
    }
    private void updateData() {
        String[] from = {
                "fullname"
        };

        int[] to = {
                R.id.users_list_item_textView
        };

        ArrayList<HashMap<String, Object>> dataMaps = new ArrayList<>();


        for (Result result :
                currentResults) {
            String firstName = result.getName().getFirst();
            String lastName = result.getName().getLast();
            String fullName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1) +
                    " " + lastName.substring(0, 1).toUpperCase() + lastName.substring(1);

            HashMap<String, Object> map = new HashMap<>();
            map.put("fullname", fullName);
            dataMaps.add(map);
        }

        SimpleAdapter adapter =
                new SimpleAdapter(getActivity().getApplicationContext(), dataMaps, R.layout.users_list_item, from, to);
        users_listView.setAdapter(adapter);
    }

}
