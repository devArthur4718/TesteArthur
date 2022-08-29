package com.devarthur4718.searchaddressapp

fun String.capitalizeAllWords(): String {
    val listString = mutableListOf<String>()
    this.split(" ").forEach {
        listString.add(it.lowercase().replaceFirstChar { it.uppercase() })
    }

    return listString.toString()
        .replace("[", "")
        .replace("]", "")
        .replace(",", "")
}
