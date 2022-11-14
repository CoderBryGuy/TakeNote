package com.example.takenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.takenote.database.Note;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {

    private List<Note> notes;
    private OnItemClickListener listener;



    @NonNull
    @NotNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);


        return new NoteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoteHolder holder, int position) {
            Note note = notes.get(position);
            holder.name.setText(note.getTitle());
            holder.description.setText(note.getDescription());



    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNotes(int position){
        return notes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder{

    TextView name, description;

        public NoteHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_note_item);
            description = itemView.findViewById(R.id.desc_note_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemCLick(notes.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemCLick(Note note);


    }

    public void SetOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }






}
