package com.example.tarea7;

import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewTareas = findViewById(R.id.recyclerViewTareas);
        tareas = new ArrayList<>();

        // Cargar datos iniciales
        cargarDatos();

        // Configuración del RecyclerView y el adaptador
        tareaAdapter = new Adapter(tareas, this);
        recyclerViewTareas.setAdapter(tareaAdapter);
        recyclerViewTareas.setLayoutManager(new LinearLayoutManager(this));

        // Configurar clic largo para mostrar el BottomSheetDialog
        tareaAdapter.setOnItemLongClickListener((tarea, position) -> mostrarBottomSheet(tarea, position));

        // Botón flotante para agregar una nueva tarea
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            AgregarTrabajo agregarTrabajoDialog = new AgregarTrabajo();
            agregarTrabajoDialog.show(getSupportFragmentManager(), "AgregarTrabajo");
        });
    }

    // Método para manejar el clic en una tarea
    public void onTareaClick(Tarea tarea, int position) {
        Toast.makeText(this, "Tarea clickeada: " + tarea.getDescripcion(), Toast.LENGTH_SHORT).show();
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
            // Lógica para editar la tarea
            Toast.makeText(this, "Editar tarea: " + tarea.getDescripcion(), Toast.LENGTH_SHORT).show();
        });

        eliminarOpcion.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            tareas.remove(position);
            tareaAdapter.notifyItemRemoved(position);
            Toast.makeText(this, "Tarea eliminada.", Toast.LENGTH_SHORT).show();
        });

        completarOpcion.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            // Lógica para marcar como completada
            Toast.makeText(this, "Tarea completada: " + tarea.getDescripcion(), Toast.LENGTH_SHORT).show();
        });

        bottomSheetDialog.show();
    }

    // Método para cargar datos iniciales
    private void cargarDatos() {
        tareas.add(new Tarea("PMDM", "Descripción de la tarea 1", "2025-01-15"));
        tareas.add(new Tarea("AD", "Descripción de la tarea 2", "2025-01-16"));
    }

    // Método para agregar una tarea
    public void agregarTarea(String asignatura, String descripcion, String fecha) {
        if (asignatura.isEmpty() || descripcion.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Tarea nuevaTarea = new Tarea(asignatura, descripcion, fecha);
        tareas.add(nuevaTarea);

        if (tareaAdapter != null) {
            tareaAdapter.notifyDataSetChanged();
        } else {
            Log.e("MainActivity", "El adaptador no está inicializado.");
        }

        Toast.makeText(this, "Tarea añadida: " + descripcion, Toast.LENGTH_SHORT).show();
    }
}
