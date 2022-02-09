package com.example.linkit.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.linkit.data.room.entity.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query("SELECT * FROM folderTable")
    fun getAllFolders() : Flow<List<FolderEntity>>

    @Query("SELECT * FROM folderTable WHERE snode != null")
    fun getSharedFolders() : Flow<List<FolderEntity>>

    @Query("SELECT * FROM folderTable WHERE snode == null")
    fun getPrivateFolders() : Flow<List<FolderEntity>>

    @Query("SELECT * FROM folderTable WHERE folderId = :id")
    suspend fun getFolderById(id: Long) : FolderEntity

//    @Query("SELECT * FROM linkTable WHERE tags LIKE :query || '%' ORDER BY `key` DESC ")
//    fun getQueryLink(query: String): LiveData<List<Link>>

    @Insert
    suspend fun insert(folderEntity: FolderEntity)

    @Update
    suspend fun update(folderEntity: FolderEntity)

    @Delete
    suspend fun delete(folderEntity: FolderEntity)

    @Query("DELETE FROM folderTable")
    suspend fun deleteAll()
}