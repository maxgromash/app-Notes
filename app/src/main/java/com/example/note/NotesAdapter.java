package com.example.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    //Наш массив
    public NotesAdapter(ArrayList<Note> notes) {
        this.notes = notes;
    }

    private List<Note> notes;

    //Создаём всё для обработки нажатий
    //Объект интервейся
    private OnNoteClickListener onNoteClickListener;

    //Сеттер
    public void setOnNoteClickListener(OnNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    //Интерфейс
    interface OnNoteClickListener {
        void onNoteClick(int position);

        void onLongClick(int position);
    }

    //Необходимо задать вид (xml) нашего item
    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    // holder - элемент, задаём значения (текст, цвета и тд.)
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewDescription.setText(note.getDescription());
        holder.textViewDayOfWeek.setText(note.getDayOfWeek());
        int colorId = 0;
        int priority = note.getPriority();
        switch (priority) {
            case 1:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_red_light);
                break;
            case 2:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_orange_light);
                break;
            case 3:
                colorId = holder.itemView.getResources().getColor(android.R.color.holo_green_light);
                break;
        }
        holder.textViewTitle.setBackgroundColor(colorId);
    }

    //Просто количество элементов в нашем массиве
    @Override
    public int getItemCount() {
        return notes.size();
    }

    //Класс ViewHolder для представления элемента
    class NotesViewHolder extends RecyclerView.ViewHolder {
        //Создаём все View
        private TextView textViewTitle;
        private TextView textViewDescription;
        private TextView textViewDayOfWeek;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            //Инициализируем
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewDayOfWeek = itemView.findViewById(R.id.textViewdayOfWeek);

            //Перенаправляем onClick на наш метод в onNoteClickListener
            //Короткое нажатие
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onNoteClickListener != null)
                        onNoteClickListener.onNoteClick(getAdapterPosition());
                }
            });

            //Длинное нажатие
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onNoteClickListener != null)
                        onNoteClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
