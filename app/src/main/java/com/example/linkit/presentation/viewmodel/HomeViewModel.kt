package com.example.linkit.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.data.repository.FolderRepository
import com.example.linkit.data.room.entity.FolderEntity
import com.example.linkit.data.storage.LinkItPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefs : LinkItPrefs,
    private val repository: FolderRepository
): ViewModel() {
    val user = prefs.getLoggedInUser()
    // 내부의 값을 변경할 수 있는 hot flow (StateFlow)
    private val _folderList = MutableStateFlow<List<FolderEntity>>(emptyList())
    val folderEntityList : StateFlow<List<FolderEntity>> = _folderList

    // 관찰할 FLow를 등록한다.
    init {
        viewModelScope.launch(Dispatchers.IO) {
            // distinctUntilChanged: 다른 값을 emit 할 때만 collect한다.
            // TODO: Flow 코루틴 빌더 옵션 정리하기
            repository.getAllFolders().distinctUntilChanged()
                .collect { folderList ->
                    if (folderList.isNullOrEmpty()) {
                        Log.d("Empty", ": Empty List")
                    } else {
                        _folderList.value = folderList
                    }
                }
        }
    }

    fun addLink(folderEntity: FolderEntity) = viewModelScope.launch { repository.insert(folderEntity) }
    fun updateLink(folderEntity: FolderEntity) = viewModelScope.launch { repository.update(folderEntity) }
    fun removeLink(folderEntity: FolderEntity) = viewModelScope.launch { repository.delete(folderEntity) }
}