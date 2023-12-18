package vistas

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

data class Notes(var text: String, val idValue: String = UUID.randomUUID().toString())

object NoteRepository {

    val scope = CoroutineScope(Dispatchers.IO)

    private var cache = mutableListOf<Notes>()
    private val _notes = MutableSharedFlow<List<Notes>>()
    val notes: Flow<List<Notes>> = _notes

    fun addNote(notes: Notes) {
        cache.add(notes)
        updateFlow()
    }

    fun deleteNote(textNote: String, id: String, index: Int) {
        cache.removeAt(index)
        updateFlow()
    }

    fun editNote(index: Int, text: String) {
        cache[index] = cache[index].copy(text = text)
        updateFlow()
    }

    fun updateFlow () {

        scope.launch {
            _notes.emit(cache.toList())
        }

    }

}



