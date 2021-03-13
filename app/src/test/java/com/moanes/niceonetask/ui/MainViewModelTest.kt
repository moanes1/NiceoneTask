package com.moanes.niceonetask.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moanes.datasource.model.Character
import com.moanes.datasource.repositories.CharactersRepo
import com.moanes.niceonetask.TestCoroutineRule
import com.moanes.niceonetask.getCharacters
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okio.IOException
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var charactersRepo: CharactersRepo

    @MockK
    private lateinit var loadingObserver: Observer<Boolean>

    @MockK
    private lateinit var errorsObserver: Observer<String>

    @MockK
    private lateinit var charactersObserver: Observer<MutableList<Character>>

    private lateinit var subject: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        subject = MainViewModel(charactersRepo)
    }

    @Test
    fun `getCharacters success`() {
        // given
        val offset: Int = 0
        val limit: Int = 10
        coEvery { charactersRepo.getCharacters(offset, limit) } returns getCharacters()

        subject.charactersLiveData.observeForever(charactersObserver)
        subject.showLoading.observeForever(loadingObserver)

        //when
        subject.getCharacters()

        // then
        verify { loadingObserver.onChanged(true) }
        coVerify { charactersRepo.getCharacters(offset, limit) }
        verify { charactersObserver.onChanged(getCharacters()) }
        verify { loadingObserver.onChanged(false) }
    }

    @Test
    fun `getCharacters failure`() {
        // given
        val offset: Int = 0
        val limit: Int = 10
        coEvery { charactersRepo.getCharacters(offset, limit) } throws IOException()

        subject.errorLiveData.observeForever(errorsObserver)
        subject.showLoading.observeForever(loadingObserver)

        //when
        subject.getCharacters()

        // then
        verify { loadingObserver.onChanged(true) }
        verify { loadingObserver.onChanged(false) }
        verify { errorsObserver.onChanged(IOException().localizedMessage) }

    }
}