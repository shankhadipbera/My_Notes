package com.shankha.mynotes.Database;


import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.shankha.mynotes.Models.Notes;

import java.util.List;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert(Notes notes);

    @Query("SELECT * FROM Notes ORDER BY ID DESC")
    List<Notes> getAll();

    @Query("UPDATE Notes SET title = :title, notes = :notes WHERE ID= :id")
    void update(int id, String title, String notes);

    @Delete
    void delete (Notes notes);
    @Query("UPDATE Notes SET pinned = :pin WHERE ID = :id")
    void pin(int id, boolean pin);

}
