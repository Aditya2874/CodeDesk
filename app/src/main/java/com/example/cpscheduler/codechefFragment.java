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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class codechefFragment extends Fragment {
    private static final String API_URL = "https://competeapi.vercel.app/contests/codechef/";
    private ArrayList<Information> arrList = new ArrayList<>();
    public codechefFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_codechef, container, false);
        ProgressBar pbc = inf.findViewById(R.id.codeChef_PB);
        pbc.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = inf.findViewById(R.id.recycler_codechef);
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
                            JSONArray upcomingContests = response.getJSONArray("future_contests");
                            for (int i = 0; i < upcomingContests.length(); i++) {
                                JSONObject contest = upcomingContests.getJSONObject(i);
                                String contestCode = contest.getString("contest_code");
                                String contestName = contest.getString("contest_name");
                                String startDate = contest.getString("contest_start_date");
                                String endDate = contest.getString("contest_end_date");
                                String duration = contest.getString("contest_duration");
                                // Process the upcoming contest details as needed
                                Log.d("Upcoming Contest", "Code: " + contestCode + ", Name: " + contestName + ", Start Date: " + startDate + ", End Date: " + endDate);

                                Information information = new Information();
                                information.setName(contestName);
                                information.setDuration(Long.parseLong(duration));
                                information.setLink("https://www.codechef.com/contests");
                                information.setStart_time(startDate);
                                information.setEnd_time(endDate);
                                arrList.add(information);
                                pbc.setVisibility(View.GONE);
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