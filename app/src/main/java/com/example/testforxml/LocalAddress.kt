package com.example.testforxml

import android.R
import android.content.Context
import android.widget.ArrayAdapter

class LocalAddress(
    val localName: String,
    val postalCode: String
) {

    companion object {
        fun transformListIntoArrayAdapter(
            list: MutableList<LocalAddress>,
            context: Context
        ): ArrayAdapter<String> {
            val stringList = list.map { "{${it.postalCode} ${it.localName}}" }

            return ArrayAdapter(
                context,
                R.layout.simple_list_item_1,
                stringList
            )
        }
    }
}
