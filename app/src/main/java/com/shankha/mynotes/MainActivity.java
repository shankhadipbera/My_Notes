package com.shankha.mynotes;


import  com.shankha.mynotes.R;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shankha.mynotes.Adapter.NotesListAdapter;
import com.shankha.mynotes.Database.RoomDB;
import com.shankha.mynotes.Models.Notes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<Notes> notes =new ArrayList<>();
    RoomDB database;
    FloatingActionButton btn;
    SearchView searchView_home;
    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycle_home);
        btn=findViewById(R.id.fac_add);
        searchView_home=findViewById(R.id.searchView_home);

        database=RoomDB.getInstance(this);
        notes=database.mainDAO().getAll();

        updateRecycler(notes);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, MainActivity2.class),101);
            }
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newText) {
        List<Notes> filtered_list=new ArrayList<>();
        for(Notes singalnotes : notes){
            if(singalnotes.getTitle().toLowerCase().contains(newText.toLowerCase())||singalnotes.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filtered_list.add(singalnotes);
            }
        }
        notesListAdapter.filteredList(filtered_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            if(resultCode== MainActivity.RESULT_OK){
                Notes new_notes= (Notes) data.getSerializableExtra("Note");
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        } else if (requestCode==102) {
            if(resultCode== Activity.RESULT_OK){
                Notes new_note= (Notes) data.getSerializableExtra("Note");
                database.mainDAO().update(new_note.getID(),new_note.getTitle(),new_note.getNotes());
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
            
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        notesListAdapter=new NotesListAdapter(MainActivity.this,notes,notesClickedListener);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(notesListAdapter);
    }
    private final NotesClickedListener notesClickedListener=new NotesClickedListener() {
        @Override
        public void onClicked(Notes notes) {
            Intent intent=new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("old_note",notes);
            startActivityForResult(intent,102);

        }

        @Override
        public void onLongClicked(Notes notes, CardView cardView) {
                selectedNote=new Notes();
                selectedNote=notes;
                showPopUp(cardView);
        }
    };

    private void showPopUp(CardView cardView) {
        PopupMenu popupMenu=new PopupMenu(this,cardView);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
            int itemId= item.getItemId();
        if(itemId==R.id.pins) {
            if (selectedNote.isPinned()) {
                database.mainDAO().pin(selectedNote.getID(), false);
                Toast.makeText(this, "Unpined!..", Toast.LENGTH_SHORT).show();
            } else {
                database.mainDAO().pin(selectedNote.getID(), true);
                Toast.makeText(this, "Pinned....", Toast.LENGTH_SHORT).show();
            }
            notes.clear();
            notes.addAll(database.mainDAO().getAll());
            notesListAdapter.notifyDataSetChanged();
            return true;
        } else if (itemId==R.id.delete) {
            database.mainDAO().delete(selectedNote);
            notes.clear();
            notes.addAll(database.mainDAO().getAll());
            notesListAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Notes Deleted!..", Toast.LENGTH_SHORT).show();
            return true;


        }else {
            return false;
        }


    }
}