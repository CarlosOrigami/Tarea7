package com.example.tarea7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.TareaViewHolder> {
    private List<Tarea> tareas;
    private Context context;

    public Adapter(List<Tarea> tareas, Context context) {
        this.tareas = tareas;
        this.context = context;
    }

    @NonNull
    @Override
    public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tarea, parent, false);
        return new TareaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);
        holder.asignatura.setText(tarea.getAsignatura());
        holder.descripcion.setText(tarea.getDescripcion());
        holder.fecha.setText(tarea.getFecha());

        // Establecer el clic directamente en el ViewHolder
        holder.itemView.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                // Si el contexto es MainActivity, manejar el clic
                ((MainActivity) context).onTareaClick(tarea, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    public void setOnItemLongClickListener(Object o) {
    }

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        TextView asignatura, descripcion, fecha;

        public TareaViewHolder(View itemView) {
            super(itemView);
            asignatura = itemView.findViewById(R.id.asignatura);
            descripcion = itemView.findViewById(R.id.descripcion);
            fecha = itemView.findViewById(R.id.fecha);
        }
    }
}

