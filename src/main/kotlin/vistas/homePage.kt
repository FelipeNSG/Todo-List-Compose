package vistas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.flow.MutableStateFlow

import java.io.File


@Composable
fun Title() {
    Alignment.Center
    Text(
        text = "TODO LIST",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 30.sp,
        textAlign = TextAlign.Center

    )
}

@Composable
fun TitleEmpty() {
    Alignment.Center
    Text(
        text = "Empty...",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun SearchBar() {

    var optionsList by remember { mutableStateOf(false) }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,

        )
    {
        var text by rememberSaveable { mutableStateOf("") }
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text(text = "Search note...") },
            trailingIcon = { Lens() },
            modifier = Modifier.width(700.dp)
                    .border(width = 0.5.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
            )

        Row()
        {
            DropdownMenu(
                expanded = optionsList,
                onDismissRequest = { optionsList = false }, modifier = Modifier.width(115.dp)
            ) {
                DropdownMenuItem(onClick = {}) {
                    Text("ALL")
                }
                DropdownMenuItem(onClick = {}) {
                    Text("Complete")
                }
                DropdownMenuItem(onClick = {}) {
                    Text("Incomplete")
                }
            }
        }

        //Button Filter
        Button(
            onClick = { optionsList = !optionsList },
            modifier = Modifier.height(54.dp)
                .width(110.dp)
                .padding(start = 4.dp)

        ) {
            Text(text = "ALL")
            if (!optionsList) {
                KeyboardArrowDown()
            } else {
                KeyBoardArrowUp()
            }

        }

        ButtonTheme(Modifier.padding(start = 4.dp))
    }

}

@Composable
fun ButtonTheme(modifier: Modifier) {
    val changeMode = remember {
        mutableStateOf(false)
    }
    Button(
        onClick = {
            changeMode.value = !changeMode.value
            JetRedditThemeSettings.isInDarkTheme.value = !JetRedditThemeSettings.isInDarkTheme.value

        },
        modifier.width(50.dp)
            .height(54.dp)
    ) {

        if (changeMode.value) {
            IconSun()
        } else {
            IconMoon()
        }
    }


}


@Composable
fun ButtonAdd(onApplyButtonClick: (String) -> Unit) {
    var text by rememberSaveable { mutableStateOf("") }

    val openDialog = remember {
        mutableStateOf(false)
    }

    var createNote = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(start = 1250.dp, top = 300.dp)

    )
    {
        IconButton(

            onClick = { openDialog.value = true },
            modifier = Modifier
                .size(50.dp)
                .border(0.dp, Color.Magenta, shape = CircleShape)
                .background(color = Color.Magenta, shape = CircleShape)

        ) {
            Icon(Icons.Default.Add, contentDescription = "content description", tint = Color.White)
        }
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "NEW NOTE")

                }

            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = text,
                        placeholder = { Text("Input your note...") },
                        onValueChange = { text = it },
                        modifier = Modifier.width(400.dp)
                            .border(width = 0.5.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))


                    )
                }
            },

            dismissButton = {
                Button(
                    onClick = { openDialog.value = false },
                    modifier = Modifier.padding(end = 220.dp, top = 100.dp)

                ) {
                    Text(text = "CANCEL")
                }
            },

            confirmButton = {
                Button(
                    onClick = {
                        NoteRepository.addNote(Notes(text))
                        onApplyButtonClick(text)
                        openDialog.value = false
                    },
                    modifier = Modifier.padding(end = 82.dp, top = 100.dp)
                ) {
                    Text(text = "APPLY")
                }
            },
        )

    }

}

//  Icons
@Composable
fun Lens() {

    Icon(
        imageVector = Icons.Default.Search,
        contentDescription = null,
        tint = Color.Black,
    )

}

@Composable
fun KeyboardArrowDown() {

    Icon(
        imageVector = Icons.Default.KeyboardArrowDown,
        contentDescription = null,
        tint = Color.White
    )

}

@Composable
fun KeyBoardArrowUp() {

    Icon(
        imageVector = Icons.Default.KeyboardArrowUp,
        contentDescription = null,
        tint = Color.White
    )

}

