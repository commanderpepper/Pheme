package com.commanderpepper.pheme.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val publisher: String,
    val title: String,
    val thumbnail: String,
    val preview: String,
    val content: String,
    val category: String
)