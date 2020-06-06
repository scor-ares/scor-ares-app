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
    Activity context; //Εδώ αποθηκεύεται το context του Fragment που δημιουργεί instance της συγκεκριμένης κλάσης.

    public TeamData(Activity context){
        this.context = context;
    }

    public String getTeamName() {
        //TODO Η ομάδα που εμφανίζει αυτή η μέθοδος αυτήν την στιγμή είναι hardcoded. Να εμφανίζεται ανάλογα με παράμετρο συνάρτησης.
        String json;
        String name = "";
        try{
            InputStream is = context.getAssets().open("teams.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            JSONObject obj = jsonArray.getJSONObject(1);
            name = obj.getString("name");

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return name;
    }
}
