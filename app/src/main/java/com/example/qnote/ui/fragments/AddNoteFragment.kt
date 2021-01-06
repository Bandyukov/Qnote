package com.example.qnote.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.qnote.R
import com.example.qnote.core.database.NoteDatabase
import com.example.qnote.core.models.Note
import com.example.qnote.databinding.FragmentAddNoteBinding
import com.example.qnote.ui.viewModels.MainViewModel
import com.example.qnote.ui.viewModels.MainViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class AddNoteFragment : Fragment() {

    private val viewModel by lazy {
        val database = NoteDatabase.getInstance(requireContext().applicationContext)
        val mainViewModelFactory = MainViewModelFactory(database)
        ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        val binding: FragmentAddNoteBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_note,
            container,
            false
        )

        binding.lifecycleOwner = this

        val bundle: Bundle? = this.arguments

        bundle?.let {
            viewModel.getNoteById(it.getInt("id"))

            viewModel.note.observe(viewLifecycleOwner) {
                if (viewModel.note.value != null) {
                    val currentNote = viewModel.note.value
                    binding.editTextTitle.setText(currentNote?.title)
                    binding.editTextDescription.setText(currentNote?.description)
                } else Toast.makeText(requireContext(), "NULL", Toast.LENGTH_SHORT).show()
            }
        }
//        if (bundle != null) {
//            viewModel.getNoteById(bundle.getInt("id"))
//
//            viewModel.note.observe(viewLifecycleOwner) {
//                if (viewModel.note.value != null) {
//                    val currentNote = viewModel.note.value
//                    binding.editTextTitle.setText(currentNote?.title)
//                    binding.editTextDescription.setText(currentNote?.description)
//                } else Toast.makeText(requireContext(), "NULL", Toast.LENGTH_SHORT).show()
//            }
//        }

        binding.textViewSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDescription.text.toString()

            val date = Date()
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            val dateText = dateFormat.format(date)

            if (description.isNotEmpty()) {
                if (bundle != null)
                    viewModel.addNewNote(Note(title, description, dateText, 1, bundle.getInt("id")))
                else
                    viewModel.addNewNote(Note(title, description, dateText, 1))

                val imm: InputMethodManager =
                    requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(requireView().windowToken, 0)
                findNavController().navigate(R.id.action_addNoteFragment_to_mainFragment)
            } else
                Toast.makeText(requireContext(), getString(R.string.empty), Toast.LENGTH_SHORT)
                    .show()

        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu2, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(requireContext(), "Color", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }


}