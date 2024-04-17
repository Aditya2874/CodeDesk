package com.example.cpscheduler;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class leetcodeFragment extends Fragment {
    private static final String API_URL = "https://competeapi.vercel.app/contests/leetcode/";
    private ArrayList<Information> arrList = new ArrayList<>();
    public leetcodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {View inf = inflater.inflate(R.layout.fragment_leetcode, container, false);
        ProgressBar lpb = inf.findViewById(R.id.leet_PB);
        lpb.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = inf.findViewById(R.id.recyler_leetcode);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RecyclerAdapter adp = new RecyclerAdapter(arrList,this.getContext());
        RequestQueue queue = Volley.newRequestQueue(this.getContext());

        // Request a JSON object response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the JSON response
                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONArray topTwoContests = data.getJSONArray("topTwoContests");
                            for (int i = 0; i < topTwoContests.length(); i++) {
                                JSONObject contest = topTwoContests.getJSONObject(i);
                                String title = contest.getString("title");
                                long startTime = contest.getLong("startTime");
                                long duration = contest.getLong("duration");
                                // Process the contest details as needed
                                Log.d("Contest", "Title: " + title + ", Start Time: " + startTime + ", Duration: " + duration);

                                Information information = new Information();
                                information.setName(title);
                                information.setDuration(duration);
                                information.setLink("https://leetcode.com/contest/");
                                information.setStart_time(Long.toString(startTime));
                                information.setEnd_time(Long.toString(startTime+duration));
                                arrList.add(information);
                                lpb.setVisibility(View.GONE);
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
        queue.add(jsonObjectRequest);
        return inf;
    }
}