package cd.hena.app_artisan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AjoutPrestation extends AppCompatActivity {

    private EditText titresEditText;
    private EditText categoriesEditText;
    private EditText descriptionEditText;
    private EditText prixEditText;

    private Button prestationBtn;
    private PersistenceSqlLite persistenceSqlLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_prestation);

        init();
    }

    private void init(){
        titresEditText=findViewById(R.id.titresEditText);
        categoriesEditText=findViewById(R.id.categoriesEditTex);
        prixEditText=findViewById(R.id.prixEditText);
        descriptionEditText=findViewById(R.id.descriptionEditText);
        prestationBtn=findViewById(R.id.prestationBtn);
        setClicListener();
    }

    private void setClicListener() {
        prestationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void showConfirmationDialog() {
        // Récupérer les données du formulaire
        String titre = titresEditText.getText().toString();
        String categories = categoriesEditText.getText().toString();
        String prix = prixEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        persistenceSqlLite = new PersistenceSqlLite(this);


        // Vérifier si tous les champs sont remplis
        if (titre.isEmpty() || categories.isEmpty() || prix.isEmpty() || description.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Créer une instance de la classe Client avec les données du formulaire
        PersistenceSqlLite.Prestation prestation = new PersistenceSqlLite.Prestation(titre, categories, description, prix);

        // Afficher un dialogue de confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Êtes-vous sûr de vouloir enregistrer cette prestation ?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertPrestation(prestation);
            }
        });
        builder.setNegativeButton("Non", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void insertPrestation(PersistenceSqlLite.Prestation prestation) {
        // Insérer le client dans la base de données en utilisant l'instance de PersistenceSqlLite
        long newRowId = persistenceSqlLite.creerPrestation(prestation);

        // Afficher un message de succès ou d'échec
        if (newRowId != -1) {
            Toast.makeText(getApplicationContext(), "La prestation a été enregistré avec succès", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AjoutPrestation.this, ListePrestationActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Échec de l'enregistrement de la prestaion", Toast.LENGTH_SHORT).show();
        }
    }
}