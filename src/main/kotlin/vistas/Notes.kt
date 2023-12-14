package vistas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class Notes(val text:String){}

object NoteRepository{

     val _notes:MutableStateFlow<MutableList<Notes>> = MutableStateFlow(mutableListOf(Notes("Hello"), Notes("Hola"), Notes("QUE TAL")))
    val notes: List<Notes> = _notes.value
    val scope = CoroutineScope(Dispatchers.IO)

    fun addNote (notes: Notes){
        _notes.value.add(notes)
        scope.launch {
            _notes.collect{
                println(it.size)
            }
        }
    }
}


