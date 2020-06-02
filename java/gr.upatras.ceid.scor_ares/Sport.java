package gr.upatras.ceid.scor_ares;

import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Sport extends Fragment implements AdapterView.OnItemSelectedListener{
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.dummy_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        //Αρχικοποίηση του πλαίσιου που δείχνει τους επερχόμενους αγώνες στον χρήστη
        LinearLayout pastMatches = view.findViewById(R.id.pastMatches);

        //TODO Η λίστα με τους αγώνες πρέπει να μεταβάλλεται δυναμικά ανάλογα με τα στοιχεία που υπάρχουν διαθέσιμα σε αντίστοιχο XML
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
        if (position != 0) {
            String text = parent.getItemAtPosition(position).toString();

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
