package adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.siyal.loveforcricket.MatchDetailActivity;
import com.siyal.loveforcricket.MatchSummaryActivity;
import com.siyal.loveforcricket.PlayersActivity;
import com.siyal.loveforcricket.R;

import java.util.List;

import model.Match;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private Context context;
    private List<Match> matchList;

    public MatchAdapter(Context context, List<Match> matchList){
        this.context = context;
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_row, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        final Match match = matchList.get(position);
        holder.txtTeam1.setText(match.getTeam1());
        holder.txtTeam2.setText(match.getTeam2());
        holder.txtMatchType.setText(match.getMatchType());
        holder.txtMatchStatus.setText(match.getMatchStatus());
        holder.txtDate.setText(match.getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] options = new CharSequence[]{"Match Detail", "Players List", "Match Summary"};

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                builder.setTitle("Choose Option");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            Intent intent = new Intent(context, MatchDetailActivity.class);
                            intent.putExtra("matchId", match.getId());
                            intent.putExtra("date", match.getDate());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else if(which==1){
                            Intent intent = new Intent(context, PlayersActivity.class);
                            intent.putExtra("matchId", match.getId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else if(which==2){
                            Intent intent = new Intent(context, MatchSummaryActivity.class);
                            intent.putExtra("matchId", match.getId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }


    public class MatchViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTeam1, txtTeam2, txtMatchType, txtMatchStatus, txtDate;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTeam1 = (TextView) itemView.findViewById(R.id.show_team_one_text);
            txtTeam2 = (TextView) itemView.findViewById(R.id.show_team_two_text);
            txtMatchType = (TextView) itemView.findViewById(R.id.show_match_type_text);
            txtMatchStatus = (TextView) itemView.findViewById(R.id.show_match_status_text);
            txtDate = (TextView) itemView.findViewById(R.id.show_date_text);
        }
    }
}
