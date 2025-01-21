package com.example.tarea7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TareaDB extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "tareas.db";
    private static final int DATABASE_VERSION = 1;

    // Crear tabla SQL directamente
    private static final String CREATE_TABLE =
            "CREATE TABLE tareas (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "asignatura TEXT, " +
                    "descripcion TEXT, " +
                    "fecha TEXT, " +
                    "estado TEXT);";

    public TareaDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE); // Crear la tabla
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tareas"); // Borrar tabla vieja
        onCreate(db); // Crear tabla nueva
    }
}
