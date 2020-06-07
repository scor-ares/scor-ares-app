package gr.upatras.ceid.scor_ares;

import android.app.Activity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

public class TeamData  {
    /* Η κλάση αυτή είναι βοηθητική κλάση που αναλαμβάνει την αναζήτηση δεδομένων απο το αρχείο teams.json
     * που βρίσκεται στον φάκελο assets και περιέχει τα στοιχεία κάθε ομάδας που μας ενδιαφέρουν να εμφανίζονται
     * στην οθόνη. Κάθε μέθοδος της παρούσας κλάσης επιστρέφει τα συγκεκριμένα στοιχεία που μας ενδιαφέρουν.
     */
    Activity context; //Εδώ αποθηκεύεται το context του Fragment που δημιουργεί instance της συγκεκριμένης κλάσης. Αρχικοποιείται στον constructor.

    public TeamData(Activity context){
        this.context = context;
    }

    public String getTeamStringOfType(String teamID, String valType) {
        //Η μέθοδος επιστρέφει το όνομα της ομάδας που αντιστοιχεί στο teamID που δόθηκε.
        String json;
        String val = ""; //Εδώ αποθηκέυεται το όνομα της ομάδας που θα επιστρέφει η παρούσα μέθοδος.
        JSONObject obj = new JSONObject();
        try{
            //Άνοιγμα του αρχείου teams.json, μετατροπή του περιεχομένου του σε input stream
            InputStream is = context.getAssets().open("teams.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            //Μετατροπή του input stream σε String και έπειτα σε JSONArray
            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            //Αναζήτηση κάθε JSONObject που βρίσκεται στο jsonArray
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("id").equals(teamID)) { //Εάν το id της ομάδας αντιστοιχεί σε αυτό που δόθηκε
                    obj = jsonArray.getJSONObject(i);
                    break;
                }
            }
            val = obj.getString(valType);

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return val;
    }

    public JSONArray getTeamRoster(String teamID) {
        //Η μέθοδος επιστρέφει JSONArray της ομάδας στην οποία αντιστοιχεί το teamID που δόθηκε
        String json;
        JSONArray roster = new JSONArray(); //Εδώ αποθηκέυεται το JSONArray που πρόκεται να επιστρέψει η μέθοδος
        JSONObject obj = new JSONObject();
        try{
            //Άνοιγμα του αρχείου teams.json, μετατροπή του περιεχομένου του σε input stream
            InputStream is = context.getAssets().open("teams.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            //Μετατροπή του input stream σε String και έπειτα σε JSONArray
            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            //Αναζήτηση κάθε JSONObject που βρίσκεται στο jsonArray
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("id").equals(teamID)) { //Εάν το id της ομάδας αντιστοιχεί σε αυτό που δόθηκε
                    obj = jsonArray.getJSONObject(i);
                    break;
                }
            }
            roster = obj.getJSONArray("roster");

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return roster;
    }
}
