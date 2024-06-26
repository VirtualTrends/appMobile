package com.example.virtualtrendsmobile.actividades;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.virtualtrendsmobile.util.SessionManager;
import com.example.virtualtrendsmobile.R;
import com.example.virtualtrendsmobile.database.DatabaseHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class ContactoActivity extends AppCompatActivity {

    EditText editEmail, editAsunto, editMensaje;
    private DatabaseHelper dbHelper;

SessionManager sessionManager;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNav;
    ActionBarDrawerToggle drawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        sessionManager = new SessionManager(getApplicationContext());

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.home){
                    startActivity(new Intent(getApplicationContext(), SelectorActivity.class));
                }  else if (id==R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), EditarPerfilActivity.class));
                }  else if (id==R.id.calendar) {
                    startActivity(new Intent(getApplicationContext(), TurnosActivity.class));
                }
                return false;
            }
        });

        editEmail = findViewById(R.id.editEmail);
        editAsunto = findViewById(R.id.editAsunto);
        editMensaje = findViewById(R.id.editMensaje);
        dbHelper = new DatabaseHelper(this);

        BottomNavigationView nav = findViewById(R.id.btnNavSelector);
        nav.setSelectedItemId(R.id.info);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.back){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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


    public void enviar(View view) {

        String email = editEmail.getText().toString();
        String asunto = editAsunto.getText().toString();
        String mensaje = editMensaje.getText().toString();

        long resultado = insertarDatosContacto(email, asunto, mensaje);

        if (resultado != -1) {

            Toast.makeText(this, "Mensaje recibido", Toast.LENGTH_SHORT).show();
            
        } else {

            Toast.makeText(this, "Error al enviar el mensaje", Toast.LENGTH_SHORT).show();
        }
    }

    private long insertarDatosContacto(String email, String asunto, String mensaje) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL_CONTACT, email);
        values.put(DatabaseHelper.COLUMN_ASUNTO, asunto);
        values.put(DatabaseHelper.COLUMN_MENSAJE, mensaje);

        long resultado = db.insert(DatabaseHelper.TABLE_CONTACT, null, values);

        db.close();

        return resultado;
    }

}
