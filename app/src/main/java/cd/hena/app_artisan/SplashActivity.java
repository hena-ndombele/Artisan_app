package cd.hena.app_artisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE);

        // Vérifier si l'utilisateur est déjà connecté
        if (isLoggedIn()) {
            redirectToHome();
        } else {
            // Rediriger vers la page MainActivity après un délai de 3 secondes
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }

    private boolean isLoggedIn() {
        // Vérifier si la clé "isLoggedIn" est définie à true dans les SharedPreferences
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void redirectToHome() {
        Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}