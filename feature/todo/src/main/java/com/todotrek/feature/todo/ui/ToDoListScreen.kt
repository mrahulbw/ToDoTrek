package com.todotrek.feature.todo.ui

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.todotrek.convertor.R
import com.todotrek.feature.todo.ToDoViewModel
import com.todotrek.feature.todo.model.ToDoModel
import com.todotrek.feature.todo.util.ToDoListUiState
import com.todotrek.feature.todo.util.convertLongToTime

@Composable
fun ToDoListRoute(navigateToAddToDo: () -> Unit) {
    ToDoListScreen(navigateToAddToDo = navigateToAddToDo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    viewModel: ToDoViewModel = hiltViewModel(),
    navigateToAddToDo: () -> Unit,
) {
    val toDoListUiState by viewModel.toDoListUiState.collectAsStateWithLifecycle()

    val toDoData = (toDoListUiState as? ToDoListUiState.Success)?.data ?: listOf()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val expandedFab = remember { mutableStateOf(true) }

    val listState = rememberLazyListState()

    val expandedFabState = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    LaunchedEffect(key1 = expandedFabState.value) {
        expandedFab.value = expandedFabState.value
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBar(scrollBehavior)
        },
        floatingActionButton = {
            ExtendedFabView(expandedFab.value) {
                navigateToAddToDo()
            }
        },
        content = { innerPadding ->
            ContentView(
                innerPadding,
                toDoListUiState,
                listState,
                toDoData,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
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
            containerColor = Color(0xFFFFFFFF),
            scrolledContainerColor = Color(0xFFFFFFFF)
        )
    )
}

@Composable
fun ContentView(
    innerPadding: PaddingValues,
    toDoListUiState: ToDoListUiState,
    listState: LazyListState,
    toDoData: List<ToDoModel>
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
            containerColor = Color(0xFFF7F2F9)
        ),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 12.dp)
                .background(color = Color(0xFFF7F2F9))
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
                    lineHeight = 1.sp,
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
fun ConvertorPreview() {
    ToDoItemView(toDoItem = ToDoModel("test", "Description test"))
}