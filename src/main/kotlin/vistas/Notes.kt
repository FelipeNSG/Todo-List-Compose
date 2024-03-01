package vistas

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

enum class Filter {
    ALL,
    COMPLETED,
    UNCOMPLETED,
    WORD_SEARCH
}

data class Notes(var text: String, var checkbox: Boolean = false, val myUuid: String = UUID.randomUUID().toString())

object NoteRepository {
    private var filterActually: Filter = Filter.ALL
    private val scope = CoroutineScope(Dispatchers.IO)

    private var cache = mutableListOf<Notes>()
    private var db = mutableListOf<Notes>()

    private val _notes = MutableSharedFlow<List<Notes>>()
    val notes: Flow<List<Notes>> = _notes

    private fun updateCache() {
        when (filterActually) {
            Filter.ALL -> {
                cache.clear()
                cache.addAll(db)
            }

            Filter.COMPLETED -> {
                cache.clear()
                cache.addAll(db.filter { it.checkbox }.toMutableList())
            }

            else -> {
                cache.clear()
                cache.addAll(db.filter { !it.checkbox }.toMutableList())
            }
        }
    }

    private fun searWord(query: String) {
        filterActually = Filter.WORD_SEARCH
        cache.clear()
        cache.addAll(db.filter { it.text.lowercase().contains(query.lowercase()) }.toMutableList())
    }

    fun addNote(notes: Notes) {
        db.add(notes)
        updateCache()
        updateFlow()
    }

    fun deleteNote(uuid: String) {

        db.removeIf { it.myUuid == uuid }
        updateCache()
        updateFlow()
    }

    fun editNote(uuid: String, text: String) {
        db.firstOrNull {
            it.myUuid == uuid
        }?.let { nota ->
            val index: Int = db.indexOf(nota)
            val newNote = nota.copy(text = text)
            db[index] = newNote

            updateCache()
            updateFlow()
        } ?: run {
            println("editCheck uuid: $uuid no encontrado")
        }
    }

    //buscar la nota y guardarla en una variable
    //obtener el indice de esa nota
    //hacer una copia de la nota original y modificar su valor
    //remplazar la nota con el indice encontrado

    fun editCheckBox(uuid: String, check: Boolean) {
        db.firstOrNull {
            it.myUuid == uuid
        }?.let { nota ->
            val index: Int = db.indexOf(nota)
            val newNote = nota.copy(checkbox = check)
            db[index] = newNote
            updateCache()
            //println("Esto es el cache: $db")
            updateFlow()
        } ?: run {
            println("editCheck uuid: $uuid no encontrado")
        }
        //db[index] = db[index].copy(checkbox = check)
    }

    fun showAll() {
        filterActually = Filter.ALL
        updateCache()
        updateFlow()
    }

    fun completedTask() {
        filterActually = Filter.COMPLETED
        updateCache()
        updateFlow()
    }

    fun uncompletedTask() {
        filterActually = Filter.UNCOMPLETED
        updateCache()
        updateFlow()
    }

    fun performSearch(query: String) {
        searWord(query)
        updateFlow()
    }

    private fun updateFlow() {
        scope.launch {
            _notes.emit(cache.toList())
        }
    }

}