@Composable
fun IconMoon() {

    AsyncImage(
        load = { loadImageBitmap(File("src/main/resources/moon.png")) },
        painterFor = { remember { BitmapPainter(it) } },
        contentDescription = "Sample",
    )

}

@Composable
fun IconSun() {

    AsyncImage(
        load = { loadImageBitmap(File("src/main/resources/sun.png")) },
        painterFor = { remember { BitmapPainter(it) } },
        contentDescription = "Sun",
    )

}

@Composable
fun IconEdit() {
    AsyncImage(
        load = { loadImageBitmap(File("src/main/resources/edit.png")) },
        painterFor = { remember { BitmapPainter(it) } },
        contentDescription = "edit",
    )
}

@Composable
fun ButtonEdit(index: Int) {

    var text by rememberSaveable { mutableStateOf("") }
    val openDialog = remember {
        mutableStateOf(false)
    }

    IconButton(onClick = {
        openDialog.value = true
    })

    {
        IconEdit()
    }

    if (openDialog.value) {

        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "EDIT NOTE")
                }

            },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.width(400.dp)
                            .border(width = 0.5.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
                    )
                }
            },

            dismissButton = {
                Button(
                    onClick = { openDialog.value = false },
                    modifier = Modifier.padding(end = 220.dp, top = 100.dp)

                ) {
                    Text(text = "CANCEL")
                }
            },

            confirmButton = {
                Button(
                    onClick = {
                        NoteRepository.editNote(index, text)
                        openDialog.value = false


                    },
                    modifier = Modifier.padding(end = 82.dp, top = 100.dp)
                ) {
                    Text(text = "APPLY")
                }
            },
        )

    }
}

@Composable
fun IconDelete() {
    AsyncImage(
        load = { loadImageBitmap(File("src/main/resources/delete.png")) },
        painterFor = { remember { BitmapPainter(it) } },
        contentDescription = "Delete",
    )
}

@Composable
fun ButtonDelete(textNote: String, id: String, index: Int) {
    IconButton(onClick = {
        NoteRepository.deleteNote(textNote, id, index)

    }) {
        IconDelete()
    }
}


@Composable
fun ImageEmpty() {
    AsyncImage(
        load = { loadImageBitmap(File("src/main/resources/detective.png")) },
        painterFor = { remember { BitmapPainter(it) } },
        contentDescription = "Sample",
        modifier = Modifier
            .height(300.dp)
            .fillMaxSize()
            .padding(top = 35.dp)
    )
}


@Composable
fun CreateNote(note: Notes, index: Int) {
    val checked = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {

        Checkbox(
            checked = checked.value,
            onCheckedChange = { isChecked -> checked.value = isChecked },
        )
        if (checked.value) {
            Text("${note.text} ", textDecoration = TextDecoration.LineThrough)
        } else {
            Text("${note.text} ")
        }

        ButtonEdit(index)
        ButtonDelete(note.text, note.idValue, index)

    }
}


@Composable
fun CreateEditNote(note: Notes, index: Int) {
    val checked = remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {

        Checkbox(
            checked = checked.value,
            onCheckedChange = { isChecked -> checked.value = isChecked },
        )
        if (checked.value) {
            Text("${note.text} ", textDecoration = TextDecoration.LineThrough)
        } else {
            Text("${note.text} ")
        }

        ButtonEdit(index)
        ButtonDelete(note.text, note.idValue, index)

    }
}

@Composable
fun AddNote(notes: List<Notes>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    )
    {
        items(notes.size) {
            CreateNote(notes[it], it)
        }


    }
}


//Logic Code

@Composable
fun MainScreen() {

    val notes = NoteRepository.notes.collectAsState(
        emptyList()
    )
    ToDoListTheme {
        Column(
            modifier = Modifier.padding(60.dp),

            ) {
            Title()
            SearchBar()
            if (notes.value.isEmpty()) {
                ImageEmpty()
                TitleEmpty()
            } else {
                AddNote(notes.value)
            }
            ButtonAdd {

            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MainScreen()
    }
}

