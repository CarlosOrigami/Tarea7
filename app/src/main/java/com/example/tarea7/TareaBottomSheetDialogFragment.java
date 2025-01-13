package com.example.tarea7;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.Serializable;

public class TareaBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private Tarea tarea;
    private int position;

    // Método estático para crear una nueva instancia del fragmento y pasarle los parámetros
    public static TareaBottomSheetDialogFragment newInstance(Tarea tarea, int position) {
        TareaBottomSheetDialogFragment fragment = new TareaBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("tarea", (Serializable) tarea);  // Guardamos la tarea
        args.putInt("position", position);  // Guardamos la posición
        fragment.setArguments(args);  // Asignamos el Bundle al fragmento
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Obtener los datos del Bundle
        if (getArguments() != null) {
            tarea = (Tarea) getArguments().getSerializable("tarea");
            position = getArguments().getInt("position");
        }

        // Inflar el layout del BottomSheet
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        // Configurar los elementos del BottomSheet (por ejemplo, editar, borrar, marcar como completado)
        TextView asignaturaTextView = view.findViewById(R.id.asignaturaTextView);
        Button editButton = view.findViewById(R.id.editButton);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        Button completeButton = view.findViewById(R.id.completeButton);

        // Mostrar la tarea
        asignaturaTextView.setText(tarea.getAsignatura());

        // Acciones de los botones
        editButton.setOnClickListener(v -> {
            // Aquí puedes poner la lógica para editar la tarea
        });

        deleteButton.setOnClickListener(v -> {
            // Aquí puedes poner la lógica para borrar la tarea
            // y notificar al adapter que se ha eliminado
        });

        completeButton.setOnClickListener(v -> {
            // Aquí puedes poner la lógica para marcar la tarea como completada
            // y mostrar un Toast
        });

        return view;
    }
}

