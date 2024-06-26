package com.example.virtualtrendsmobile.actividades.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.virtualtrendsmobile.actividades.InicioActivity;
import com.example.virtualtrendsmobile.actividades.SelectorActivity;
import com.example.virtualtrendsmobile.util.SessionManager;
import com.example.virtualtrendsmobile.R;

import java.util.Objects;

public class GetStartedActivity extends AppCompatActivity {
    Button admin, cliente;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        //init
        admin = findViewById(R.id.bt_administrador);
        cliente = findViewById(R.id.bt_cliente);

        sessionManager = new SessionManager(getApplicationContext());
        boolean b = sessionManager.checkSession();
        if(b){
           String resp = sessionManager.getSessionDetails("key_session_userType");
           if(Objects.equals(resp,"admin")){
               startActivity(new Intent(GetStartedActivity.this, Administrar.class));
               finish();
           }else{
               startActivity(new Intent(getApplicationContext(), SelectorActivity.class));
               finish();
           }
        }else{
            Toast.makeText(getApplicationContext(), "Bienvenido a Virtual Trends!", Toast.LENGTH_SHORT).show();
        }

        admin.setOnClickListener( v -> startActivity(new Intent(getApplicationContext(), CuentaAdmin.class)));
        cliente.setOnClickListener( v -> startActivity(new Intent(getApplicationContext(), InicioActivity.class)));

    }
    
}