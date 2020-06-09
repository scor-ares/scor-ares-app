package gr.upatras.ceid.scor_ares;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MatchData extends Data {

    public MatchData(Activity context, String filename) {
        super(context, filename);
    }

    public ArrayList<Match> getMatchArrayOfSport(String sportName, String type) {
        ArrayList<Match> matchArray = new ArrayList<>();
        JSONArray matches = new JSONArray();
        String[] contestants;
        TeamData teamData = new TeamData(context, "teams.json");

        try {

            if (type.equals("upcoming")) {
                matches = json.getJSONObject(0).getJSONArray("matches");
            } else if (type.equals("completed")) {
                matches = json.getJSONObject(1).getJSONArray("matches");
            }

            //Αναζήτηση κάθε JSONObject που βρίσκεται στο jsonArray
            for (int i = 0; i < matches.length(); i++) {
                JSONObject obj = matches.getJSONObject(i);


                if (obj.getString("sport").equals(sportName)) {
                    String ID_1 = obj.getJSONArray("contestants").getString(0);
                    String ID_2 = obj.getJSONArray("contestants").getString(1);

                    matchArray.add(new Match(
                            teamData.getTeamStringOfType(ID_1,"name"),
                            teamData.getTeamStringOfType(ID_2, "name")
                    ));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return matchArray;
    }
}
