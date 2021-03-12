package com.moanes.niceonetask.ui

import androidx.lifecycle.MutableLiveData
import com.moanes.datasource.model.Character
import com.moanes.datasource.repositories.CharactersRepo
import com.moanes.niceonetask.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val charactersRepo: CharactersRepo) :
    BaseViewModel() {
    private var lastOffset = 0
    private val limit = 10
    private var isLastPage = false

    val charactersLiveData = MutableLiveData<MutableList<Character>>()


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
}