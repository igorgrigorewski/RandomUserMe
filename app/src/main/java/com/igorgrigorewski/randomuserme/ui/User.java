package com.igorgrigorewski.randomuserme.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.igorgrigorewski.randomuserme.R;
import com.igorgrigorewski.randomuserme.content.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class User extends Fragment {
    private TextView fullname;
    private TextView phone;
    private TextView email;
    private TextView address;
    private TextView birthday;
    private ImageView photo;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_info, container, false);

        fullname = (TextView) view.findViewById(R.id.user_info_fullname);
//        phone = (TextView) view.findViewById(R.id.user_info_phone);
//        email = (TextView) view.findViewById(R.id.user_info_email);
//        address = (TextView) view.findViewById(R.id.user_info_address);
//        birthday = (TextView) view.findViewById(R.id.user_info_birthday);
        photo = (ImageView) view.findViewById(R.id.user_info_image);
        listView = (ListView) view.findViewById(R.id.user_info_list);
        return view;
    }

    public static User getInstance(Bundle args) {
        User fragment = new User();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        fullname.setText(getArguments()
                .getString("fullname"));
//        phone.setText(getArguments()
//                .getString("phone"));
//        email.setText(getArguments()
//                .getString("email"));
//        address.setText(getArguments()
//                .getString("address"));
//        birthday.setText(getArguments()
//                .getString("birthday"));
        Picasso.with(getActivity()).load(getArguments().getString("photo")).into(photo);

        String[] from = {
                "icon",
                "text"
        };

        int[] to = {
                R.id.user_info_list_item_icon,
                R.id.user_info_list_item_text
        };

        ArrayList<HashMap<String, Object>> dataMaps = new ArrayList<>();

        HashMap<String, Object> map = new HashMap<>();
        map.put("icon", R.drawable.ic_phone_black_24dp);
        map.put("text", getArguments().getString("phone"));
        dataMaps.add(map);

        map = new HashMap<>();
        map.put("icon", R.drawable.ic_email_black_24dp);
        map.put("text", getArguments().getString("email"));
        dataMaps.add(map);

        map = new HashMap<>();
        map.put("text", getArguments().getString("address"));
        dataMaps.add(map);

        map = new HashMap<>();
        map.put("text", getArguments().getString("birthday"));
        dataMaps.add(map);

        SimpleAdapter adapter =
                new SimpleAdapter(getActivity().getApplicationContext(), dataMaps, R.layout.user_info_list_item, from, to);
        listView.setAdapter(adapter);
    }
}
