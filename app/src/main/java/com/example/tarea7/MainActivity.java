package com.example.tarea7;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewTareas;
    private Adapter tareaAdapter;
    private List<Tarea> tareas;
    private Tarea tareaParaEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTareas = findViewById(R.id.recyclerViewTareas);
        tareas = new ArrayList<>();  // Lista vacía al inicio

        // Configuración del RecyclerView y el adaptador
        tareaAdapter = new Adapter(tareas);
        recyclerViewTareas.setAdapter(tareaAdapter);
        recyclerViewTareas.setLayoutManager(new LinearLayoutManager(this));

        // Cargar tareas desde la base de datos
        cargarTareasDeDb();

        // Configurar clic para mostrar el BottomSheetDialog
        tareaAdapter.setOnItemClickListener((tarea, position) -> mostrarBottomSheet(tarea, position));

        // Botón flotante para agregar una nueva tarea
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            AgregarTrabajo agregarTrabajoDialog = new AgregarTrabajo();
            agregarTrabajoDialog.show(getSupportFragmentManager(), "AgregarTrabajo");
        });
    }

    // Método para mostrar el BottomSheetDialog
    private void mostrarBottomSheet(Tarea tarea, int position) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        // Configurar las opciones del BottomSheet
        TextView editarOpcion = bottomSheetView.findViewById(R.id.opcionEditar);
        TextView eliminarOpcion = bottomSheetView.findViewById(R.id.opcionEliminar);
        TextView completarOpcion = bottomSheetView.findViewById(R.id.opcionCompletar);

        editarOpcion.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            tareaParaEditar = tarea; // Guardar la tarea para editar

            // Pasar la tarea a editar al Fragmento de AgregarTrabajo
            AgregarTrabajo agregarTrabajoDialog = new AgregarTrabajo();
            Bundle args = new Bundle();
            args.putString("descripcion", tarea.getDescripcion());
            args.putString("fecha", tarea.getFecha());
            agregarTrabajoDialog.setArguments(args);
            agregarTrabajoDialog.show(getSupportFragmentManager(), "EditarTrabajo");
        });

        eliminarOpcion.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();

            // Crear el AlertDialog de confirmación
            new AlertDialog.Builder(this)
                    .setMessage("¿Seguro que quieres eliminar esta tarea?")
                    .setPositiveButton("Eliminar", (dialog, which) -> {
                        // Eliminar la tarea de la lista y la base de datos
                        eliminarTareaDeDb(tarea.getDescripcion());
                        tareas.remove(position);
                        tareaAdapter.notifyItemRemoved(position);
                        Toast.makeText(this, "Tarea eliminada.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                    .show();
        });

        completarOpcion.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            tarea.setEstado("Completada"); // Cambiar el estado de la tarea
            tareaAdapter.actualizarTarea(position, tarea); // Actualizar la tarea en el adaptador
            Toast.makeText(this, "Tarea completada: " + tarea.getDescripcion(), Toast.LENGTH_SHORT).show();
        });

        bottomSheetDialog.show();
    }

    // Método para agregar o editar una tarea
    public void agregarTarea(String asignatura, String descripcion, String fecha) {
        if (asignatura.isEmpty() || descripcion.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tareaParaEditar != null) {
            tareaParaEditar.setAsignatura(asignatura);
            tareaParaEditar.setDescripcion(descripcion);
            tareaParaEditar.setFecha(fecha);
            tareaAdapter.notifyDataSetChanged(); // Notificar que se ha editado la tarea
            tareaParaEditar = null; // Limpiar la tarea para editar
            Toast.makeText(this, "Tarea editada: " + descripcion, Toast.LENGTH_SHORT).show();
        } else {
            Tarea nuevaTarea = new Tarea(asignatura, descripcion, fecha);
            tareas.add(nuevaTarea);
            guardarTareaEnDb(asignatura, descripcion, fecha, "Pendiente"); // Guardar en SQLite
            tareaAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Tarea añadida: " + descripcion, Toast.LENGTH_SHORT).show();
        }
    }

    // Método para guardar una tarea en la base de datos
    private void guardarTareaEnDb(String asignatura, String descripcion, String fecha, String estado) {
        SQLiteDatabase db = new TareaDB(this).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("asignatura", asignatura);
        valores.put("descripcion", descripcion);
        valores.put("fecha", fecha);
        valores.put("estado", estado);

        db.insert("tareas", null, valores); // Nombre de la tabla directamente
        db.close();
    }

    // Método para cargar tareas desde la base de datos
    private void cargarTareasDeDb() {
        SQLiteDatabase db = new TareaDB(this).getReadableDatabase();

        String[] columnas = {"asignatura", "descripcion", "fecha", "estado"}; // Nombres de las columnas directamente

        Cursor cursor = db.query(
                "tareas",    // Nombre de la tabla directamente
                columnas,
                null,
                null,
                null,
                null,
                null
        );

        tareas.clear();
        while (cursor.moveToNext()) {
            String asignatura = cursor.getString(cursor.getColumnIndexOrThrow("asignatura"));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
            String estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"));

            tareas.add(new Tarea(asignatura, descripcion, fecha, estado));
        }
        cursor.close();
        db.close();
        tareaAdapter.notifyDataSetChanged();
    }

    // Método para eliminar una tarea de la base de datos
    private void eliminarTareaDeDb(String descripcion) {
        SQLiteDatabase db = new TareaDB(this).getWritableDatabase();
        db.delete("tareas", "descripcion=?", new String[]{descripcion}); // Tabla y columna directamente
        db.close();
    }
}


