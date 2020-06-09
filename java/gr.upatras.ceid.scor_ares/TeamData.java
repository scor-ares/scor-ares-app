package gr.upatras.ceid.scor_ares;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class TeamData extends Data{
    /* Η κλάση αυτή είναι βοηθητική κλάση που αναλαμβάνει την αναζήτηση δεδομένων απο το αρχείο teams.json
     * που βρίσκεται στον φάκελο assets και περιέχει τα στοιχεία κάθε ομάδας που μας ενδιαφέρουν να εμφανίζονται
     * στην οθόνη. Κάθε μέθοδος της παρούσας κλάσης επιστρέφει τα συγκεκριμένα στοιχεία που μας ενδιαφέρουν.
     */
    public TeamData(Activity context, String filename){
        super(context, filename); //To constructor θέτει την μεταβλητη json που υπάρχει στην υπερκλάση Data
    }

    public String getTeamStringOfType(String teamID, String valType) {
        //Η μέθοδος επιστρέφει το όνομα της ομάδας που αντιστοιχεί στο teamID που δόθηκε.
        String val = ""; //Εδώ αποθηκέυεται το όνομα της ομάδας που θα επιστρέφει η παρούσα μέθοδος.
        JSONObject obj = new JSONObject();

            //Αναζήτηση κάθε JSONObject που βρίσκεται στο jsonArray
        try{
            for (int i = 0; i < json.length(); i++) {
                if (json.getJSONObject(i).getString("id").equals(teamID)) { //Εάν το id της ομάδας αντιστοιχεί σε αυτό που δόθηκε
                    obj = json.getJSONObject(i);
                    break;
                }
            }
            val = obj.getString(valType);

        } catch (JSONException e){
            e.printStackTrace();
        }

        return val;
    }

    public JSONArray getTeamRoster(String teamID) {
        //Η μέθοδος επιστρέφει JSONArray της ομάδας στην οποία αντιστοιχεί το teamID που δόθηκε
        JSONArray roster = new JSONArray(); //Εδώ αποθηκέυεται το JSONArray που πρόκεται να επιστρέψει η μέθοδος
        JSONObject obj = new JSONObject();
        try{
            //Αναζήτηση κάθε JSONObject που βρίσκεται στο jsonArray
            for (int i = 0; i < json.length(); i++) {
                if (json.getJSONObject(i).getString("id").equals(teamID)) { //Εάν το id της ομάδας αντιστοιχεί σε αυτό που δόθηκε
                    obj = json.getJSONObject(i);
                    break;
                }
            }
            roster = obj.getJSONArray("roster");

        } catch (JSONException e){
            e.printStackTrace();
        }

        return roster;
    }

    public ArrayList<Team> getTeamArrayOfSport(String sportName){
        ArrayList<Team> teamChoices = new ArrayList<>(
                Collections.singletonList(new Team())
        );
        String name;
        String id;
        String color;
        JSONArray roster;

        try{
            //Αναζήτηση κάθε JSONObject που βρίσκεται στο jsonArray
            for (int i = 0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);

                if (obj.getString("sport").equals(sportName)) {
                    name = obj.getString("name");
                    id = obj.getString("id");
                    color = obj.getString("color");
                    roster = obj.getJSONArray("roster");
                    teamChoices.add(new Team(id, name, color, roster));
                }
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

        return teamChoices;
    }
}
