package com.example.linkit.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.data.repository.FolderRepository
import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.data.storage.LinkItPrefs
import com.example.linkit.domain.interfaces.IFolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FolderRepository
): ViewModel() {
    // 내부의 값을 변경할 수 있는 hot flow (StateFlow)
    private val _allFolders = mutableStateOf(emptyList<IFolder>())
    val allFolders : State<List<IFolder>> = _allFolders

    // 관찰할 FLow를 등록한다.
    init { collectAllFolders() }

    fun addLink(folderEntity: FolderEntity) = viewModelScope.launch { repository.insert(folderEntity) }
    fun updateLink(folderEntity: FolderEntity) = viewModelScope.launch { repository.update(folderEntity) }
    fun removeLink(folderEntity: FolderEntity) = viewModelScope.launch { repository.delete(folderEntity) }

    private fun collectAllFolders() {
        viewModelScope.launch {
            repository.getAllFolders()
                .distinctUntilChanged()
                .collect { folderList ->
                    _allFolders.value = folderList
                }
        }
    }
}