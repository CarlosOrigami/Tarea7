package com.example.tarea7;

import android.app.AlertDialog;
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
                        // Eliminar la tarea y actualizar la lista
                        tareas.remove(position);
                        tareaAdapter.notifyItemRemoved(position);
                        Toast.makeText(this, "Tarea eliminada.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> {
                        // No hacer nada si el usuario cancela
                        dialog.dismiss();
                    })
                    .show(); // Mostrar el AlertDialog
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
            tareaAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Tarea añadida: " + descripcion, Toast.LENGTH_SHORT).show();
        }
    }
}



