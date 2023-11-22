package cd.hena.app_artisan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {
    private TextView emailssTextView;
    ProgressDialog progressDialog;
    private Button aproposBtn;

    Button decBtn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        init();
    }

    private void init() {
        emailssTextView = findViewById(R.id.emailssTextView);
        decBtn = findViewById(R.id.decBtn);
        aproposBtn = findViewById(R.id.aproposBtn);
        sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
        recuperer();
        deconnexion();
        apropos();
    }

    private void apropos(){
        aproposBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AproposActivity.class);
                startActivity(intent);
            }
        });
    }

    private void recuperer() {
        if (isLoggedIn()) {
            String userEmail = getUserEmail();
            emailssTextView.setText(userEmail);
        } else {
            emailssTextView.setText("Utilisateur non connecté");
        }
    }

    private boolean isLoggedIn() {
        // Vérifier si la clé "isUserLoggedIn" est définie à true dans les SharedPreferences
        return sharedPreferences.getBoolean("isUserLoggedIn", false);
    }

    private String getUserEmail() {
        // Récupérer l'email de l'utilisateur à partir des SharedPreferences
        return sharedPreferences.getString("userEmail", "");
    }

    private void deconnexion() {

        decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(ProfilActivity.this, "", "Deconnexion en cours...", true);

                // L'utilisateur choisit de se déconnecter

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isUserLoggedIn", false);
                editor.apply();

                // Redirigez l'utilisateur vers la page de connexion
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}