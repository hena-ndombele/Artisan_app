package cd.hena.app_artisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// Importations

// Importations

public class CreerCompteActivity extends AppCompatActivity {

    // Variables
    private EditText prenomEditText;
    private EditText nomEditText;
    private EditText emailEditText;
    private EditText telephoneEditText;
    private EditText passwordEditText;

    private Button creercomptesBtn;
    private TextView connexionBtn;

    PersistenceSqlLite persistenceSqlLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_compte);
        init();
    }

    private void init() {
        // Initialisations des vues

        connexionBtn = findViewById(R.id.connexionBtn);
        prenomEditText = findViewById(R.id.prenomEditText);
        nomEditText = findViewById(R.id.nomEditText);
        emailEditText = findViewById(R.id.emailEditText);
        telephoneEditText = findViewById(R.id.telephoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        creercomptesBtn = findViewById(R.id.creercomptesBtn);
        persistenceSqlLite = new PersistenceSqlLite(this);

        ClicButtonConnexion();
        ActionButtonCreer();
    }

    private void ClicButtonConnexion() {
        connexionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ActionButtonCreer() {
        creercomptesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    ClicButtonCreerCompte();
                } else {
                    Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateFields() {
        String prenom = prenomEditText.getText().toString();
        String nom = nomEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String telephone = telephoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || telephone.isEmpty() || password.isEmpty()) {
            return false; // L'un des champs est vide
        }

        return true; // Tous les champs sont remplis
    }

    private void ClicButtonCreerCompte() {
        String prenom = prenomEditText.getText().toString();
        String nom = nomEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String telephone = telephoneEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Créer un nouvel utilisateur
        PersistenceSqlLite.Utilisateur utilisateur = new PersistenceSqlLite.Utilisateur(prenom, nom, email,  password);

        // Insérer l'utilisateur dans la base de données
        long resultat = persistenceSqlLite.creerUtilisateur(utilisateur);
        if (resultat != -1) {
            // Succès de la création du compte
            Toast.makeText(getApplicationContext(), "Votre compte a été créé", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreerCompteActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Échec de la création du compte
            Toast.makeText(this, "Erreur lors de la création du compte", Toast.LENGTH_SHORT).show();
        }
    }
}