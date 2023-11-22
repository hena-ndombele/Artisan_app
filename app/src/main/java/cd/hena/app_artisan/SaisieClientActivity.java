package cd.hena.app_artisan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import cd.hena.app_artisan.PersistenceSqlLite;
import cd.hena.app_artisan.R;

public class SaisieClientActivity extends AppCompatActivity {

    private EditText dateEditText;
    private EditText prenomsEditText;
    private EditText nomsEditText;
    private EditText adresseEditText;
    private EditText telsEditText;
    private EditText typeEditText;

    private Button clientBtn;

    private PersistenceSqlLite persistenceSqlLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisie_client);
        init();
    }

    private void init() {
        dateEditText = findViewById(R.id.dateEditText);
        persistenceSqlLite = new PersistenceSqlLite(this);
        prenomsEditText = findViewById(R.id.prenomsEditText);
        nomsEditText = findViewById(R.id.nomsEditText);
        adresseEditText = findViewById(R.id.adresseEditText);
        telsEditText = findViewById(R.id.telsEditText);
        typeEditText = findViewById(R.id.typeEditText);
        clientBtn = findViewById(R.id.clientBtn);
        setClicListener();
    }

    private void setClicListener() {
        clientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void showConfirmationDialog() {
        // Récupérer les données du formulaire
        String prenom = prenomsEditText.getText().toString();
        String nom = nomsEditText.getText().toString();
        String adresse = adresseEditText.getText().toString();
        String telephone = telsEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String type = typeEditText.getText().toString();

        // Vérifier si tous les champs sont remplis
        if (prenom.isEmpty() || nom.isEmpty() || adresse.isEmpty() || telephone.isEmpty() || date.isEmpty() || type.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer une instance de la classe Client avec les données du formulaire
        PersistenceSqlLite.Client client = new PersistenceSqlLite.Client(prenom, nom, adresse, telephone, date, type);

        // Afficher un dialogue de confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Êtes-vous sûr de vouloir enregistrer ce client ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertClient(client);
            }
        });
        builder.setNegativeButton("Non", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void insertClient(PersistenceSqlLite.Client client) {
        // Insérer le client dans la base de données en utilisant l'instance de PersistenceSqlLite
        long newRowId = persistenceSqlLite.creerClient(client);

        // Afficher un message de succès ou d'échec
        if (newRowId != -1) {
            Toast.makeText(getApplicationContext(), "Le client a été enregistré avec succès", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SaisieClientActivity.this, ListeClientsActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Échec de l'enregistrement du client", Toast.LENGTH_SHORT).show();
        }
    }

    public void showDatePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Méthode appelée lorsque l'utilisateur sélectionne une date
                        // Mettez à jour le champ de date avec la date sélectionnée
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        dateEditText.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}