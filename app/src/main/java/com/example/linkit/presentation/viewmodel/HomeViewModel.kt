package com.example.linkit.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.data.repository.FolderRepository
import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.model.FolderPrivate
import com.example.linkit.domain.model.FolderShared
import com.example.linkit.domain.model.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FolderRepository,
) : ViewModel() {
    private val _allFolders = mutableStateOf(emptyList<IFolder>())
    val allFolders: State<List<IFolder>> = _allFolders

    // 관찰할 Flow를 등록한다.
    init {
        collectAllFolders(); "HomeViewModel 생성!".log()
    }

    fun addFolder(name: String, shared: Boolean = false) {
        val folder = when (shared) {
            true -> FolderShared(name = name, snode = 0, gid = 0)
            false -> FolderPrivate(name = name)
        }
        viewModelScope.launch { repository.insert(folder) }
    }

    fun updateFolder(folder: IFolder) = viewModelScope.launch { repository.update(folder) }
    fun removeFolder(folder: IFolder) = viewModelScope.launch { repository.delete(folder) }
    fun searchLink(text: String) {
        Log.d("##12", "$text")
    }

    private fun collectAllFolders() {
        viewModelScope.launch {
            repository.getAllFolders()
                .distinctUntilChanged()
                .collect { folderList ->
                    _allFolders.value = folderList
                }
        }
    }

    override fun onCleared() {
        "HomeViewModel 제거!".log()
        super.onCleared()
    }
}