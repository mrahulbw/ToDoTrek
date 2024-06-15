package com.todotrek.feature.todo.util

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertLongToTime(time: Long) =
    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(time))