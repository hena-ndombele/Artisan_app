package cd.hena.app_artisan;
import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;
import java.util.TimeZone;

import cd.hena.app_artisan.R;

public class PrenderRendezVousActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "appointment_notification_channel";
    private static final int NOTIFICATION_ID = 1;

    private EditText editTextTitle;
    private Button buttonCreateEvent;
    private Button buttonSelectDate;

    private Calendar selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prender_rendez_vous);

        editTextTitle = findViewById(R.id.editTextTitle);
        buttonCreateEvent = findViewById(R.id.buttonCreateEvent);
        buttonSelectDate = findViewById(R.id.buttonSelectDate);

        buttonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
            }
        });

        buttonSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void createEvent() {
        String title = editTextTitle.getText().toString();

        // Vérifier si l'autorisation d'accéder au calendrier a été accordée
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // Demander l'autorisation d'accéder au calendrier si elle n'a pas encore été accordée
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, PERMISSION_REQUEST_CODE);
        } else {
            // L'autorisation d'accéder au calendrier a déjà été accordée
            addEventToCalendar(title);
        }
    }

    private void addEventToCalendar(String title) {
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();

        // Paramètres de l'événement
        contentValues.put(CalendarContract.Events.CALENDAR_ID, 1);
        contentValues.put(CalendarContract.Events.TITLE, title);
        contentValues.put(CalendarContract.Events.DTSTART, selectedDate.getTimeInMillis());
        contentValues.put(CalendarContract.Events.DTEND, selectedDate.getTimeInMillis() + 3600000); // 1 heure
        contentValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

        // Ajouter l'événement au calendrier
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        contentResolver.insert(CalendarContract.Events.CONTENT_URI, contentValues);

        // Planifier la notification
        scheduleNotification();

        Toast.makeText(this, "Rendez-vous créé avec succès", Toast.LENGTH_SHORT).show();
    }

    private void scheduleNotification() {
        // Créer le canal de notification (uniquement pour les versions d'Android >= Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Appointment Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        // Créer l'intention pour lancer l'activité lorsque la notification est cliquée
        Intent intent = new Intent(this, PrenderRendezVousActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Créer l'objet NotificationCompat pour la notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Rappel de rendez-vous")
                .setContentText("Vous avez un rendez-vous prévu.")
                .setSmallIcon(R.drawable.logos)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Obtenir le gestionnaire de notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                selectedDate = Calendar.getInstance();
                selectedDate.set(year, monthOfYear, dayOfMonth);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // L'autorisation d'accéder au calendrier a été accordée
                createEvent();
            } else {
                // L'autorisation d'accéder au calendrier a été refusée
                Toast.makeText(this, "L'autorisation d'accéder au calendrier a été refusée", Toast.LENGTH_SHORT).show();
            }
        }
    }
}