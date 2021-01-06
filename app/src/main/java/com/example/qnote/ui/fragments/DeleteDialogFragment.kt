package com.example.qnote.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.qnote.R
import com.example.qnote.core.models.Note
import com.example.qnote.ui.viewModels.MainViewModel

class DeleteDialogFragment(mainViewModel: MainViewModel) : DialogFragment() {

    private var note: Note? = null

    constructor(mainViewModel: MainViewModel, note: Note) : this(mainViewModel) {
        this.note = note
    }

    private val viewModel = mainViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val message: String

        val function : () -> Unit

        if (note != null) {
            message = getString(R.string.delete_one)
            function = {viewModel.deleteNote(note!!)}
        }
        else {
            message = getString(R.string.delete_all)
            function = {viewModel.clear()}
        }

        builder
            .setMessage(message)
            .setNegativeButton(getString(R.string.no)) { dialogInterface, _ ->
                dialogInterface?.cancel()
            }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                function()
            }
            .setNeutralButton(getString(R.string.cancel)) { dialogInterface, _ ->
                dialogInterface?.cancel()
            }


        return builder.create()
    }

}