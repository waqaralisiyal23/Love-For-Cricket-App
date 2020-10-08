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

public class MatchSummaryActivity extends AppCompatActivity {

    private String matchId;

    private TextView txtFieldTeam1Title, txtFieldTeam2Title, txtTeam1FieldDetail, txtTeam2FieldDetail;
    private TextView txtBowlTeam1Title, txtBowlTeam2Title, txtTeam1BowlDetail, txtTeam2BowlDetail;
    private TextView txtBatTeam1Title, txtBatTeam2Title, txtTeam1BatDetail, txtTeam2BatDetail;
    private TextView txtOtherResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_summary);

        matchId = getIntent().getStringExtra("matchId");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Match Summary");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        txtFieldTeam1Title = (TextView) findViewById(R.id.field_team_1_title);
        txtTeam1FieldDetail = (TextView) findViewById(R.id.field_team_1_detail);
        txtFieldTeam2Title = (TextView) findViewById(R.id.field_team_2_title);
        txtTeam2FieldDetail = (TextView) findViewById(R.id.field_team_2_detail);

        txtBowlTeam1Title = (TextView) findViewById(R.id.bowl_team_1_title);
        txtTeam1BowlDetail = (TextView) findViewById(R.id.bowl_team_1_detail);
        txtBowlTeam2Title = (TextView) findViewById(R.id.bowl_team_2_title);
        txtTeam2BowlDetail = (TextView) findViewById(R.id.bowl_team_2_detail);

        txtBatTeam1Title = (TextView) findViewById(R.id.bat_team_1_title);
        txtTeam1BatDetail = (TextView) findViewById(R.id.bat_team_1_detail);
        txtBatTeam2Title = (TextView) findViewById(R.id.bat_team_2_title);
        txtTeam2BatDetail = (TextView) findViewById(R.id.bat_team_2_detail);

        txtOtherResults = (TextView) findViewById(R.id.other_results_detail);

        loadUrlData();
    }

    private void loadUrlData() {
        final ProgressDialog progressDialog = new ProgressDialog(MatchSummaryActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //id just for testing = 1144158
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyUrls.matchSummaryUrl + matchId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject dataObject = jsonObject.getJSONObject("data");

                    JSONArray fieldingArray = dataObject.getJSONArray("fielding");
                    JSONArray bowlingArray = dataObject.getJSONArray("bowling");
                    JSONArray battingArray = dataObject.getJSONArray("batting");

                    JSONObject field0 = fieldingArray.getJSONObject(0);
                    JSONObject field1 = fieldingArray.getJSONObject(1);

                    String field1Title = field0.getString("title");
                    String field2Title = field1.getString("title");
                    txtFieldTeam1Title.setText(field1Title);
                    txtFieldTeam2Title.setText(field2Title);

                    JSONArray field1ScoresArray = field0.getJSONArray("scores");
                    JSONArray field2ScoresArray = field1.getJSONArray("scores");

                    //getting/setting fielding summary for team 1
                    for(int i=0; i<field1ScoresArray.length(); i++){
                        String name = field1ScoresArray.getJSONObject(i).getString("name");
                        String bowled = field1ScoresArray.getJSONObject(i).getString("bowled");
                        String catchh = field1ScoresArray.getJSONObject(i).getString("catch");
                        String lbw = field1ScoresArray.getJSONObject(i).getString("lbw");
                        String runout = field1ScoresArray.getJSONObject(i).getString("runout");
                        String stumped = field1ScoresArray.getJSONObject(i).getString("stumped");

                        txtTeam1FieldDetail.append(
                                "Name: "+name
                                +"\nBowled: "+bowled
                                +"\nCatch: "+catchh
                                +"\nLBW: "+lbw
                                +"\nRun Out: "+runout
                                +"\nStumped: "+stumped+"\n\n"
                        );
                    }

                    //getting/setting fielding summary for team 2
                    for(int i=0; i<field2ScoresArray.length(); i++){
                        String name = field2ScoresArray.getJSONObject(i).getString("name");
                        String bowled = field2ScoresArray.getJSONObject(i).getString("bowled");
                        String catchh = field2ScoresArray.getJSONObject(i).getString("catch");
                        String lbw = field2ScoresArray.getJSONObject(i).getString("lbw");
                        String runout = field2ScoresArray.getJSONObject(i).getString("runout");
                        String stumped = field2ScoresArray.getJSONObject(i).getString("stumped");

                        txtTeam2FieldDetail.append(
                                "Name: "+name
                                        +"\nBowled: "+bowled
                                        +"\nCatch: "+catchh
                                        +"\nLBW: "+lbw
                                        +"\nRun Out: "+runout
                                        +"\nStumped: "+stumped+"\n\n"
                        );
                    }

                    JSONObject bowl0 = bowlingArray.getJSONObject(0);
                    JSONObject bowl1 = bowlingArray.getJSONObject(1);

                    String bowl1Title = bowl0.getString("title");
                    String bowl2Title = bowl1.getString("title");
                    txtBowlTeam1Title.setText(bowl1Title);
                    txtBowlTeam2Title.setText(bowl2Title);

                    JSONArray bowl1ScoresArray = bowl0.getJSONArray("scores");
                    JSONArray bowl2ScoresArray = bowl1.getJSONArray("scores");

                    //getting/setting bowling summary for team 1
                    for(int i=0; i<bowl1ScoresArray.length(); i++){
                        String bowlerName = bowl1ScoresArray.getJSONObject(i).getString("bowler");
                        String overs = bowl1ScoresArray.getJSONObject(i).getString("O");
                        String maiden = bowl1ScoresArray.getJSONObject(i).getString("M");
                        String wickets = bowl1ScoresArray.getJSONObject(i).getString("W");
                        String runs = bowl1ScoresArray.getJSONObject(i).getString("R");
                        String zeroes = bowl1ScoresArray.getJSONObject(i).getString("0s");
                        String fours = bowl1ScoresArray.getJSONObject(i).getString("4s");
                        String sixes = bowl1ScoresArray.getJSONObject(i).getString("6s");
                        String econ = bowl1ScoresArray.getJSONObject(i).getString("Econ");

                        txtTeam1BowlDetail.append(
                                "Name: "+bowlerName
                                        +"\nOvers: "+overs
                                        +"\nMaiden: "+maiden
                                        +"\nWickets: "+wickets
                                        +"\nRuns: "+runs
                                        +"\n0s: "+zeroes
                                        +"\n4s: "+fours
                                        +"\n6s: "+sixes
                                        +"\n0s: "+zeroes
                                        +"\nEcon: "+econ+"\n\n"
                        );
                    }

                    //getting/setting bowling summary for team 2
                    for(int i=0; i<bowl2ScoresArray.length(); i++){
                        String bowlerName = bowl2ScoresArray.getJSONObject(i).getString("bowler");
                        String overs = bowl2ScoresArray.getJSONObject(i).getString("O");
                        String maiden = bowl2ScoresArray.getJSONObject(i).getString("M");
                        String wickets = bowl2ScoresArray.getJSONObject(i).getString("W");
                        String runs = bowl2ScoresArray.getJSONObject(i).getString("R");
                        String zeroes = bowl2ScoresArray.getJSONObject(i).getString("0s");
                        String fours = bowl2ScoresArray.getJSONObject(i).getString("4s");
                        String sixes = bowl2ScoresArray.getJSONObject(i).getString("6s");
                        String econ = bowl2ScoresArray.getJSONObject(i).getString("Econ");

                        txtTeam2BowlDetail.append(
                                "Name: "+bowlerName
                                        +"\nOvers: "+overs
                                        +"\nMaiden: "+maiden
                                        +"\nWickets: "+wickets
                                        +"\nRuns: "+runs
                                        +"\n0s: "+zeroes
                                        +"\n4s: "+fours
                                        +"\n6s: "+sixes
                                        +"\n0s: "+zeroes
                                        +"\nEcon: "+econ+"\n\n"
                        );
                    }

                    JSONObject bat0 = battingArray.getJSONObject(0);
                    JSONObject bat1 = battingArray.getJSONObject(1);

                    String bat1Title = bat0.getString("title");
                    String bat2Title = bat1.getString("title");
                    txtBatTeam1Title.setText(bat1Title);
                    txtBatTeam2Title.setText(bat2Title);

                    JSONArray bat1ScoresArray = bat0.getJSONArray("scores");
                    JSONArray bat2ScoresArray = bat1.getJSONArray("scores");

                    //getting/setting batting summary for team 1
                    for(int i=0; i<bat1ScoresArray.length(); i++){
                        String batsman = bat1ScoresArray.getJSONObject(i).getString("batsman");
                        String balls = bat1ScoresArray.getJSONObject(i).getString("B");
                        String runs = bat1ScoresArray.getJSONObject(i).getString("R");
                        String fours = bat1ScoresArray.getJSONObject(i).getString("4s");
                        String sixes = bat1ScoresArray.getJSONObject(i).getString("6s");
                        String strikeRate = bat1ScoresArray.getJSONObject(i).getString("SR");
                        String dismissalInfo = bat1ScoresArray.getJSONObject(i).getString("dismissal-info");
                        String dismissal = "", dismissedBy = "";
                        try{
                            dismissal = bat1ScoresArray.getJSONObject(i).getString("dismissal");
                            dismissedBy = bat1ScoresArray.getJSONObject(i).getJSONObject("dismissal-by").getString("name");
                        }
                        catch (Exception e){
                        }

                        txtTeam1BatDetail.append(
                                "Batsman: "+batsman
                                        +"\nBalls: "+balls
                                        +"\nRuns: "+runs
                                        +"\n4s: "+fours
                                        +"\n6s: "+sixes
                                        +"\nSR: "+strikeRate
                                        +"\nDismissal: "+dismissal
                                        +"\nDismissal Info: "+dismissalInfo
                                        +"\nDismissed By: "+dismissedBy+"\n\n"
                        );
                    }

                    //getting/setting batting summary for team 2
                    for(int i=0; i<bat2ScoresArray.length(); i++){
                        String batsman = bat2ScoresArray.getJSONObject(i).getString("batsman");
                        String balls = bat2ScoresArray.getJSONObject(i).getString("B");
                        String runs = bat2ScoresArray.getJSONObject(i).getString("R");
                        String fours = bat2ScoresArray.getJSONObject(i).getString("4s");
                        String sixes = bat2ScoresArray.getJSONObject(i).getString("6s");
                        String strikeRate = bat2ScoresArray.getJSONObject(i).getString("SR");
                        String dismissalInfo = bat2ScoresArray.getJSONObject(i).getString("dismissal-info");
                        String dismissal = "", dismissedBy = "";
                        try{
                            dismissal = bat2ScoresArray.getJSONObject(i).getString("dismissal");
                            dismissedBy = bat2ScoresArray.getJSONObject(i).getJSONObject("dismissal-by").getString("name");
                        }
                        catch (Exception e){
                        }

                        txtTeam2BatDetail.append(
                                "Batsman: "+batsman
                                        +"\nBalls: "+balls
                                        +"\nRuns: "+runs
                                        +"\n4s: "+fours
                                        +"\n6s: "+sixes
                                        +"\nSR: "+strikeRate
                                        +"\nDismissal: "+dismissal
                                        +"\nDismissal Info: "+dismissalInfo
                                        +"\nDismissed By: "+dismissedBy+"\n\n"
                        );
                    }

                    //Old matches have these values
                    //Ongoing matches may or may not have these values
                    //Upcoming matches have no values
                    String manOfTheMatch = "", tossWinnerTeam = "", winnerTeam = "";

                    try{
                        manOfTheMatch = dataObject.getJSONObject("man-of-the-match").getString("name");
                    }
                    catch (Exception e){
                    }

                    try{
                        tossWinnerTeam = dataObject.getString("toss_winner_team");
                    }
                    catch (Exception e){
                    }

                    try{
                        winnerTeam = dataObject.getString("winner_team");
                    }
                    catch (Exception e){
                    }

                    txtOtherResults.setText("Toss Winner: "+tossWinnerTeam+"\nWinner: "+winnerTeam+"\nMan of the Match: "+manOfTheMatch);
                }
                catch (Exception e){
                    Toast.makeText(MatchSummaryActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MatchSummaryActivity.this, "Error: "+error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        //enqueue the request
        RequestQueue requestQueue = Volley.newRequestQueue(MatchSummaryActivity.this);
        requestQueue.add(stringRequest);
    }
}