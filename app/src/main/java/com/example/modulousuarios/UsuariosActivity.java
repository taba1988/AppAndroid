package com.example.modulousuarios;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UsuariosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        DataBaseHelper db = new DataBaseHelper(this);
        LinearLayout layoutFilas = findViewById(R.id.layoutFilas);

        // Botón atrás
        if (findViewById(R.id.btnAtrasDash) != null) {
            findViewById(R.id.btnAtrasDash).setOnClickListener(v -> finish());
        }

        Cursor c = db.obtenerTodosLosUsuarios();

        int item = 1;
        while (c.moveToNext()) {
            LinearLayout fila = new LinearLayout(this);
            fila.setOrientation(LinearLayout.HORIZONTAL);

            // 4️⃣ Pintar filas con ANCHOS FIJOS (Los mismos que pusiste en el XML)
            fila.addView(crearCeldaDato(String.valueOf(item++), 50));
            fila.addView(crearCeldaDato(String.valueOf(c.getInt(0)), 50));
            fila.addView(crearCeldaDato(c.getString(1), 200)); // NOMBRES
            fila.addView(crearCeldaDato(c.getString(2), 120)); // DOCUMENTO
            fila.addView(crearCeldaDato(c.getString(3), 100)); // TELÉFONO
            fila.addView(crearCeldaDato(c.getString(4), 150)); // DIRECCIÓN
            fila.addView(crearCeldaDato(c.getString(6), 250)); // CORREO
            fila.addView(crearCeldaDato(c.getString(5), 100)); // USUARIO

            layoutFilas.addView(fila);
        }
        c.close();
    }

    // Esta función es la que hace la magia de alinear todo
    private TextView crearCeldaDato(String texto, int anchoDp) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setTextColor(Color.BLACK);
        tv.setPadding(10, 10, 10, 10);

        // Convertimos los DP a Pixeles reales del teléfono
        float escala = getResources().getDisplayMetrics().density;
        int anchoPx = (int) (anchoDp * escala + 0.5f);

        tv.setLayoutParams(new LinearLayout.LayoutParams(anchoPx, LinearLayout.LayoutParams.WRAP_CONTENT));
        return tv;
    }
}