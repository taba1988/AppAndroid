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

        // B1: Obtener el usuario que inició sesión
        String usuario = getIntent().getStringExtra(EXTRA_USUARIO);

        // B2: Mostrar en TextView
        TextView txtUsuario = findViewById(R.id.txtUsuario); // crea este TextView en tu layout
        if(usuario != null){
            txtUsuario.setText("Bienvenido, " + usuario);
        }

        // Lógica botón CERRAR SESIÓN
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // ÚNICO PASO NUEVO: tarjeta Usuarios
        CardView cardUsuarios = findViewById(R.id.cardUsuarios);
        cardUsuarios.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, UsuariosActivity.class);
            startActivity(intent);
        });
    }
}
