package com.example.qnote.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.qnote.core.database.NoteDatabase
import com.example.qnote.core.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewModel(private val database: NoteDatabase) : ViewModel() {

    private val job: Job
    private val uiScope: CoroutineScope

    private val _listOfNotes_ : MutableLiveData<List<Note>>
    val listOfNotes : LiveData<List<Note>>
    get() = _listOfNotes_

    private val _note_ = MutableLiveData<Note>()
    val note : LiveData<Note> get() = _note_

    init {
        job = Job()
        uiScope = CoroutineScope(Dispatchers.Main + job)
        _listOfNotes_ = MutableLiveData<List<Note>>()
    }

//    fun addNote(notes: ArrayList<Note>) {
//        Log.i("res", "try to add array notes to view Model")
//        val list = _listOfNotes_.value
//        if (list == null) {
//            Log.i("res", "list is null")
//            _listOfNotes_.value = notes
//            Log.i("res", "array notes added to viewModel successfully _1_")
//        }
//        else {
//            Log.i("res", "list is not null")
//            list.clear()
//            list.addAll(notes)
//
//            _listOfNotes_.value = list
//            Log.i("res", "array notes added to viewModel successfully _2_")
//        }
//
//        for (now in _listOfNotes_.value!!){
//            Log.i("res", "${now.title} ")
//        }
//    }
//
//    fun addNote(note: Note) {
//        Log.i("res", "try to add note to viewModel")
//        val list = _listOfNotes_.value
//        if (list == null) {
//            _listOfNotes_.value = ArrayList()
//            _listOfNotes_.value!!.add(note)
//        } else {
//            list.clear()
//            list.add(note)
//
//            _listOfNotes_.value = list
//        }
//    }

    fun addNewNote(note: Note) {
        uiScope.launch {
            //val note1 = Note("shop", "buy food", "23.12.2020", 1)
            database.noteDao.insertNote(note)
            _listOfNotes_.postValue(database.noteDao.getAllNotes())
        }
    }

    fun getNotesFromDB() {
        uiScope.launch {
            val list = database.noteDao.getAllNotes()
            if (!list.isNullOrEmpty())
                Log.i("res", "${list.size}")
            else
                Log.i("res", "list is null or empty")
            _listOfNotes_.postValue(list)
        }
    }

    fun getNoteById(id: Int){
        uiScope.launch {
            val note = database.noteDao.getNoteById(id)
            _note_.value = note
        }
    }

    fun clear() {
        uiScope.launch {
            database.noteDao.deleteAllNotes()
            _listOfNotes_.postValue(database.noteDao.getAllNotes())
        }
    }

    fun deleteNote(note: Note) {
        uiScope.launch {
            database.noteDao.deleteNote(note)
            _listOfNotes_.postValue(database.noteDao.getAllNotes())
        }
    }



}