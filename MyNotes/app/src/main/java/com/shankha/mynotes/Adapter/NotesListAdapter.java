package com.shankha.mynotes.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.shankha.mynotes.Models.Notes;
import com.shankha.mynotes.NotesClickedListener;
import com.shankha.mynotes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NoteListViewHolder> {
    Context context;
   List<Notes> list;
    NotesClickedListener listener;

    public NotesListAdapter(Context context, List<Notes> list, NotesClickedListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteListViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder holder, int position) {
        holder.txtV_title.setText(list.get(position).getTitle());
        holder.txtV_title.setSelected(true);
        holder.txtV_notes.setText(list.get(position).getNotes());
        holder.txtV_date.setText(list.get(position).getDate());
        holder.txtV_date.setSelected(true);
        if(list.get(position).isPinned()){
            holder.img_pin.setImageResource(R.drawable.baseline_pin);
        }else{
            holder.img_pin.setImageResource(0);
        }

        int color_code=randomColor();
        holder.notes_contener.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));

        holder.notes_contener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              listener.onClicked(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_contener.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClicked(list.get(holder.getAdapterPosition()),holder.notes_contener);
                return true;
            }
        });

    }

    private int randomColor()
    {
        List<Integer> colorCode= new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);
        colorCode.add(R.color.color6);
        colorCode.add(R.color.color7);
        colorCode.add(R.color.color8);
        colorCode.add(R.color.color9);
        colorCode.add(R.color.color10);
        colorCode.add(R.color.color11);
        colorCode.add(R.color.color12);

        Random random=new Random();
        int randomColor=random.nextInt(colorCode.size());
        return colorCode.get(randomColor);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filteredList(List<Notes> filtered_list){
        list=filtered_list;
        notifyDataSetChanged();
    }

}

class NoteListViewHolder extends RecyclerView.ViewHolder{
    TextView txtV_title,txtV_notes,txtV_date;
    ImageView img_pin;
    CardView notes_contener;
    public NoteListViewHolder(@NonNull View itemView) {
        super(itemView);
        img_pin=itemView.findViewById(R.id.img_pin);
        txtV_title=itemView.findViewById(R.id.txtV_title);
        txtV_notes=itemView.findViewById(R.id.txtV_notes);
        txtV_date=itemView.findViewById(R.id.txtV_date);
        notes_contener=itemView.findViewById(R.id.notes_contener);
    }
}
