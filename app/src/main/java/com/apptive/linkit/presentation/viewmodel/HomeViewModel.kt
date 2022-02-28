package com.apptive.linkit.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apptive.linkit.domain.repository.FolderRepository
import com.apptive.linkit.domain.interfaces.IFolder
import com.apptive.linkit.domain.model.FolderPrivate
import com.apptive.linkit.domain.model.FolderShared
import com.apptive.linkit.domain.model.log
import com.apptive.linkit.presentation.mapper.FolderMapper
import com.apptive.linkit.presentation.model.FolderView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FolderRepository,
    private val folderMapper: FolderMapper
): ViewModel() {
    val folders = mutableStateListOf<FolderView>()

    // 관찰할 Flow를 등록한다.
    init { collectAllFolders() ; "HomeViewModel 생성!".log()}

    fun addFolder(name: String, shared: Boolean = false) {
        val folder = when (shared) {
            true -> FolderShared(name = name, snode = 0, gid = 0)
            false -> FolderPrivate(name = name)
        }
        viewModelScope.launch { repository.insert(folder) }
    }

    fun updateFolder(folder: FolderView) {
        val domain = folderMapper.map(folder)
        viewModelScope.launch { repository.update(domain) }
    }
    fun deleteFolder(folder: FolderView) {
        val domain = folderMapper.map(folder)
        viewModelScope.launch { repository.delete(domain) }
    }

    private fun collectAllFolders() {
        viewModelScope.launch {
            repository.getAllFolders()
                .distinctUntilChanged()
                .map { folderMapper.map(it) }
                .collect { folderList ->
                    folders.clear()
                    folders.addAll(folderList)
                }
        }
    }

    override fun onCleared() {
        "HomeViewModel 제거!".log()
        super.onCleared()
    }
}