package com.alpha2048.mergeadaptertest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    val trigger = BroadcastChannel<Unit>(1)

    val repoItems = ArrayList<RepoItemResponse>()

    private val repository = GithubRepository()

    private var page = 0

    private var isLoading = false

    fun loadData() {
        if (isLoading) return
        isLoading = true
        page++
        viewModelScope.launch(Dispatchers.Main) {
            repository.getRepositoryList("Coroutine", page).collect {
                repoItems.addAll(it.items)
                trigger.send(Unit)
                isLoading = false
            }
        }
    }
}