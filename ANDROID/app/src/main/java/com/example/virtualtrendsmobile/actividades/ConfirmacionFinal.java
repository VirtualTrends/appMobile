package com.example.virtualtrendsmobile.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualtrendsmobile.util.SessionManager;
import com.example.virtualtrendsmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ConfirmacionFinal extends AppCompatActivity {
    String fecha, horario;

    SessionManager sessionManager;
    TextView tv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_final);


        sessionManager = new SessionManager(getApplicationContext());
        tv = findViewById(R.id.tv_turno_mensaje);
        Intent i = getIntent();
        fecha = i.getStringExtra("fecha");
        horario = i.getStringExtra("horario");
        tv.setText("Tienes un turno el dia: " + fecha + "\n" + "Horario: " + horario);

        BottomNavigationView nav = findViewById(R.id.btnNavSelector);
        nav.setSelected(true);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id==R.id.back){
                    startActivity(new Intent(getApplicationContext(), ComprobacionReserva.class));
                } else if (id==R.id.info) {
                    startActivity(new Intent(getApplicationContext(), ContactoActivity.class));
                }else if (id== R.id.map){
                    startActivity(new Intent(getApplicationContext(), DondeEstamosActivity.class));
                } else if (id==R.id.turn) {
                    startActivity(new Intent(getApplicationContext(), NuestroServicio.class));
                } else if (id==R.id.logout) {
                    sessionManager.logout();
                    startActivity(new Intent(getApplicationContext(), InicioActivity.class));
                }

                return false;
            }

        });

    }

    public void confirmar_final_reserva(View view){
        Intent intent = new Intent(this, ComprobacionReserva.class);
        startActivity(intent);
        finish();
    }
}
