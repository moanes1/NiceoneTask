package com.moanes.niceonetask.ui

import androidx.lifecycle.MutableLiveData
import com.moanes.datasource.model.Character
import com.moanes.datasource.repositories.CharactersRepo
import com.moanes.niceonetask.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
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
        charactersLiveData.value=ArrayList<Character>()
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


    private fun loadNextPage() {
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
                        calculateAge(character)
                    }
                }

                delay(1000)
            }
        }
    }

    private fun calculateAge(character: Character) {
        val sdf = SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH)
        val from = sdf.parse(character.birthday)
        val to = Calendar.getInstance().time

        val diffInMillies = abs(to.time - from.time)

        val seconds = diffInMillies / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val months = days / 30
        val years = months / 12
        character.liveAge =
            "$years years, ${months % 12} months, ${days % 30} days, ${hours % 24} hours, ${minutes % 60} minutes, ${seconds % 60} seconds"
        charactersLiveData.value = charactersLiveData.value
    }

    override fun onCleared() {
        super.onCleared()
        timeJob?.cancel()
        timeJob=null
    }
}