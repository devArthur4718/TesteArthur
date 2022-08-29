package com.devarthur4718.searchaddressapp

import java.text.Normalizer

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

private val REGEX_UNACCENT = "\\p{InCombiningDiacriticalMarks}+".toRegex()

fun CharSequence.unaccent(): String {
    val temp = Normalizer.normalize(this, Normalizer.Form.NFD)
    return REGEX_UNACCENT.replace(temp, "")
}
