package com.todotrek.feature.todo.ui.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.todotrek.R
import com.todotrek.basedesign.WhiteBackground
import com.todotrek.feature.todo.ToDoViewModel
import com.todotrek.feature.todo.model.ToDoModel
import com.todotrek.feature.todo.ui.EmptyView
import com.todotrek.feature.todo.ui.ErrorDialog
import com.todotrek.feature.todo.ui.ExtendedFabView
import com.todotrek.feature.todo.ui.LoadingView
import com.todotrek.feature.todo.util.AddToDoAction
import com.todotrek.feature.todo.util.AddToDoUiState
import com.todotrek.feature.todo.util.ToDoListUiState
import com.todotrek.feature.todo.util.convertLongToTime
import kotlinx.coroutines.launch

@Composable
fun ToDoListRoute(toDoViewModel: ToDoViewModel, navigateToAddToDo: () -> Unit) {
    ToDoListScreen(viewModel = toDoViewModel, navigateToAddToDo = navigateToAddToDo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    viewModel: ToDoViewModel,
    navigateToAddToDo: () -> Unit
) {
    val toDoListUiState by viewModel.toDoListUiState.collectAsStateWithLifecycle()
    val addToDoUiState by viewModel.addToDoUiStateFlow.collectAsStateWithLifecycle()

    val toDoData = (toDoListUiState as? ToDoListUiState.Success)?.data ?: listOf()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val errorDialogState = rememberSaveable { mutableStateOf(false) }

    val expandedFab = rememberSaveable { mutableStateOf(true) }

    val listState = rememberLazyListState()

    val snackBarState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = snackBarState.value) {
        if (snackBarState.value) {
            scope.launch {
                snackBarHostState.showSnackbar("ToDo Added Successfully")
            }
        }
    }

    val expandedFabState = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(key1 = expandedFabState.value) {
        expandedFab.value = expandedFabState.value
    }

    val resetAddToDoUiState = remember { mutableStateOf(false) }

    LaunchedEffect(key1 = resetAddToDoUiState.value) {
        if (resetAddToDoUiState.value) {
            scope.launch {
                viewModel.actionFlow.emit(AddToDoAction.None)
            }
        }
    }

    val searchQueryState = rememberSaveable {
        mutableStateOf("")
    }

    val searchButtonState = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = searchButtonState.value) {
        if (searchButtonState.value) {
            searchButtonState.value = false
            scope.launch {
                viewModel.searchData(searchQueryState.value)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(scrollBehavior = scrollBehavior)
        },
        floatingActionButton = {
            ExtendedFabView(expandedFab.value) {
                navigateToAddToDo()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        content = { innerPadding ->
            ContentView(
                innerPadding = innerPadding,
                toDoListUiState = toDoListUiState,
                listState = listState,
                toDoData = toDoData,
                searchQuery = searchQueryState.value,
                onSearchQueryChange = {
                    searchQueryState.value = it
                    searchButtonState.value = true
                },
                onSearchClear = {
                    if (searchQueryState.value.isNotBlank()) {
                        searchQueryState.value = ""
                        searchButtonState.value = true
                    }
                },
                onSearchClicked = {
                    if (searchQueryState.value.isNotBlank()) {
                        searchButtonState.value = true
                    }
                }
            )

            when (addToDoUiState) {
                AddToDoUiState.Error -> {
                    errorDialogState.value = true
                }

                AddToDoUiState.Success -> {
                    snackBarState.value = true
                    resetAddToDoUiState.value = true
                }

                AddToDoUiState.Loading,
                AddToDoUiState.None -> {
                    errorDialogState.value = false
                }
            }

            if (errorDialogState.value) {
                ErrorDialog {
                    resetAddToDoUiState.value = true
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(scrollBehavior: TopAppBarScrollBehavior) {
    MediumTopAppBar(
        modifier = Modifier
            .shadow(elevation = 8.dp),
        title = {
            Text(
                stringResource(R.string.your_todos),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = WhiteBackground,
            scrolledContainerColor = WhiteBackground
        ),
        actions = {

        }
    )
}

@Composable
fun ContentView(
    innerPadding: PaddingValues,
    toDoListUiState: ToDoListUiState<ToDoModel>,
    listState: LazyListState,
    toDoData: List<ToDoModel>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearchClear: () -> Unit,
    onSearchClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .background(Color.LightGray)
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        when (toDoListUiState) {
            is ToDoListUiState.Error -> {
                EmptyView()
            }

            ToDoListUiState.Loading -> {
                LoadingView()
            }

            is ToDoListUiState.Success -> {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    content = {
                        item {
                            SearchBox(
                                searchQuery = searchQuery,
                                onSearchQueryChange = onSearchQueryChange,
                                placeHolder = stringResource(R.string.search_todos),
                                onSearchClear = onSearchClear,
                                onSearchClicked = onSearchClicked
                            )
                        }
                        items(toDoData.size) {
                            ToDoItemView(toDoItem = toDoData[it])
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ToDoItemView(toDoItem: ToDoModel) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = WhiteBackground
        ),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 12.dp)
                .background(color = WhiteBackground)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = toDoItem.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                lineHeight = 1.sp,
                maxLines = 1
            )
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "${convertLongToTime(toDoItem.createdAt)} - ",
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                Text(
                    text = toDoItem.description,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Start,
                    maxLines = 2
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToDoListPreview() {
    ToDoItemView(toDoItem = ToDoModel("test", "Description test"))
}