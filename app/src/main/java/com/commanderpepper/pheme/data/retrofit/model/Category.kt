package com.commanderpepper.pheme.data.retrofit.model

enum class Category(val category: String){
    NEWS("news"),
    SPORTS("sports"),
    TECH("technology"),
    ENTERTAINMENT("entertainment")
}

fun String?.getCategory(): Category {
    return when(this){
        "news" -> Category.NEWS
        "sports" -> Category.SPORTS
        "technology" -> Category.TECH
        "entertainment" -> Category.ENTERTAINMENT
        else -> Category.NEWS
    }
}