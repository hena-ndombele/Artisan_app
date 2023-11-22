package cd.hena.app_artisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    FloatingActionButton fab;

    BottomNavigationView bottomNavigationView;
    TextView clientCtn;
    TextView prestationCtn;
    private PersistenceSqlLite persistenceSqlLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        boolean isUserLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false);

        if (!isUserLoggedIn) {
            // L'utilisateur n'est pas connecté, redirigez-le vers la page de connexion
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish(); // Fermer l'activité actuelle pour empêcher l'utilisateur de revenir en arrière
        } else {
            persistenceSqlLite = new PersistenceSqlLite(this);
            List<PersistenceSqlLite.Client> clients = persistenceSqlLite.getAllClients();
            List<PersistenceSqlLite.Prestation> prestations = persistenceSqlLite.getAllPrestation();
            int userCount = clients.size();
            int prestationCount = prestations.size();
            clientCtn = findViewById(R.id.clientCtn);
            prestationCtn = findViewById(R.id.prestationCtn);
            clientCtn.setText(String.valueOf(userCount));
            prestationCtn.setText(String.valueOf(prestationCount));
            init();
        }
    }

    private void init() {
        fab = findViewById(R.id.fab);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                int itemId=item.getItemId();
                if (itemId == R.id.profil) {
                    Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        ClicFloatingButton();

    }

    private void ClicFloatingButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AjoutActivity.class);
                startActivity(intent);
            }
        });
    }


    }
