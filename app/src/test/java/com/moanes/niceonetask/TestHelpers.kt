package com.moanes.niceonetask

import com.moanes.datasource.model.Character

fun getCharacters():ArrayList<Character>{
    val list= ArrayList<Character>()
    for(i in 1..10){
        val character =Character("$i-1-2020",i,"","character $i")
        list.add(character)
    }
    return list
}