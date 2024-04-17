package com.example.cpscheduler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class codeforcesFragment extends Fragment {
    private static final String API_URL = "https://competeapi.vercel.app/contests/codeforces/";
    ArrayList<Information> arrList = new ArrayList<>();
    public codeforcesFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_codeforces, container, false);
        ProgressBar fpb = inf.findViewById(R.id.codeforces_PB);
        fpb.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = inf.findViewById(R.id.recycler_codeforces);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RecyclerAdapter adp = new RecyclerAdapter(arrList,this.getContext());
        RequestQueue queue = Volley.newRequestQueue(this.getContext());

        // Request a JSON array response from the provided URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, API_URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the JSON response
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject contest = response.getJSONObject(i);
                                String site = contest.getString("site");
                                String title = contest.getString("title");
                                long startTime = contest.getLong("startTime");
                                long duration = contest.getLong("duration");
                                long endTime = contest.getLong("endTime");
                                String url = contest.getString("url");

                                // Log or process the contest details as needed
                                Log.d("Contest", "Site: " + site + ", Title: " + title + ", URL: " + url);
                                Information information = new Information();
                                information.setName(title);
                                information.setDuration(duration);
                                information.setLink(url);
                                information.setStart_time(Long.toString(startTime));
                                information.setEnd_time(Long.toString(endTime));
                                arrList.add(information);
                                fpb.setVisibility(View.GONE);
                                recyclerView.setAdapter(adp);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors
                        Log.e("API Error", "Error occurred: " + error.getMessage());
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
        return inf;
    }


}