package com.moanes.niceonetask.ui

import androidx.lifecycle.MutableLiveData
import com.moanes.datasource.model.Character
import com.moanes.datasource.repositories.CharactersRepo
import com.moanes.niceonetask.base.BaseViewModel
import com.moanes.niceonetask.util.calculateAge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.abs

@HiltViewModel
class MainViewModel @Inject constructor(private val charactersRepo: CharactersRepo) :
    BaseViewModel() {
    private var lastOffset = 0
    private val limit = 10
    private var isLastPage = false

    val charactersLiveData = MutableLiveData<MutableList<Character>>()
    var timeJob: Job? = null

    init {
        charactersLiveData.value = ArrayList<Character>()
    }

    fun getCharacters() = handelRequest {
        val result = withContext(Dispatchers.IO) {
            charactersRepo.getCharacters(lastOffset, limit)
        }
        isLastPage = result.isEmpty()
        if (lastOffset == 0)
            showNoData.postValue(result.isEmpty())

        result.let { charactersLiveData.value?.addAll(it) }

        charactersLiveData.value = charactersLiveData.value

    }


    fun loadNextPage() {
        if (!isLastPage) {
            lastOffset += limit
            getCharacters()
        }
    }

    fun calculateCharactersAge() {
        timeJob = launch {
            while (true) {
                charactersLiveData.value?.let {
                    for (character in it.iterator()) {
                        character.liveAge = calculateAge(character.birthday)
                        charactersLiveData.value = charactersLiveData.value
                    }
                }

                delay(1000)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timeJob?.cancel()
        timeJob = null
    }
}