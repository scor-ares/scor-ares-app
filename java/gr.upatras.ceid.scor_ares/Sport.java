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

import java.util.ArrayList;

public class Sport extends Fragment implements AdapterView.OnItemSelectedListener {
    /* Η παρούσα κλάση είναι αυτή που αναλαμβάνει να εμφανίζει την αρχική οθόνη του κάθε αθλήματος,
     * καθώς και να παρέχει λειτουργείες πλοήγησης του χρήστη σε επόμενες οθόνες
     */
    private String sportName; //Ο τίτλος της σελίδας, αλλάζει ανάλογα με το ποιό άθλημα επέλεξε ο χρήστης
    private LayoutInflater layoutInflater;
    private View view;
    private ArrayList<Team> teams; // Η λίστα με τις ομάδες που θα εμφανίζεται στον spinner
    private TeamData teamData; // Το αντικείμενο που αναλαμβάνει την ανάκτηση δεδομένων απο το teams.json
    private MatchData matchData;// Το αντικείμενο που αναλαμβάνει την ανάκτηση δεδομένων απο το matches.json

    public Sport(String sportName) {
        this.sportName = sportName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.sport_layout, container, false);

        teamData = new TeamData(getActivity(), "teams.json");
        matchData = new MatchData(getActivity(), "matches.json");

        TextView text = (TextView) view.findViewById(R.id.header_text);
        text.setText(sportName);

        //Αρχικοποίηση του spinner που περιέχει τις επιλογές ομάδας
        Spinner spinner = view.findViewById(R.id.teamSpinner);

        teams = teamData.getTeamArrayOfSport(sportName); // Δημιουργία της λίστας των ομάδων που αντιστοιχούν στο άθλημα που επέλεξε ο χρήστης

        // Προσθήκη ArrayAdapter που περιέχει τις ομάδες της λίστας teams στο spinner
        ArrayAdapter adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, teams);//Οι επιλογές που θα εμφανίζονται στο drop down menu του spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Αρχικοποίηση του πλαίσιου που δείχνει τους επερχόμενους αγώνες στον χρήστη
        LinearLayout upcomingMatches = view.findViewById(R.id.upcomingMatches);
        //Αρχικοποίηση του πλαίσιου που δείχνει τους περασμένους αγώνες στον χρήστη
        LinearLayout completedMatches = view.findViewById(R.id.pastMatches);

        // Δημιουργια και εμφάνιση της λίστας των επερχόμενων αγώνων
        ArrayList<Match> upcomingMatchesList = matchData.getMatchArrayOfSport(sportName, "upcoming");

        for (final Match match : upcomingMatchesList) {
            TextView matchTitle = new TextView(this.getActivity());
            matchTitle.setText(match.toString());

            matchTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragment, match);
                    fragmentTransaction.commit();
                }
            });
            upcomingMatches.addView(matchTitle, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        // Δημιουργια και εμφάνιση της λίστας των περασμένων αγώνων
        ArrayList<Match> completedMatchesList = matchData.getMatchArrayOfSport(sportName, "completed");

        for (final Match match : completedMatchesList) {
            TextView matchTitle = new TextView(this.getActivity());
            matchTitle.setText(match.toString());

            matchTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragment, match);
                    fragmentTransaction.commit();
                }
            });
            completedMatches.addView(matchTitle, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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

}
