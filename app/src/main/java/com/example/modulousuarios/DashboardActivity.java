package com.example.modulousuarios;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    public static final String EXTRA_USUARIO = "usuario";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Obtener el usuario que inició sesión
        String usuario = getIntent().getStringExtra(EXTRA_USUARIO);

// --- logica par Datos paramostrar del usuario logueado---
        TextView txtUsuario = findViewById(R.id.txtUsuario);
        TextView txtCargo = findViewById(R.id.txtCargo);
        TextView txtDocumento = findViewById(R.id.txtDocumento);

        String loginNombre = getIntent().getStringExtra(EXTRA_USUARIO);

        if (loginNombre != null) {
            DataBaseHelper dbHelper = new DataBaseHelper(this);
            android.database.Cursor cursor = dbHelper.obtenerUsuario(loginNombre);

            if (cursor != null && cursor.moveToFirst()) {
                // Obtener datos de las columnas de la Base de Datos
                String nombres = cursor.getString(cursor.getColumnIndexOrThrow("nombres"));
                String rol = cursor.getString(cursor.getColumnIndexOrThrow("rol"));
                String doc = cursor.getString(cursor.getColumnIndexOrThrow("documento"));

                // Mostrar los datos del usuario logeado
                txtUsuario.setText("Nombres: " + nombres);
                txtCargo.setText("Cargo: " + (rol != null ? rol : ""));
                txtDocumento.setText("ID: " + doc);
                cursor.close();
            }
        }

        // Lógica botón CERRAR SESIÓN
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // Logita para navegar a usuarios
        CardView cardUsuarios = findViewById(R.id.cardUsuarios);
        cardUsuarios.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, UsuariosActivity.class);
            startActivity(intent);
        });
    }
}
