package gr.upatras.ceid.scor_ares;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Match extends Fragment {
    private LayoutInflater layoutInflater;
    private View view;

    private String[] contestants = new String[2];
    private String[] score = new String[2];
    private String tagLine;

    public Match(){
        this.tagLine = "Match";
    }

    public Match(String contestant1, String contestant2){
        this.contestants = new String[] {
                contestant1,
                contestant2
        };
        this.tagLine = contestants[0] + " vs. " + contestants[1];
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutInflater = getActivity().getLayoutInflater();
        view = layoutInflater.inflate(R.layout.match_layout, container, false);

        TeamData teamData = new TeamData(this.getActivity(), "teams.json");

        return view;
    }

    @Override
    public String toString() {
        return this.tagLine;
    }
}
