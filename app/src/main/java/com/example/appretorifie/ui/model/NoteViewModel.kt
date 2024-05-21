package com.example.appretorifie.ui.model
import android.app.Application
import androidx.lifecycle.LiveData
import com.example.appretorifie.db.NotesDatabase
import com.example.appretorifie.db.model.Note
import com.example.appretorifie.repository.NotesRepository

class NoteViewModel(application: Application) {

    private var repository: NotesRepository
        get() {
            TODO()
        }
    var all: LiveData<List<Note>>
        get() {
            TODO()
        }

    init {
        val db = NotesDatabase.getInstance(application)
        val dao = db.notesDao()
        repository = NotesRepository(dao)

        all = repository.getNotes()
    }

}