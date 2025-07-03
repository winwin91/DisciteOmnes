package com.example.disciteomnes.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView; // WICHTIG!
import androidx.recyclerview.widget.RecyclerView;

import com.example.disciteomnes.R;
import com.example.disciteomnes.model.Task;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    private List<Task> taskList;

    // 🎨 Farben für die Cards
    private final int[] cardColors = {
            Color.parseColor("#FBC02D"), // Gelb
            Color.parseColor("#E91E63"), // Pink
            Color.parseColor("#7E57C2"), // Lila
            Color.parseColor("#26A69A"), // Türkis
            Color.parseColor("#FF9800")  // Orange
    };

    public TasksAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);

        holder.title.setText(task.getTitle());
        holder.description.setText(task.getDescription());
        holder.dueDate.setText(task.getDueDate());
        holder.assignedTo.setText("Zugewiesen an: " + task.getAssignedTo());
        holder.completed.setChecked(task.isCompleted());

        // 🎨 Card-Farbe dynamisch setzen
        int colorIndex = position % cardColors.length;
        holder.taskCard.setCardBackgroundColor(cardColors[colorIndex]);

        // Optional: Checkbox-Listener
        holder.completed.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            // TODO: Hier kannst du sofort updateTaskStatus() aufrufen, wenn du magst!
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, dueDate, assignedTo;
        CheckBox completed;
        CardView taskCard; // ✅ CardView Feld darf nicht fehlen!

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            description = itemView.findViewById(R.id.taskDescription);
            dueDate = itemView.findViewById(R.id.taskDueDate);
            assignedTo = itemView.findViewById(R.id.taskAssignedTo);
            completed = itemView.findViewById(R.id.taskCompleted);
            taskCard = itemView.findViewById(R.id.taskCard); // Richtig binden!
        }
    }
}
