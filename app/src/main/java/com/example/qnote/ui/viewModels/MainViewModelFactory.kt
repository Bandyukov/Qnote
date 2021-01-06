package com.example.qnote.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.qnote.core.database.NoteDatabase
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val database: NoteDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(database) as T
        throw IllegalArgumentException("Exception")
    }
}