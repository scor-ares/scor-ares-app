package gr.upatras.ceid.scor_ares;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    NavigationView menu;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* ανάθεση των αντικειμένων που αντιστοιχούν στα layout στοιχεία drawer_layout, top_bar
         και nav_view. Βρίσκονται στο αρχείο res/layout/activity_main.xml
         */
        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.top_bar);
        setSupportActionBar(toolbar);
        menu = findViewById(R.id.nav_view);
        menu.setNavigationItemSelectedListener(this);

        /* Αρχικοποίηση των fragmentManager και fragmentTransaction, φορτωση του default fragment
        που θα βλέπει ο χρήστης με το άνοιγμα της εφαρμογής. Η αρχικοποίηση γίνεται με την βοήθεια
        της μεθόδου switchToNewFragment της τρέχουσας κλάσης
         */
        switchToNewFragment(new Sport(getResources().getString(R.string.opt_football)), true);

        getSupportActionBar().setDisplayShowTitleEnabled(false); //απόκρυψη του τίτλου της εφαρμογής απο το toolbar

        /* Ο listener που αναλαμβάνει το άνοιγμα και το κλέισιμο του drawer menu
         */
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        /* Υπερφόρτωση της μεθόδου onBackPressed() που κληρονομείται απο την κλάση
        AppCompactActivity. Σε αυτήν την έκδοση της μεθόδου γίνεται έλεγχος της κατάστασης
        του drawer menu. Εάν είναι ανοιχτό τότε το πλήκτρο back κλείνει το drawer αντί να
        μεταφέρει την εφαρμογή στο προηγούμενο activity.
         */
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        /*Η μέθοδος που αναλαμβάνει τα click events του drawer menu, φορτωνει το fragment που
        αντιστοιχεί στην κάθε επιλογή του μενού
         */
        drawer.closeDrawer(GravityCompat.START); // Κάθε φορά που ο χρήστης επιλέγει κάτι θέλουμε το drawer menu να κλείνει

        switch(item.getItemId()) {
            case R.id.menu_choose_football:
                switchToNewFragment(new Sport(getResources().getString(R.string.opt_football)));
                break;
            case R.id.menu_choose_basketball:
                switchToNewFragment(new Sport(getResources().getString(R.string.opt_basketball)));
                break;
            case R.id.menu_choose_volleyball:
                switchToNewFragment(new Sport(getResources().getString(R.string.opt_volleyball)));
                break;
            case R.id.menu_choose_tennis:
                switchToNewFragment(new Sport(getResources().getString(R.string.opt_tennis)));
                break;
            case R.id.menu_favourites:
                //TODO
                break;
            case R.id.menu_nearby_events:
                //TODO
                break;
            case R.id.menu_settings:
                switchToNewFragment(new Options()); //TODO
                break;
            default:
                break;
        }
        return true;
    }

    public void switchToNewFragment(Fragment fragment, boolean add){
        /*Βοηθητική μέθοδος που αναλαμβάνει την εμφάνιση των fragment που επιλέγει ο χρήστης να δεί
        απο το drawer menu
         */
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        if (add){
            fragmentTransaction.add(R.id.container_fragment, fragment);
        }
        else {
            fragmentTransaction.replace(R.id.container_fragment, fragment);
        }

        fragmentTransaction.commit();
    }
    
    public void switchToNewFragment(Fragment fragment){
        /* Overload της μεθόδου switchToNewFragment στην περίπτωση που η παράμετρος add δεν δίνεται,
        ετσι ώστε η default λειτουργία να είναι η FragmentTransaction.replace() καθώς χρησιμοποιείται
        πιο συχνά
         */
        switchToNewFragment(fragment, false);
    }

}
