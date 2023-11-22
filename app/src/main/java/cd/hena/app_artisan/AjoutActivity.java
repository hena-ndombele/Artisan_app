package cd.hena.app_artisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AjoutActivity extends AppCompatActivity {

    Button clientss;
    Button vousBtn;

    Button prestationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);
        init();
    }


    private void init(){
        clientss=findViewById(R.id.clientssBn);
        vousBtn=findViewById(R.id.vousBtn);
        prestationBtn=findViewById(R.id.prestationBtn);
        ClicClient();
        ClicVous();
        ClicPrestation();
    }
    private void ClicClient(){
        clientss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListeClientsActivity.class);
                startActivity(intent);
            }
        });
    }
    private void ClicVous(){
        vousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrenderRendezVousActivity.class);
                startActivity(intent);
            }
        });
    }
    private void ClicPrestation(){
        prestationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListePrestationActivity.class);
                startActivity(intent);
            }
        });
    }
}