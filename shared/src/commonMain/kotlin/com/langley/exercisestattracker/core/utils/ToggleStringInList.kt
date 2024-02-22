package com.langley.exercisestattracker.core.utils

fun toggleStringInList(string: String, list: List<String>?): List<String>{
    if (list.isNullOrEmpty()){
        return listOf(string)
    }
    else {
        val mutableList = list.toMutableList()
        val stringsToRemove = mutableListOf<String>()

        for (currentString in list){

            if (string.lowercase() == currentString.lowercase()){
                stringsToRemove.add(currentString)
            }
        }
        if (stringsToRemove.isEmpty()){
            mutableList.add(string)
        }
        else {
            mutableList.remove(string)
        }

        return mutableList
    }
}