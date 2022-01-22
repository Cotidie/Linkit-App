package com.example.linkit.data.room.dao

import androidx.room.*
import com.example.linkit.data.room.entity.FolderModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {
    @Query("SELECT * FROM folder_table")
    fun getAllFolders() : Flow<List<FolderModel>>

    @Query("SELECT * FROM folder_table WHERE id = :id")
    suspend fun getFolderById(id: Long) : FolderModel

    @Insert
    suspend fun insert(folderModel: FolderModel)

    @Update
    suspend fun update(folderModel: FolderModel)

    @Delete
    suspend fun delete(folderModel: FolderModel)

    @Query("DELETE FROM folder_table")
    suspend fun deleteAll()
}