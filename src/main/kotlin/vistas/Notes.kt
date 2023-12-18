package vistas

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class Notes(var text: String, val idValue: String = UUID.randomUUID().toString()) {}

object NoteRepository {

    val scope = CoroutineScope(Dispatchers.IO)

    var cache = mutableListOf<Notes>()
    private val _notes = MutableSharedFlow<List<Notes>>()
    val notes: Flow<List<Notes>> = _notes

    fun addNote(notes: Notes) {
        val current = cache.toMutableList()
        current.add(notes)
        cache = current.toMutableList()
        updateFlow()
    }

    fun deleteNote(textNote: String, id: String, index: Int) {
        val current = cache.toMutableList()
        current.removeAt(index)
        cache = current.toMutableList()
        updateFlow()
    }

    fun editNote(index: Int, text: String) {
        val current = cache.toMutableList()
        current[index].text = text
        cache = current.toMutableList()
        updateFlow()
    }


    fun updateFlow () {

        scope.launch { _notes.emit(cache.toMutableList()) }

    }

}



