package cd.hena.app_artisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListePrestationActivity extends AppCompatActivity {


    private Button addPresationBtn;
    private ListView prestationsListView;
    private ArrayAdapter<String> prestationsAdapter;


    private PersistenceSqlLite persistenceSqlLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_prestation);
        init();
    }


    private void init(){
        addPresationBtn=findViewById(R.id.addPresationBtn);
        prestationsListView = findViewById(R.id.prestationsListView);
        persistenceSqlLite = new PersistenceSqlLite(this);
        ClicAdd();
        afficherListeClients();

    }

    private void afficherListeClients() {
        // Récupérer la liste de tous les clients depuis la base de données
        List<PersistenceSqlLite.Prestation> prestations = persistenceSqlLite.getAllPrestation();

        // Créer une liste de chaînes de caractères pour afficher les informations des clients
        List<String> prestationInfos = new ArrayList<>();
        for (PersistenceSqlLite.Prestation prestation : prestations) {
            String clientInfo = "Titre: " + prestation.getTitre() +
                    "\nCategories: " + prestation.getCategories() +
                    "\nDescription: " + prestation.getDescription() +
                    "\nPrix: " + prestation.getPrix();

            prestationInfos.add(clientInfo);
        }

        // Utiliser un adaptateur pour afficher la liste des informations des clients dans le ListView
        prestationsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prestationInfos);
        prestationsListView.setAdapter(prestationsAdapter);
    }

    private void ClicAdd(){
        addPresationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AjoutPrestation.class);
                startActivity(intent);
            }
        });
    }
}