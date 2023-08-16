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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class leetcodeFragment extends Fragment {
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
        RequestQueue rQ;
        rQ = Volley.newRequestQueue((this.getActivity()));

        String url = "https://kontests.net/api/v1/leet_code";
        JsonArrayRequest jArrRq = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject resObj = response.getJSONObject(i);
                        Information information = new Information();
                        information.setName(resObj.getString("name"));
                        information.setDuration(resObj.getString("duration"));
                        information.setLink(resObj.getString("url"));
                        String temp = resObj.getString("start_time");
                        temp = temp.substring(0,temp.length()-5);
                        String s = temp.substring(0, 10) + " " + temp.substring(11, temp.length());
                        information.setStart_time(s);
                        temp = resObj.getString("end_time");
                        temp = temp.substring(0,temp.length()-5);
                        s = temp.substring(0, 10) + " " + temp.substring(11, temp.length());
                        information.setEnd_time(s);
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
                Log.d("tag","Error");
            }
        });
        rQ.add(jArrRq);
        return inf;
    }
}