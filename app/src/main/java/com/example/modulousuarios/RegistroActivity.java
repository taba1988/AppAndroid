package com.example.modulousuarios;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

public class RegistroActivity extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper; // B1: referencia DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        // B2: Inicializar DB
        dataBaseHelper = new DataBaseHelper(this);

        // B3: Ajuste EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etNombre = findViewById(R.id.etNombres);
        EditText etDocumento = findViewById(R.id.etDocumento);
        EditText etTelefono = findViewById(R.id.etTelefono);
        EditText etDireccion = findViewById(R.id.etDireccion);

        EditText etUsuario = findViewById(R.id.etUsuario);
        EditText etCorreo = findViewById(R.id.etCorreo);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Spinner spRol = findViewById(R.id.spRol);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        Button btnCancelar = findViewById(R.id.btncancelar);
        btnCancelar.setOnClickListener(v -> {
            finish();
        });

        btnRegistrar.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString().trim();
            String correo = etCorreo.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();
            String rol = spRol.getSelectedItem().toString();

            if(usuario.isEmpty() || correo.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!password.equals(confirmPassword)){
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🔹 Nuevo uso de insertarUsuario con manejo de duplicados
            String resultado = dataBaseHelper.insertarUsuario(
                    etNombre.getText().toString().trim(),
                    etDocumento.getText().toString().trim(),
                    etTelefono.getText().toString().trim(),
                    etDireccion.getText().toString().trim(),
                    usuario,
                    correo,
                    password,
                    rol
            );

            switch (resultado) {
                case "Usuario registrado exitosamente":
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                    finish();
                    break;
                case "DOCUMENTO_DUPLICADO":
                    Toast.makeText(this, "Error: documento ya existe", Toast.LENGTH_SHORT).show();
                    break;
                case "USUARIO_DUPLICADO":
                    Toast.makeText(this, "Error: usuario ya existe", Toast.LENGTH_SHORT).show();
                    break;
                case "CORREO_DUPLICADO":
                    Toast.makeText(this, "Error: correo ya existe", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

    }
}
