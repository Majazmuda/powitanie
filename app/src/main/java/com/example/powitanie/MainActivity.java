package com.example.powitanie;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
public class MainActivity extends AppCompatActivity {
    private EditText editTextImie;
    private static final String CHANNEL_ID = "powitanie_channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextImie = findViewById(R.id.editTextImie);
        Button buttonPowitanie = findViewById(R.id.buttonPowitanie);
        buttonPowitanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imie = editTextImie.getText().toString().trim();
                if (imie.isEmpty()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Błąd")
                            .setMessage("Proszę wpisać swoje imię!")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    pokazDialogPotwierdzenia(imie);
                }
            }
        });
    }
    private void pokazDialogPotwierdzenia(final String imie) {
        new AlertDialog.Builder(this)
                .setTitle("Potwierdzenie")
                .setMessage("Cześć " + imie + "! Czy chcesz otrzymać powiadomienie powitalne?")
                .setPositiveButton("Tak, poproszę", (dialog, which) -> {
                    wyswietlPowiadomienie(imie);
                    Toast.makeText(MainActivity.this, "Powiadomienie zostało wysłane!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Nie, dziękuję", (dialog, which) -> {
                    Toast.makeText(MainActivity.this, "Rozumiem. Nie wysyłam powiadomienia.", Toast.LENGTH_SHORT).show();
                })
                .show();
    }
    private void wyswietlPowiadomienie(String imie) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Powitanie Kanał",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Witaj!")
                .setContentText("Miło Cię widzieć, " + imie + "!")
                .setAutoCancel(true);
        notificationManager.notify(1, builder.build());
    }
}