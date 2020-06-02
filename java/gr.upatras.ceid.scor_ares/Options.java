package gr.upatras.ceid.scor_ares;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Options extends Fragment
{
    Button forum_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_layout, container, false);

        forum_button = view.findViewById(R.id.forum_button);
        forum_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Forum forum = new Forum();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, forum);
                fragmentTransaction.commit();
            }
        });


        return view;
    }
}