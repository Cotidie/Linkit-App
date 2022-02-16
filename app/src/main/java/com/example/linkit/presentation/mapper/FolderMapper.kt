package com.example.linkit.presentation.mapper

import android.view.View
import androidx.compose.runtime.mutableStateOf
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ListMapperImpl
import com.example.linkit.domain.interfaces.Mapper
import com.example.linkit.domain.model.FolderPrivate
import com.example.linkit.domain.model.FolderShared
import com.example.linkit.presentation.model.FolderView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/** Folder 객체의 View <-> Domain 레이어 간 변환 맵퍼 */
class FolderMapper @Inject constructor() {
    private val folderToView = FolderToView()
    private val viewToFolder = ViewToFolder()
    private val foldersToViews = FoldersToViews()
    private val viewsToFolders = ViewsToFolders()


    fun map(folder: IFolder): FolderView = folderToView.map(folder)
    fun map(view: FolderView): IFolder = viewToFolder.map(view)
    @JvmName("FoldersToViews")
    fun map(folders: List<IFolder>): List<FolderView> = foldersToViews.map(folders)
    @JvmName("ViewsToFolders")
    fun map(views: List<FolderView>): List<IFolder> = viewsToFolders.map(views)
}

private class FolderToView(): Mapper<IFolder, FolderView> {
    override fun map(input: IFolder): FolderView {
        val gid = when (input.isShared()) {
            true -> (input as FolderShared).gid
            false -> null
        }
        val snode = when (input.isShared()) {
            true -> (input as FolderShared).snode
            false -> null
        }

        return FolderView(
            id = input.id,
            name = mutableStateOf(input.name),
            image = mutableStateOf(input.image),
            gid = mutableStateOf(gid),
            snode = mutableStateOf(snode)
        )
    }
}

private class ViewToFolder(): Mapper<FolderView, IFolder> {
    override fun map(input: FolderView): IFolder {
        return when(input.isShared()) {
            true -> FolderShared(
                id = input.id,
                name = input.name.value,
                image = input.image.value,
                gid = input.gid.value!!,
                snode = input.gid.value!!
            )
            false -> FolderPrivate(
                id = input.id,
                name = input.name.value,
                image = input.image.value
            )
        }
    }
}

private class FoldersToViews(): ListMapperImpl<IFolder, FolderView>(FolderToView())
private class ViewsToFolders(): ListMapperImpl<FolderView, IFolder>(ViewToFolder())