package com.example.qnote.ui.fragments

import android.content.ClipData
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.qnote.R
import com.example.qnote.ui.adapters.NoteAdapter
import com.example.qnote.ui.adapters.OnNoteClickListener
import com.example.qnote.core.database.NoteDatabase
import com.example.qnote.databinding.FragmentMainBinding
import com.example.qnote.ui.viewModels.MainViewModel
import com.example.qnote.ui.viewModels.MainViewModelFactory



class MainFragment : Fragment() {

    private val viewModel by lazy {
        val database = NoteDatabase.getInstance(requireContext().applicationContext)
        val mainViewModelFactory = MainViewModelFactory(database)
        ViewModelProvider(this, mainViewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
         val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false)

        binding.lifecycleOwner = this

        getData()

        val adapterNote = NoteAdapter()
        binding.recyclerView.apply {
            adapter = adapterNote
            layoutManager = LinearLayoutManager(requireContext())
        }

        binding.imageButtonAddNewNote.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment)
        }

        adapterNote.setOnNoteClickListener(object : OnNoteClickListener {
            override fun onShortClick(position: Int) {
                val note = viewModel.listOfNotes.value!![position]

                val bundle = Bundle()
                bundle.putInt("id", note.uniqueId)

                findNavController().navigate(R.id.action_mainFragment_to_addNoteFragment, bundle)
            }

            override fun onLongClick(position: Int) {
                val fragment = DeleteDialogFragment(viewModel, viewModel.listOfNotes.value!![position])
                fragmentManager?.let { fragment.show(it, "qwerty") }
            }
        })


        viewModel.listOfNotes.observe(viewLifecycleOwner) {
            adapterNote.setNotes(it)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deleteAllNotes -> {
                val fragment = DeleteDialogFragment(viewModel)
                fragmentManager?.let { fragment.show(it, "qwerty") }
            }
            R.id.sortBy -> {
                Toast.makeText(requireContext(), "SORT", Toast.LENGTH_SHORT).show()
                
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        viewModel.getNotesFromDB()
    }

}