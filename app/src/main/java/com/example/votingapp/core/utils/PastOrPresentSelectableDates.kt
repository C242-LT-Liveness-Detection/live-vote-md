package com.example.votingapp.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates : SelectableDates {
    @RequiresApi(Build.VERSION_CODES.O)
    @ExperimentalMaterial3Api
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        val today = LocalDate.now()
        val selectedDate = LocalDate.ofEpochDay(utcTimeMillis / (24 * 60 * 60 * 1000))
        return selectedDate.isAfter(today)
    }


}