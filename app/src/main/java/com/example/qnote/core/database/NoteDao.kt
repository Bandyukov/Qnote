package com.example.qnote.core.database

import androidx.room.*
import com.example.qnote.core.models.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes_table")
    suspend fun getAllNotes() : List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM NOTES_TABLE WHERE uniqueId==:noteId")
    suspend fun getNoteById(noteId: Int) : Note
}