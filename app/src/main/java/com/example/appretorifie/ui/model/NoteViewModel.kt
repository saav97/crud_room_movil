package com.example.appretorifie.ui.model
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import com.example.appretorifie.core.TextFieldState
import com.example.appretorifie.db.NotesDatabase
import com.example.appretorifie.db.model.Note
import com.example.appretorifie.repository.NotesRepository
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch


class NoteViewModel(application: Application ):ViewModel() {
    private val _text = mutableStateOf(TextFieldState())
    val text: State<TextFieldState> = _text

    private var currentID: Int? = null

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

    private fun load(id:Int?){
        viewModelScope.launch {
            if(id != null){
                repository.findById(id).also{ note ->
                    currentID = note.id
                    _text.value = text.value.copy(
                        text = note.text
                    )
                }
            }else{
                currentID = null
                _text.value = text.value.copy(
                    text = "text"
                )
            }

        }
    }

}