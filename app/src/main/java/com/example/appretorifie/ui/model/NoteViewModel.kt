package com.example.appretorifie.ui.model
import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import com.example.appretorifie.core.TextFieldState
import com.example.appretorifie.db.NotesDatabase
import com.example.appretorifie.db.model.Note
import com.example.appretorifie.repository.NotesRepository
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.Date


class NoteViewModel(application: Application ):ViewModel() {
    private val _text = mutableStateOf(TextFieldState())
    val text: State<TextFieldState> = _text
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var openDialog by mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<Event>()
    val eventFlow = _eventFlow.asSharedFlow()



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

    fun onEvent(event: Event){
        when (event){
            is Event.setText -> {
                _text.value = text.value.copy(
                    text = event.text
                )
            }

            is Event.Save -> {
                if(currentID != null){
                    repository.update(Note(currentID, text.value.text, Date()))
                }else{
                    repository.insert(Note(null, text.value.text, Date()))
                }

                openDialog = false
                coroutineScope.launch (Dispatchers.IO) {
                    _eventFlow.emit(Event.Save)
                }
            }

            is Event.OpenDialog -> {
                openDialog = true
            }
            is Event.CloseDialog -> {
                openDialog = false
            }
            is Event.Load -> {
                load(event.id)
                openDialog = true
            }

            is Event.Delete -> {
                event.id?.let {repository.delete(it)}
            }



        }
    }

}