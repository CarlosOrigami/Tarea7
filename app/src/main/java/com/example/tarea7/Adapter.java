package com.example.tarea7;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Tarea> tareas;
    private OnItemClickListener onItemClickListener;

    // Constructor
    public Adapter(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    // Interfaz para manejar clics simples
    public interface OnItemClickListener {
        void onItemClick(Tarea tarea, int position);
    }

    // Método para establecer el listener de clic
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista del item con el estado añadido
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tarea, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tarea tarea = tareas.get(position);

        // Configurar las vistas con los datos de la tarea
        holder.textViewAsignatura.setText(tarea.getAsignatura());
        holder.textViewDescripcion.setText(tarea.getDescripcion());
        holder.textViewFecha.setText(tarea.getFecha());
        holder.textViewEstado.setText(tarea.getEstado());  // Mostrar el estado

        // Configurar el listener para clics simples
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(tarea, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }

    // Método para actualizar el estado de una tarea
    public void actualizarTarea(int position, Tarea tarea) {
        tareas.set(position, tarea);
        notifyItemChanged(position);
    }

    // Clase ViewHolder para mantener las referencias de las vistas
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAsignatura;
        TextView textViewDescripcion;
        TextView textViewFecha;
        TextView textViewEstado;  // Añadir TextView para el estado

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAsignatura = itemView.findViewById(R.id.asignatura);
            textViewDescripcion = itemView.findViewById(R.id.descripcion);
            textViewFecha = itemView.findViewById(R.id.fecha);
            textViewEstado = itemView.findViewById(R.id.estado);  // Referencia al TextView de estado
        }
    }
}



