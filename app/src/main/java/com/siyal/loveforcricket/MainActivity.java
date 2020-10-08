package com.siyal.loveforcricket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import adapter.MatchAdapter;
import model.Match;
import utils.MyUrls;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Match> matchList;
    private MatchAdapter matchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Matches List");

        recyclerView = (RecyclerView) findViewById(R.id.match_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        matchList = new ArrayList<>();

        loadUrlData();
    }

    private void loadUrlData() {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyUrls.matchesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{
                    JSONArray matchesArray = new JSONObject(response).getJSONArray("matches");
                    for(int i=0; i<matchesArray.length(); i++){
                        try{
                            String uniqueId = matchesArray.getJSONObject(i).getString("unique_id");
                            String team1 = matchesArray.getJSONObject(i).getString("team-1");
                            String team2 = matchesArray.getJSONObject(i).getString("team-2");
                            String matchType = matchesArray.getJSONObject(i).getString("type");

                            String matchStatus = "";
                            boolean matchStarted = matchesArray.getJSONObject(i).getBoolean("matchStarted");
                            if(matchStarted){
                                matchStatus = "Match Started";
                            }
                            else{
                                matchStatus = "Match Not Started";
                            }

                            String dateTimeGMT = matchesArray.getJSONObject(i).getString("dateTimeGMT");
                            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            format1.setTimeZone(TimeZone.getTimeZone(dateTimeGMT));
                            Date date = format1.parse(dateTimeGMT);

                            //convert to dd/MM/yyyy HH:mm
                            SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            format2.setTimeZone(TimeZone.getTimeZone("GMT"));
                            String dateTime = format2.format(date);

                            //set data
                            Match match = new Match(uniqueId, team1, team2, matchType, matchStatus, dateTime);
                            //add to list
                            matchList.add(match);
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    //adapter to be set in recycler view
                    matchAdapter = new MatchAdapter(MainActivity.this, matchList);
                    recyclerView.setAdapter(matchAdapter);
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: "+error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        //enqueue the request
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}