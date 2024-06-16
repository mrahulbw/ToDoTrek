package com.todotrek.feature.todo.ui.addtodo

import androidx.compose.runtime.Composable
import com.todotrek.feature.todo.ToDoViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.todotrek.R
import com.todotrek.feature.todo.model.AddToDoModel
import com.todotrek.feature.todo.util.AddToDoAction
import com.todotrek.feature.todo.util.AddToDoUiState
import kotlinx.coroutines.delay

@Composable
fun AddToDoRoute(toDoViewModel: ToDoViewModel, navigateBack: () -> Unit) {
    AddToDoScreen(viewModel = toDoViewModel, navigateBack = navigateBack)
}

@Composable
fun AddToDoScreen(
    viewModel: ToDoViewModel,
    navigateBack: () -> Unit
) {
    val addToDoUiState by viewModel.addToDoUiStateFlow.collectAsStateWithLifecycle()

    val loadingDialogState = rememberSaveable { mutableStateOf<Boolean?>(null) }

    var title by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    var description by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    var addButtonState by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = addButtonState) {
        if (addButtonState) {
            addButtonState = false

            viewModel.actionFlow.emit(
                AddToDoAction.Add(
                    AddToDoModel(title = title.text.trim(), description = description.text.trim())
                )
            )
        }
    }

    LaunchedEffect(key1 = loadingDialogState.value) {
        if (loadingDialogState.value?.not() == false) {
            delay(1000)
            navigateBack()
        }
    }

    Scaffold(
        topBar = {
            TopBar(navigateBack)
        },
        content = { innerPadding ->
            ContentView(
                innerPadding = innerPadding,
                title = title,
                description = description,
                onTitleChange = {
                    title = it
                },
                onDescriptionChange = {
                    description = it
                },
                onAddClick = {
                    addButtonState = true
                }
            )

            when (addToDoUiState) {
                AddToDoUiState.None -> {
                }

                AddToDoUiState.Error,
                AddToDoUiState.Success -> {
                    loadingDialogState.value = false
                }

                AddToDoUiState.Loading -> {
                    loadingDialogState.value = true
                }
            }

            if (loadingDialogState.value == true) {
                LoadingDialog {}
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navigateBack: () -> Unit) {
    TopAppBar(
        modifier = Modifier
            .shadow(elevation = 8.dp),
        title = {
            Text(
                stringResource(R.string.add_todo),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
            )
        },
        navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.click_to_go_back)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFFFFFF),
            scrolledContainerColor = Color(0xFFFFFFFF)
        )
    )
}

@Composable
fun ContentView(
    innerPadding: PaddingValues,
    title: TextFieldValue,
    onTitleChange: (TextFieldValue) -> Unit,
    description: TextFieldValue,
    onDescriptionChange: (TextFieldValue) -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier
                .padding(top = 50.dp, start = 25.dp, end = 25.dp)
                .fillMaxWidth(),
            value = title,
            label = { Text(text = stringResource(R.string.title)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                onTitleChange(
                    TextFieldValue(
                        text = it.text,
                        selection = it.selection,
                        composition = it.composition
                    )
                )
            },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(top = 30.dp, start = 25.dp, end = 25.dp)
                .fillMaxWidth()
                .heightIn(min = 100.dp),
            value = description,
            label = { Text(text = stringResource(R.string.description)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = {
                onDescriptionChange(
                    TextFieldValue(
                        text = it.text,
                        selection = it.selection,
                        composition = it.composition
                    )
                )
            },
            maxLines = 2
        )

        ElevatedButton(
            modifier = Modifier
                .padding(top = 40.dp, start = 25.dp, end = 25.dp)
                .fillMaxWidth()
                .height(60.dp),
            onClick = onAddClick,
            enabled = title.text.trim().isNotBlank()
        ) {
            Text(
                stringResource(id = R.string.add_todo),
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddToDoPreview() {
}