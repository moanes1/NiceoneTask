package com.moanes.niceonetask.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moanes.niceonetask.R
import com.moanes.niceonetask.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: CharactersAdapter

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleError(viewModel)
        handleProgress(viewModel)

        handleCharactersLiveData()
        initCharactersList()
        handlePagination()

        viewModel.getCharacters()

        viewModel.calculateCharactersAge()

    }

    private fun handleCharactersLiveData() {
        viewModel.charactersLiveData.observe(this, {
            adapter.submitList(it.toMutableList())
        })
    }

    private fun initCharactersList() {
        adapter = CharactersAdapter()
        charactersRV.layoutManager = LinearLayoutManager(this)
        charactersRV.adapter = adapter
    }

    private fun handlePagination() {
        charactersRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = recyclerView.layoutManager!!.childCount
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                val firstVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()

                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    viewModel.loadNextPage()
                }
            }
        })
    }
}