package com.shankha.mynotes;

import androidx.cardview.widget.CardView;

import com.shankha.mynotes.Models.Notes;

public interface NotesClickedListener {
    void onClicked(Notes notes);
    void onLongClicked(Notes notes, CardView cardView);
}
