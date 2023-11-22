package cd.hena.app_artisan;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cd.hena.app_artisan.HomeActivity;
import cd.hena.app_artisan.PersistenceSqlLite;
import cd.hena.app_artisan.SharedPreference;

public class MainActivity extends AppCompatActivity {

    private TextView creerBtn;
    private EditText emailsEditText;
    private EditText passwordsEditText;

    private Button connectsBtn;

    private SharedPreference sharedPreference;
    private PersistenceSqlLite persistenceSqlLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        creerBtn = findViewById(R.id.creerBtn);
        connectsBtn = findViewById(R.id.connectsBtn);
        emailsEditText = findViewById(R.id.emailsEditText);
        passwordsEditText = findViewById(R.id.passwordsEditText);
        persistenceSqlLite = new PersistenceSqlLite(this);
        sharedPreference = new SharedPreference(this);
        ClicButtonCreer();
        ClicButtonConnexion();
    }

    private void ClicButtonCreer() {
        creerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreerCompteActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ClicButtonConnexion() {
        connectsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailsEditText.getText().toString();
                String password = passwordsEditText.getText().toString();
                // Enregistrement du jeton d'authentification
                String token = "votre_token";
                sharedPreference.saveToken(token);
                boolean connexion = persistenceSqlLite.verifierConnexion(email, password);
                if (connexion) {
                    Toast.makeText(MainActivity.this, "Vous êtes connecté", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isUserLoggedIn", true);
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}