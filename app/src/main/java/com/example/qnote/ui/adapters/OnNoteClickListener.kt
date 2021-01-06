package com.example.qnote.ui.adapters

interface OnNoteClickListener {
    fun onShortClick(position: Int)
    fun onLongClick(position: Int)
}