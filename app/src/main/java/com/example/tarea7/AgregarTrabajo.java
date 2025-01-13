package com.example.tarea7;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class AgregarTrabajo extends DialogFragment {

    private EditText descripcionEditText;
    private EditText fechaEditText;
    private Spinner asignaturaSpinner;
    private int year, month, day;

    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        // Crear el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.agregar_trabajo, null);

        descripcionEditText = view.findViewById(R.id.descripcionEditText);
        fechaEditText = view.findViewById(R.id.fechaEditText);
        asignaturaSpinner = view.findViewById(R.id.asignaturaSpinner);

        // Configuración del Spinner para las asignaturas
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.asignaturas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        asignaturaSpinner.setAdapter(adapter);

        // Si estamos editando una tarea, rellenamos los campos
        if (getArguments() != null) {
            String asignatura = getArguments().getString("asignatura");
            String descripcion = getArguments().getString("descripcion");
            String fecha = getArguments().getString("fecha");

            asignaturaSpinner.setSelection(adapter.getPosition(asignatura));
            descripcionEditText.setText(descripcion);
            fechaEditText.setText(fecha);
        }

        // Configuración del DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        fechaEditText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getActivity(),
                    (view1, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                        fechaEditText.setText(selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        builder.setView(view)
                .setPositiveButton("Guardar", (dialog, id) -> {
                    String asignaturaSeleccionada = asignaturaSpinner.getSelectedItem().toString();
                    String descripcion = descripcionEditText.getText().toString();
                    String fecha = fechaEditText.getText().toString();

                    // Llamar al método agregarTarea de la actividad
                    MainActivity activity = (MainActivity) getActivity();
                    if (activity != null) {
                        activity.agregarTarea(asignaturaSeleccionada, descripcion, fecha);
                    }
                })
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    // Cancelar
                });

        return builder.create();
    }
}

