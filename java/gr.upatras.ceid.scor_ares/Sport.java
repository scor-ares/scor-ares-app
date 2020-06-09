package gr.upatras.ceid.scor_ares;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class Sport extends Fragment implements AdapterView.OnItemSelectedListener{
    /* Η παρούσα κλάση είναι αυτή που αναλαμβάνει να εμφανίζει την αρχική οθόνη του κάθε αθλήματος,
     * καθώς και να παρέχει λειτουργείες πλοήγησης του χρήστη σε επόμενες οθόνες
     */
    private String headerText; //Ο τίτλος της σελίδας, αλλάζει ανάλογα με το ποιό άθλημα επέλεξε ο χρήστης
    private LayoutInflater layoutInflater;
    private View view;
    private ArrayList<String> team_choices;

    public Sport(String sportName){
        headerText = sportName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.sport_layout, container, false);

        TextView text = (TextView) view.findViewById(R.id.header_text);
        text.setText(headerText);

        //Αρχικοποίηση του spinner που περιέχει τις επιλογές ομάδας
        Spinner spinner = view.findViewById(R.id.teamSpinner);

        ArrayList<Team> teams = getTeamChoices(headerText);
        ArrayAdapter adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, teams);//Οι επιλογές που θα εμφανίζονται στο drop down menu του spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Αρχικοποίηση του πλαίσιου που δείχνει τους επερχόμενους αγώνες στον χρήστη
        LinearLayout upcomingMatches = view.findViewById(R.id.upcomingMatches);
        //Αρχικοποίηση του πλαίσιου που δείχνει τους περασμένους αγώνες στον χρήστη
        LinearLayout pastMatches = view.findViewById(R.id.pastMatches);

        //Παρακάτω δημιουργείται δυο ενδειγματική λίστα απο String που πρόκεται να περιέχουν στοιχεία για επερχόμενους και περασμένους αγώνες.
        //TODO οι λίστες πρέπει να δημιουργούνται δυναμικά με την βοήθεια αρχείου json
        //Επερχόμενοι Αγώνες
        for (int k = 0; k < 20; k++){
            TextView matchTitle = new TextView(this.getActivity());
            matchTitle.setId(k);

            final String teamNameString = "Upcoming Match #" + (k+1);
            matchTitle.setText(teamNameString);
            matchTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Match match = new Match();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragment, match);
                    fragmentTransaction.commit();
                }
            });
            upcomingMatches.addView(matchTitle);
        }

        // Περασμένοι Αγώνες
        for (int k = 0; k < 30; k++){
            TextView matchTitle = new TextView(this.getActivity());
            matchTitle.setId(k);

            final String teamNameString = "Past Match #" + (k+1);
            matchTitle.setText(teamNameString);
            matchTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Match match = new Match();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragment, match);
                    fragmentTransaction.commit();
                }
            });
            pastMatches.addView(matchTitle);
        }

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /* Υπερφόρτωση μεθόδου του Spinner που αναλαμβάνει την λογική για την περίπτωση που ο χρήστης επιλέγει την ομάδα που θέλει να δεί στο drop down menu.
         * Στην συγκεκριμένη περίπτωση για κάθε επιλογή δημιουργεί και εμφανίζει καινούργιο Fragment τύπου Team με όνομα το κείμενο της επιλογής.
         * Εξαίρεση αποτελέι η πρώτη επιλογη που βρίσκεται στο position = 0, η οποία λειτουργεί ως ένας τύπος prompt για τον χρήστη να επιλέξει ομάδα.
         */
        if (position != 0) {
            Team team = (Team) parent.getSelectedItem(); //Ανάκτηση του αντικειμένου Team που βρίσκεται στην θέση που επιλέχθηκε

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, team);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private ArrayList<Team> getTeamChoices(String sportName){
        //TODO προσθήκη λογικής για την εμφάνιση των ομάδων που ανήκουν στο παρόν άθλημα μονο
        ArrayList<Team> teamChoices = new ArrayList<>(
                Collections.singletonList(new Team())
        );
        String json;
        String name;
        String id;

        try{
            //Άνοιγμα του αρχείου teams.json, μετατροπή του περιεχομένου του σε input stream
            InputStream is = getActivity().getAssets().open("teams.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            //Μετατροπή του input stream σε String και έπειτα σε JSONArray
            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            //Αναζήτηση κάθε JSONObject που βρίσκεται στο jsonArray
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                if (obj.getString("sport").equals(sportName)) {
                    name = obj.getString("name");
                    id = obj.getString("id");
                    teamChoices.add(new Team(id, name));
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e){
            e.printStackTrace();
        }

        return teamChoices;
    }
}
