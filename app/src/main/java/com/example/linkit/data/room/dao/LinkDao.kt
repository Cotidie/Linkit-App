package com.example.linkit.data.room.dao

import androidx.room.*
import com.example.linkit.data.room.entity.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object. 데이터베이스에 쿼리를 날린다.
 */

@Dao
interface LinkDao {
    @Transaction
    /** 링크를 삭제하고, 태그를 참조하는 링크가 0개면 태그도 삭제한다. */
    suspend fun delete(linkWithTags: LinkWithTags) {
        val link = linkWithTags.link
        val tags = linkWithTags.tags

        delete(link)
        for (tag in tags) {
            if (countLinksByTag(tag.name) == 0)
                delete(tag)
        }
    }

    // Flow는 객체만 생성되고, SELECT 작업은 .collect에서 이루어진다.
    // 객체 생성은 변수 선언과 같으므로 코루틴으로 작업하지 않아도 된다.
    @Transaction
    @Query("SELECT * FROM linkTable")
    fun getLinks() : Flow<List<LinkWithTags>>
    @Transaction
    @Query("SELECT * FROM tagTable")
    fun getTags() : Flow<List<TagEntity>>
    @Transaction
    @Query("SELECT * from linkTable where parentFolderId = :folderId")
    fun getLinksInFolder(folderId: Long) : Flow<List<LinkWithTags>>

    @Query("SELECT * from linkTable where linkId = :id")
    suspend fun getLinkById(id: Long): LinkWithTags
    @Query("SELECT * FROM linkTable WHERE url LIKE '%' || :url || '%'")
    suspend fun getLinksByUrl(url: String): List<LinkWithTags>
    @Query("SELECT * FROM linkTable WHERE parentFolderId = :folderId AND url LIKE '%' || :url || '%'")
    suspend fun getLinksByUrlInFolder(url: String, folderId: Long): List<LinkWithTags>
    @Query(
        "SELECT * FROM linkTable AS link " +
            "INNER JOIN linkTagTable AS linkTag ON linkTag.linkId = link.linkId " +
            "INNER JOIN tagTable AS tag ON tag.name = linkTag.name " +
            "WHERE tag.name LIKE '&' || :tag || '$' "
    )
    suspend fun getLinksByTag(tag: String): List<LinkWithTags>
    @Query(
        "SELECT * FROM linkTable AS link " +
                "INNER JOIN linkTagTable AS linkTag ON linkTag.linkId = link.linkId " +
                "INNER JOIN tagTable AS tag ON tag.name = linkTag.name " +
                "WHERE parentFolderId = :folderId AND tag.name LIKE '&' || :tag || '$' "
    )
    suspend fun getLinksByTagInFolder(tag: String, folderId: Long): List<LinkWithTags>


    @Query("SELECT COUNT(name) FROM linkTagTable WHERE name = :tag")
    fun countLinksByTag(tag: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(linkEntity: LinkEntity): Long
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tagEntity: TagEntity): Long
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(linkTagRef : LinkTagRef)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(linkEntity: LinkEntity)

    @Query("DELETE FROM linkTable")
    suspend fun deleteAll()
    @Delete
    suspend fun delete(link: LinkEntity)
    @Delete
    suspend fun delete(tag: TagEntity)
    @Delete
    suspend fun delete(ref: LinkTagRef)
    @Query("DELETE FROM linkTable WHERE linkId IN (:ids)")
    suspend fun delete(ids: List<Long>)
}