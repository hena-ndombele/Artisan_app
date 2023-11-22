package cd.hena.app_artisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListeClientsActivity extends AppCompatActivity {

    private Button addBtn;
    private ListView clientsListView;
    private ArrayAdapter<String> clientsAdapter;
    private PersistenceSqlLite persistenceSqlLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_client);

        clientsListView = findViewById(R.id.clientsListView);
        persistenceSqlLite = new PersistenceSqlLite(this);

        afficherListeClients();

        init();
    }

    private void init(){
        addBtn=findViewById(R.id.addBtn);
        ClicAdd();
    }

    private void ClicAdd(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SaisieClientActivity.class);
                startActivity(intent);
            }
        });
    }

    private void afficherListeClients() {
        // Récupérer la liste de tous les clients depuis la base de données
        List<PersistenceSqlLite.Client> clients = persistenceSqlLite.getAllClients();

        // Créer une liste de chaînes de caractères pour afficher les informations des clients
        List<String> clientsInfos = new ArrayList<>();
        for (PersistenceSqlLite.Client client : clients) {
            String clientInfo = "Nom: " + client.getNom() +
                    "\nPrénom: " + client.getPrenom() +
                    "\nAdresse: " + client.getAdresse() +
                    "\nTéléphone: " + client.getTelephone() +
                    "\nDate: " + client.getDate() +
                    "\nType: " + client.getType();
            clientsInfos.add(clientInfo);
        }

        // Utiliser un adaptateur pour afficher la liste des informations des clients dans le ListView
        clientsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, clientsInfos);
        clientsListView.setAdapter(clientsAdapter);
    }
}