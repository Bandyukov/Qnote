package com.example.qnote.core.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes_table")
data class Note(

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "priority")
    val priority: Int

) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var uniqueId: Int = 0

    @Ignore
    constructor(title: String, description: String, date: String, priority: Int, uniqueId: Int) :
            this(title, description, date, priority) {
        this.uniqueId = uniqueId
    }

}
