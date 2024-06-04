package com.example.virtualtrendsmovile.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtualtrendsmovile.R;
import com.example.virtualtrendsmovile.database.DatabaseHelper;
import com.example.virtualtrendsmovile.modelos.Turno;
import com.example.virtualtrendsmovile.util.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class VerTurnoActivity extends AppCompatActivity {
    SessionManager sessionManager;
    TextView titulo, descripcion;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_turno);

        //inicializacion
        sessionManager = new SessionManager(getApplicationContext());
        titulo = findViewById(R.id.txtTitle);
        descripcion = findViewById(R.id.textTurnos);
        String user = sessionManager.getSessionDetails("key_session_nombre");
        String idUser = sessionManager.getSessionDetails("key_session_id");

        //descripcion
        descripcion.setText("¡Hola "+user+ "!");
        //db
        dbHelper = new DatabaseHelper(this);
        Turno t = dbHelper.selectTurno(idUser);
        if(t!=null){
            titulo.setText("Tienes un turno el día: "+ t.getFecha()+ "\n"+ "en el horario: " + t.getFranjaHoraria());
        }else{
            titulo.setText("No tienes un turno asignado.");
            Toast.makeText(getApplicationContext(), "No hay turnos asignados.", Toast.LENGTH_LONG).show();
        }

        //nav
        BottomNavigationView nav = findViewById(R.id.btnNavSelector);
        nav.setSelectedItemId(R.id.back);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.back){
                    startActivity(new Intent(getApplicationContext(), TurnosActivity.class));
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
}