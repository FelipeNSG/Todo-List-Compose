package vistas

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import java.io.File


@Composable
fun Title() {
    Alignment.Center
    Text(
        text = "TODO LIST",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 30.sp,
        textAlign = TextAlign.Center,
        color = if (!JetRedditThemeSettings.isInDarkTheme.value) {
            Color.Black
        } else {
            Color.White
        }
    )
}

@Composable
fun TitleEmpty() {
    Alignment.Center
    Text(
        text = "Empty...",
        modifier = Modifier.fillMaxWidth()
            .height(35.dp),
        fontSize = 20.sp,
        fontStyle = FontStyle.Italic,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        color = if (!JetRedditThemeSettings.isInDarkTheme.value) {
            Color.Black
        } else {
            Color.White
        }
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
        var textButton by rememberSaveable { mutableStateOf("ALL") }
        var text by rememberSaveable { mutableStateOf("") }
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                NoteRepository.performSearch(it)
            },
            placeholder = { Text(text = "Search note...") },
            trailingIcon = { Lens() },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(700.dp),
            colors = if (!JetRedditThemeSettings.isInDarkTheme.value) {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.primary,
                )
            } else {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                )
            }
        )

        Row()
        {
            DropdownMenu(
                expanded = optionsList,
                onDismissRequest = { optionsList = false }, modifier = Modifier.width(115.dp)
            ) {
                DropdownMenuItem(onClick = {
                    NoteRepository.showAll()
                    optionsList = false
                    textButton="ALL"
                }) {
                    Text("ALL")
                }
                DropdownMenuItem(onClick = {
                    NoteRepository.completedTask()
                    optionsList = false
                    textButton="Complete"

                }) {
                    Text("Complete")
                }
                DropdownMenuItem(onClick = {
                    NoteRepository.uncompletedTask()
                    optionsList = false
                    textButton="Incomplete"
                }) {
                    Text("Incomplete")
                }
            }
        }

        //Button Filter
        Button(
            onClick = { optionsList = !optionsList },
            modifier = Modifier.height(54.dp)
                .width(140.dp)
                .padding(start = 4.dp)

        ) {
            Text(text = textButton)
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
            Icon(
                imageVector = Icons.Outlined.WbSunny,
                contentDescription = null,
                tint = Color.White,
            )
        } else {
            Icon(
                imageVector = Icons.Default.Bedtime,
                contentDescription = null,
                tint = Color.White,
            )
        }
    }
}
@Composable
fun Bottom(){
    Row(modifier = Modifier.fillMaxSize()){}
}

@Composable
fun ButtonAdd() {
    var textOfNote by remember { mutableStateOf("") }

    val openDialog = remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 1250.dp)

    )
    {
        IconButton(
            onClick = { openDialog.value = true },
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
                .size(50.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "content description", tint = Color.White)
        }
    }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
                textOfNote=""
                               },
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
                    OutlinedTextField(
                        value = textOfNote,
                        textStyle =  TextStyle(fontSize = 18.sp, fontStyle = FontStyle.Normal, color = MaterialTheme.colors.onBackground),
                        placeholder = { Text("Input your note...") },
                        onValueChange = { textOfNote = it },
                        modifier = Modifier.width(400.dp),
                        colors = if (!JetRedditThemeSettings.isInDarkTheme.value) {
                            TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colors.primary,
                                unfocusedBorderColor = MaterialTheme.colors.primary,
                            )
                        } else {
                            TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colors.onPrimary,
                                unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                            )
                        }
                    )
                }
            },

            dismissButton = {
                Button(
                    onClick = { openDialog.value = false
                              textOfNote=""},
                    modifier = Modifier.padding(end = 220.dp, top = 100.dp)

                ) {
                    Text(text = "CANCEL")
                }
            },

            confirmButton = {
                Button(
                    onClick = {
                        NoteRepository.addNote(Notes(textOfNote, checkbox = false))
                        textOfNote=""
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
        tint = MaterialTheme.colors.primary,
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
fun IconEdit() {
    Icon(
        imageVector = Icons.Outlined.Edit,
        contentDescription = null,
        tint = MaterialTheme.colors.onBackground,
    )
}

@Composable
fun ButtonEdit(textNote: String, uuid: String) {

    var text by rememberSaveable { mutableStateOf(textNote) }
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
                    OutlinedTextField(
                        value = text,
                        textStyle =  TextStyle(fontSize = 18.sp, fontStyle = FontStyle.Normal, color = MaterialTheme.colors.onBackground),
                        onValueChange = { text = it },
                        modifier = Modifier.width(400.dp),
                        colors =  TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = MaterialTheme.colors.primary,
                                unfocusedBorderColor = MaterialTheme.colors.primary,
                            )
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
                        NoteRepository.editNote(uuid, text)
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
    Icon(
        imageVector = Icons.Outlined.Delete,
        contentDescription = null,
        tint = MaterialTheme.colors.onBackground,
    )
}

@Composable
fun ButtonDelete(uuid: String) {
    IconButton(onClick = {
        NoteRepository.deleteNote(uuid)

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
            .height(265.dp)
            .fillMaxSize()
            .padding(top = 35.dp)
    )
}

@Composable
fun CreateNote(note: Notes) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 10.dp)

    ) {

        Checkbox(
            checked = note.checkbox,
            onCheckedChange = { isChecked ->
                NoteRepository.editCheckBox(note.myUuid, isChecked)
            },
        )
        if (note.checkbox) {
            Text(
                "${note.text} ",
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colors.onBackground,
                fontWeight = FontWeight.SemiBold,
            )
        } else {
            Text("${note.text} ", color = MaterialTheme.colors.onBackground, fontWeight = FontWeight.SemiBold)
        }
        ButtonEdit(note.text, note.myUuid)
        ButtonDelete(note.myUuid)
    }
    Divider(
        startIndent = 0.dp,
        thickness = 1.dp,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxWidth(0.4f)
    )
}

@Composable
fun AddNote(notes: List<Notes>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        items(notes.size) {
            CreateNote(notes[it])
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
            modifier = Modifier.background(MaterialTheme.colors.background),
        ) {
            Spacer(Modifier.height(60.dp))
            Title()
            SearchBar()
            if (notes.value.isEmpty()) {
                ImageEmpty()
                TitleEmpty()
            } else {
                AddNote(notes.value)
            }
            ButtonAdd ()
            Bottom()
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication, state = WindowState(
            size = DpSize(1920.dp, 1080.dp),


        ),
        title = "TO-DO-LIST"

    ) {
        MainScreen()
    }
}