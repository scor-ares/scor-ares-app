package gr.upatras.ceid.scor_ares;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class Team extends Fragment{
    private String teamID;
    private String teamName; //Το όνομα της ομάδας, αλλάζει ανάλογα με το ποιά ομάδα επέλεξε ο χρήστης
    private LayoutInflater layoutInflater;
    private View view; // Η view που επιστρέφει η μέθοδος onCreateView αρχικοποιείται εδώ.

    public Team(String ID){
        /*TODO το teamName καλύτερα να αντικατασταθεί απο το int teamID το οποίο θα λειτουργεί ως
         * κλειδί για την εύρεση του JSONObject που περιέχει όλα τα στοιχεία ομάδας απο την κλάση TeamData
         */
        teamID = ID;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.team_layout, container, false);

        TextView text = (TextView) view.findViewById(R.id.team_name); //Το παρόν TextView είναι ο τίτλος της ομάδας που προβάλλεται

        TeamData teamData = new TeamData(getActivity()); //Δημιουργία αντικειμένου της κλάσης TeamData με παράμετρο το context του παρόντος Fragment

        teamID = "2"; //TODO διαγραφή αυτής της γραμμής
        teamName = teamData.getTeamName(teamID);

        text.setText(teamName);
        return view;
    }

}
