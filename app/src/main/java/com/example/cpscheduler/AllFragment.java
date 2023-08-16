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

public class AllFragment extends Fragment {
    private ArrayList<Information> arrList = new ArrayList<>();
    public AllFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inf = inflater.inflate(R.layout.fragment_all, container, false);
        ProgressBar pb = inf.findViewById(R.id.all_PB);
        pb.setVisibility(View.VISIBLE);
        RecyclerView recyclerView = inf.findViewById(R.id.recycler_all);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RecyclerAdapter adp = new RecyclerAdapter(arrList,this.getContext());
        RequestQueue rQ;
        rQ = Volley.newRequestQueue((this.getActivity()));

        String url = "https://kontests.net/api/v1/all";
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
                        information.setStart_time(resObj.getString("start_time"));
                        information.setEnd_time(resObj.getString("end_time"));
                        arrList.add(information);
                        pb.setVisibility(View.GONE);
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