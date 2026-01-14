package com.example.modulousuarios;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/** Test instrumentado para la pantalla de registro.*/

@RunWith(AndroidJUnit4.class)
public class RegistroInstrumentedTest {

    private RegistroActivity registroActivity;
    private DataBaseHelper dbHelper;

    @Before
    public void setUp() {
        // Lanzar la actividad de registro
        ActivityScenario<RegistroActivity> scenario = ActivityScenario.launch(RegistroActivity.class);
        scenario.onActivity(activity -> {
            registroActivity = activity;
            dbHelper = new DataBaseHelper(activity);
            // Limpiar tabla de usuarios antes de cada test
            dbHelper.getWritableDatabase().execSQL("DELETE FROM usuarios");
        });
    }

    @After
    public void tearDown() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    @Test
    public void testRegistroUsuarioExitoso() {
        // Insertar usuario de prueba
        String resultado = dbHelper.insertarUsuario(
                "Juan Perez",
                "12345678",
                "555-1234",
                "Calle Falsa 123",
                "juanemp",
                "juan@mail.com",
                "1234",
                "admin"
        );
        assertEquals("Usuario registrado exitosamente", resultado);
    }

    @Test
    public void testRegistroUsuarioDuplicadoDocumento() {
        // Insertar usuario original
        dbHelper.insertarUsuario(
                "Juan Perez",
                "12345678",
                "555-1234",
                "Calle Falsa 123",
                "juanemp",
                "juan@mail.com",
                "1234",
                "admin"
        );

        // Intentar insertar duplicado
        String resultado = dbHelper.insertarUsuario(
                "Maria Lopez",
                "12345678",
                "555-5678",
                "Calle Verdadera 456",
                "mariaemp",
                "maria@mail.com",
                "5678",
                "user"
        );
        assertEquals("DOCUMENTO_DUPLICADO", resultado);
    }
}
