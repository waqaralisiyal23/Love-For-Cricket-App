package com.siyal.loveforcricket;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.MyUrls;

public class PlayersActivity extends AppCompatActivity {

    private String matchId;

    private TextView txtTeamOneName, txtTeamTwoName, txtTeamOnePlayers, txtTeamTwoPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        matchId = getIntent().getStringExtra("matchId");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Players");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        txtTeamOneName = (TextView) findViewById(R.id.team_one_name);
        txtTeamTwoName = (TextView) findViewById(R.id.team_two_name);
        txtTeamOnePlayers = (TextView) findViewById(R.id.team_one_players);
        txtTeamTwoPlayers = (TextView) findViewById(R.id.team_two_players);

        loadUrlData();
    }

    private void loadUrlData() {
        final ProgressDialog progressDialog = new ProgressDialog(PlayersActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyUrls.playersListUrl + matchId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{
                    JSONArray squadArray = new JSONObject(response).getJSONArray("squad");
                    JSONObject team1PlayersObject = squadArray.getJSONObject(0);
                    JSONObject team2PlayersObject = squadArray.getJSONObject(1);

                    //Get names of team
                    String team1 = team1PlayersObject.getString("name");
                    String team2 = team2PlayersObject.getString("name");
                    txtTeamOneName.setText(team1);
                    txtTeamTwoName.setText(team2);

                    //Get players array for each team
                    JSONArray team1PlayersArray = team1PlayersObject.getJSONArray("players");
                    JSONArray team2PlayersArray = team2PlayersObject.getJSONArray("players");

                    //Get names of team one players
                    for(int i=0; i<team1PlayersArray.length(); i++){
                        String team1Players = team1PlayersArray.getJSONObject(i).getString("name");
                        txtTeamOnePlayers.append(team1Players+"\n");
                    }
                    //Get names of team two players
                    for(int i=0; i<team1PlayersArray.length(); i++){
                        String team2Players = team2PlayersArray.getJSONObject(i).getString("name");
                        txtTeamTwoPlayers.append(team2Players+"\n");
                    }

                }
                catch (Exception e){
                    Toast.makeText(PlayersActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PlayersActivity.this, "Error: "+error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        //enqueue the request
        RequestQueue requestQueue = Volley.newRequestQueue(PlayersActivity.this);
        requestQueue.add(stringRequest);
    }
}