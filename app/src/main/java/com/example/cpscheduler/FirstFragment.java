package com.example.cpscheduler;
import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executor;

public class FirstFragment extends Fragment {
    private String username;
    private TextView temp;
    private TextView rate;
    private TextView maxrate;
    public FirstFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_first, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("cf_name",MODE_PRIVATE);
        username = pref.getString("cfkey","aditya_030703");
        String url = "https://codeforces.com/api/user.info?handles=" + username;
        rate = inf.findViewById(R.id.rating);
        maxrate = inf.findViewById(R.id.maxrating);
        temp = inf.findViewById(R.id.rank);
        ImageView img = inf.findViewById(R.id.img);
        Log.d("API", "response2");
        RequestQueue queue = Volley.newRequestQueue(inf.getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject result = response.getJSONArray("result").getJSONObject(0);
                            String titlePhoto = result.getString("titlePhoto");
                            int rating = result.getInt("rating");
                            String r = String.valueOf(rating);
                            int mrateing = result.getInt("maxRating");
                            String mr = String.valueOf(mrateing);
                            Glide.with(getActivity()).load(titlePhoto).into(img);
                            String rank= result.getString("rank");
                            temp.setText(rank);
                            rate.setText(r);
                            maxrate.setText(mr);
                            Log.d("API", "Handle: " + username + ", Rating: " + rate);
                        } catch (JSONException e) {
                            Log.e("API", e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API", error.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
        return inf;
    }
}