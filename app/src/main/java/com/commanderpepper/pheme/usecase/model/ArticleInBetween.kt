package com.commanderpepper.pheme.usecase.model

data class ArticleInBetween(
    val id: Long,
    val publisher: String,
    val author: String,
    val title: String,
    val preview: String,
    val thumbnail: String,
    val date: String,
    val content: String
)