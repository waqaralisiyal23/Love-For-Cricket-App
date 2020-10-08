package com.siyal.loveforcricket;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import utils.MyUrls;

public class MatchDetailActivity extends AppCompatActivity {

    private String matchId, date;

    private TextView txtTeam1, txtTeam2, txtMatchStatus, txtScore, txtDescription, txtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        matchId = getIntent().getStringExtra("matchId");
        date = getIntent().getStringExtra("date");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Match Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        txtTeam1 = (TextView) findViewById(R.id.match_detail_show_team_one_text);
        txtTeam2 = (TextView) findViewById(R.id.match_detail_show_team_two_text);
        txtMatchStatus = (TextView) findViewById(R.id.match_detail_show_match_status_text);
        txtScore = (TextView) findViewById(R.id.match_detail_show_score_text);
        txtDescription = (TextView) findViewById(R.id.match_detail_show_description_text);
        txtDate = (TextView) findViewById(R.id.match_detail_show_date_text);

        txtDate.setText(date);

        loadUrlData();
    }

    private void loadUrlData() {
        final ProgressDialog progressDialog = new ProgressDialog(MatchDetailActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyUrls.matchDetailUrl + matchId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    //These values are received whether match is started or not
                    String team1 = jsonObject.getString("team-1");
                    String team2 = jsonObject.getString("team-2");

                    String matchStatus = "";
                    boolean matchStarted = jsonObject.getBoolean("matchStarted");
                    if(matchStarted){
                        matchStatus = "Match Started";
                    }
                    else{
                        matchStatus = "Match Not Started";
                    }
                    txtTeam1.setText(team1);
                    txtTeam2.setText(team2);
                    txtMatchStatus.setText(matchStatus);

                    try{
                        //These values are received only if match is started
                        //so we are enclosing it in separate try catch
                        String score = jsonObject.getString("score");
                        String description = jsonObject.getString("description");
                        txtScore.setText(score);
                        txtDescription.setText(description);
                    }
                    catch (Exception e){
                    }
                }
                catch (Exception e){
                    Toast.makeText(MatchDetailActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MatchDetailActivity.this, "Error: "+error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        //enqueue the request
        RequestQueue requestQueue = Volley.newRequestQueue(MatchDetailActivity.this);
        requestQueue.add(stringRequest);
    }
}