package com.todotrek.feature.todo.ui.todolist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todotrek.basedesign.Cranberry
import com.todotrek.basedesign.WhiteBackground

@Composable
fun SearchBox(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    placeHolder: String,
    onSearchClear: () -> Unit,
    onSearchClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = WhiteBackground)
            .border(
                BorderStroke(
                    0.1.dp,
                    SolidColor(WhiteBackground)
                ),
                RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .background(WhiteBackground)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp),
            value = searchQuery,
            onValueChange = {
                onSearchQueryChange(it)
            },
            placeholder = {
                Text(
                    text = placeHolder,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Cranberry,
                        modifier = Modifier.size(22.dp)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    onSearchClear()
                }, enabled = searchQuery.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked()
                }
            ),
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
        )
        SearchBarDivider(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 58.dp)
        )
    }
}

@Composable
fun SearchBarDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        modifier = modifier
            .width(1.dp),
        color = MaterialTheme.colorScheme.onSurface,
        thickness = 20.dp
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    SearchBox(
        searchQuery = "",
        onSearchQueryChange = {},
        placeHolder = "Search Here",
        onSearchClear = {},
        onSearchClicked = {},
    )
}