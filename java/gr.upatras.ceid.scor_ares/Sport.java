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

public class Sport extends Fragment implements AdapterView.OnItemSelectedListener{
    /* Η παρούσα κλάση είναι αυτή που αναλαμβάνει να εμφανίζει την αρχική οθόνη του κάθε αθλήματος,
     * καθώς και να παρέχει λειτουργείες πλοήγησης του χρήστη σε επόμενες οθόνες
     */
    private String headerText; //Ο τίτλος της σελίδας, αλλάζει ανάλογα με το ποιό άθλημα επέλεξε ο χρήστης
    private LayoutInflater layoutInflater;
    private View view;

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.dummy_choices, android.R.layout.simple_spinner_item); //Οι επιλογές που θα εμφανίζονται στο drop down menu του spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Αρχικοποίηση του πλαίσιου που δείχνει τους επερχόμενους αγώνες στον χρήστη
        LinearLayout pastMatches = view.findViewById(R.id.pastMatches);

        //TODO Η λίστα με τους αγώνες πρέπει να μεταβάλλεται δυναμικά ανάλογα με τα στοιχεία που υπάρχουν διαθέσιμα σε αντίστοιχο JSON
        //Παρακάτω δημιουργείται μια ενδειγματική λίστα απο String που πρόκεται να περιέχουν στοιχεία για επερχόμενους και περασμένους αγώνες.
        for (int k = 0; k < 20; k++){
            TextView teamName = new TextView(this.getActivity());
            teamName.setId(k);

            final String teamNameString = "Match #" + (k+1);
            teamName.setText(teamNameString);
            teamName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Team team = new Team(teamNameString);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container_fragment, team);
                    fragmentTransaction.commit();
                }
            });
            pastMatches.addView(teamName);
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
            String text = parent.getItemAtPosition(position).toString(); //Ανάκτηση του κειμένου που βρίσκεται στην επιλογή που επιλέχθηκε

            Team team = new Team(text);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, team);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
