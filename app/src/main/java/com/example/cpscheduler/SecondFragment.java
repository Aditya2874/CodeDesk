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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONException;
import org.json.JSONObject;

public class SecondFragment extends Fragment {

    private TextView a;
    private TextView b;
    private TextView c;
    private TextView d;
    private TextView e;
    private String username;
    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_second, container, false);
        SharedPreferences pref = getActivity().getSharedPreferences("leet_name",MODE_PRIVATE);
        username = pref.getString("lkey","adityadeokar123");
        a = inf.findViewById(R.id.ranking);
        b = inf.findViewById(R.id.total);
        c = inf.findViewById(R.id.easy);
        d = inf.findViewById(R.id.medium);
        e = inf.findViewById(R.id.hard);
        RequestQueue queue = Volley.newRequestQueue(inf.getContext());
        String url = "https://leetcode-stats-api.herokuapp.com/"+username;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String rank = String.valueOf(response.getInt("ranking"));
                            a.setText(rank);
                            String rate= String.valueOf(response.getInt("totalSolved"));
                            b.setText(rate);
                            String es= String.valueOf(response.getInt("easySolved"));
                            c.setText(es);
                            String ms= String.valueOf(response.getInt("mediumSolved"));
                            d.setText(ms);
                            String hs= String.valueOf(response.getInt("hardSolved"));
                            e.setText(hs);
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