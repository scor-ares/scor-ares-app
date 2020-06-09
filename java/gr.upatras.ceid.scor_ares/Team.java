package gr.upatras.ceid.scor_ares;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Team extends Fragment{
    private String teamID;
    private String teamName; //Το όνομα της ομάδας, αλλάζει ανάλογα με το ποιά ομάδα επέλεξε ο χρήστης
    private LayoutInflater layoutInflater;
    private View view; // Η view που επιστρέφει η μέθοδος onCreateView αρχικοποιείται εδώ.
    private TeamData teamData;

    public Team(){
        teamID = "0";
        teamName = "Επιλογή Ομάδας";
    }

    public Team(String ID, String name){
        teamID = ID;
        teamName = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.team_layout, container, false);

        TextView text = (TextView) view.findViewById(R.id.team_name); //Το παρόν TextView είναι ο τίτλος της ομάδας που προβάλλεται
        ImageView logo = (ImageView) view.findViewById(R.id.teamLogo);
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.playerRosterTable);
        JSONArray roster;

        teamData = new TeamData(getActivity());

        if(!teamID.equals("0")) {
            setName(teamData);//Δημιουργία αντικειμένου της κλάσης TeamData με παράμετρο το context του παρόντος Fragment
            logo.setColorFilter(Color.parseColor(getColor(teamData)));

            roster = teamData.getTeamRoster(teamID);

            for(int i = 0; i < roster.length(); i++){
                try {
                    JSONObject player = roster.getJSONObject(i);
                    TableRow tableRow = new TableRow(this.getActivity());
                    TextView playerName = new TextView(this.getActivity());
                    TextView playerPos = new TextView(this.getActivity());

                    playerName.setText(player.getString("name"));
                    playerPos.setText(player.getString("position"));

                    playerName.setGravity(Gravity.CENTER);
                    playerPos.setGravity(Gravity.CENTER);

                    tableRow.addView(playerName);
                    tableRow.addView(playerPos);
                    tableLayout.addView(tableRow);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        text.setText(teamName);
        return view;
    }

    @Override
    public String toString() {
        //Η μέθοδος toString() υπερφορτώνεται διότι καλείται απο τον ArrayAdapter στην κλάση Sport για να ξέρει ποιό κείμενο να εμφανίσει.
        //Εμείς θέλουμε να επιστρέφει το όνομα της κάθε ομάδας.
        return teamName;
    }

    private void setName(TeamData teamData){
        this.teamName = teamData.getTeamStringOfType(this.teamID, "name");
    }

    private String getColor(TeamData teamData){
        return teamData.getTeamStringOfType(this.teamID, "color");
    }

}
