package com.devarthur4718.searchaddressapp.featureAddressSearch.data.local.entity

import android.content.Context
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devarthur4718.searchaddressapp.capitalizeAllWords
import com.devarthur4718.searchaddressapp.unaccent
import okhttp3.ResponseBody
import java.io.File

@Entity
class LocalAddress(
    var fullAddress: String = "",
    val localName: String,
    @PrimaryKey
    val postalCode: String
) {

    companion object {
        const val FILE_NAME = "postalCodeFile"
        private var transformedList: MutableList<LocalAddress> = mutableListOf()

        fun saveFile(context: Context, body: ResponseBody) {
            if (!isAddressFilePresent(context)) {
                context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).use {
                    it.write(body.bytes())
                }
            } else {
                Log.e("Wtest", "File Already Downloaded")
            }
        }

        fun mapFileDataIntoObjectList(context: Context): MutableList<LocalAddress> {
            val file = File(context.filesDir, FILE_NAME)
            val readList = file.readLines()
            transformedList = mutableListOf()
            for (line in readList) {
                val dataSelected = line.split(",")
                transformedList.add(
                    LocalAddress(
                        "${dataSelected[dataSelected.lastIndex - 2]}-${dataSelected[dataSelected.lastIndex - 1]} ${dataSelected.last().unaccent()}",
                        dataSelected.last().toString().capitalizeAllWords(),
                        "${dataSelected[dataSelected.lastIndex - 2]}-${dataSelected[dataSelected.lastIndex - 1]}",
                    )
                )
            }
            transformedList.removeAt(0)
            return transformedList
        }

        private fun isAddressFilePresent(context: Context): Boolean {
            return context.fileList().contains(FILE_NAME)
        }
    }
}
