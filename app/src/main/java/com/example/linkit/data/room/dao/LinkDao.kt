package com.example.linkit.data.room.dao

import androidx.room.*
import com.example.linkit.data.room.entity.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object. 데이터베이스에 쿼리를 날린다.
 */

@Dao
interface LinkDao {
    // Flow는 객체만 생성되고, SELECT 작업은 .collect에서 이루어진다.
    // 객체 생성은 변수 선언과 같으므로 코루틴으로 작업하지 않아도 된다.

    @Transaction
    @Query("SELECT * FROM linkTable")
    fun getLinks() : Flow<List<LinkWithTags>>

    @Transaction
    @Query("SELECT * FROM tagTable")
    fun getTags() : Flow<List<TagWithLinks>>

    @Transaction
    @Query("SELECT * from linkTable where parentFolderId = :folderId")
    fun getLinksInFolder(folderId: Long) : Flow<List<LinkWithTags>>

    @Query("SELECT * from linkTable where linkId = :id")
    suspend fun getLinkById(id: Long): LinkWithTags

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(linkEntity: LinkEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tagEntity: TagEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(linkTagRef : LinkTagRef)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(linkEntity: LinkEntity)

    @Query("DELETE FROM linkTable")
    suspend fun delete()

    @Delete
    suspend fun deleteLink(linkEntity: LinkEntity)
}