package vistas

import kotlinx.coroutines.flow.*

class Notes(val text:String){}

object NoteRepository{

    private val _notes = MutableStateFlow(listOf(Notes("Hello"), Notes("Hola"), Notes("QUE TAL")))
    val notes: StateFlow<List<Notes>> = _notes.asStateFlow()

    fun addNote(notes: Notes) {
        val current =  _notes.value.toMutableList()
        current.add(notes)
        _notes.value = current
    }
}


