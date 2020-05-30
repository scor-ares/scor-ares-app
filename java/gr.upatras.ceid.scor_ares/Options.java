package gr.upatras.ceid.scor_ares;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class Options extends Fragment //implements View.OnClickListener
{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        Button accountSettings = (Button) View.findViewById(R.id.accountSettings);
        accountSettings.setOnClickListener(this);

         */
        return inflater.inflate(R.layout.options_layout, container, false);
    }

    /*
    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        if (view.getId() == R.id.accountSettings){
            fragment = new AccountSettings();
            replaceFragment(fragment);
        }
    }

    public void replaceFragment(Fragment fr) {
        FragmentTransaction transaction = FragmentActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fragment,fr);
        transaction.addToBackStack(null);
        transaction.commit();
    }

     */
}