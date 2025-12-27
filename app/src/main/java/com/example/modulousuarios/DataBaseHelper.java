package com.example.modulousuarios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteConstraintException;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "VIFACSys.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_USUARIOS = "usuarios";

    private static final String COL_ID = "id";
    private static final String COL_NOMBRES = "nombres";
    private static final String COL_DOCUMENTO = "documento";
    private static final String COL_TELEFONO = "telefono";
    private static final String COL_DIRECCION = "direccion";
    private static final String COL_USUARIO = "usuario";
    private static final String COL_CORREO = "correo";
    private static final String COL_PASSWORD = "password";
    private static final String COL_ROL = "rol";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_USUARIOS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NOMBRES + " TEXT, " +
                COL_DOCUMENTO + " TEXT UNIQUE, " +
                COL_TELEFONO + " TEXT, " +
                COL_DIRECCION + " TEXT, " +
                COL_USUARIO + " TEXT UNIQUE, " +
                COL_CORREO + " TEXT UNIQUE, " +
                COL_PASSWORD + " TEXT, " +
                COL_ROL + " TEXT" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }
    public String insertarUsuario(
            String nombres,
            String documento,
            String telefono,
            String direccion,
            String usuario,
            String correo,
            String password,
            String rol
    ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put(COL_NOMBRES, nombres);
        valores.put(COL_DOCUMENTO, documento);
        valores.put(COL_TELEFONO, telefono);
        valores.put(COL_DIRECCION, direccion);
        valores.put(COL_USUARIO, usuario);
        valores.put(COL_CORREO, correo);
        valores.put(COL_PASSWORD, password);
        valores.put(COL_ROL, rol);

        try {
            db.insertOrThrow(TABLE_USUARIOS, null, valores);
            return "Usuario registrado exitosamente";
        } catch (SQLiteConstraintException e) {
            String msg = e.getMessage();
            if (msg != null) {
                if (msg.contains(COL_DOCUMENTO)) return "DOCUMENTO_DUPLICADO";
                if (msg.contains(COL_USUARIO))   return "USUARIO_DUPLICADO";
                if (msg.contains(COL_CORREO))    return "CORREO_DUPLICADO";
            }
            return "DUPLICADO";
        } finally {
            db.close();
        }
    }


    public boolean validarUsuario(String usuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT " + COL_ID +
                        " FROM " + TABLE_USUARIOS +
                        " WHERE " + COL_USUARIO + "=? AND " + COL_PASSWORD + "=?",
                new String[]{usuario, password}
        );

        boolean existe = cursor.moveToFirst();
        cursor.close();
        db.close();
        return existe;
    }

    public Cursor obtenerUsuario(String usuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_USUARIOS + " WHERE " + COL_USUARIO + "=?",
                new String[]{usuario}
        );
    }

    public Cursor obtenerTodosLosUsuarios() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT id, nombres, documento, telefono, direccion, usuario, correo FROM usuarios",
                null
        );
    }

}
