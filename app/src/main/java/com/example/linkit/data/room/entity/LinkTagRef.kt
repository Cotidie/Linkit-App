package com.example.linkit.data.room.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


/** Link 테이블과 Tag 테이블 간 다대다 관계를 정의한다. */
@Entity(
    tableName = "linkTagTable",
    primaryKeys = ["linkId", "tagId"],
    foreignKeys = [
        ForeignKey(
            entity = LinkEntity::class,
            parentColumns = ["linkId"],
            childColumns = ["linkId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["tagId"],
            childColumns = ["tagId"],
            onDelete = CASCADE
        )
    ]
)
data class LinkTagRef(
    @ColumnInfo(name="linkId")
    val linkId : Long,
    @ColumnInfo(name="tagId")
    val tagId : Long
)


/** 태그 입장에서 링크 목록 */
data class TagWithLinks(
    // 대상이 엔티티인 경우 Embbeded 어노테이션 사용
    @Embedded val tag: TagEntity,
    @Relation(
        // 기준이 되는 테이블 (TagEntity)
        parentColumn = "tagId",
        // Join할 테이블 (LinkEntity)
        entityColumn = "linkId",
        // Join 테이블 제공 (다대다 연결 테이블)
        associateBy = Junction(LinkTagRef::class)
    ) val links: List<LinkEntity>
)

/** 링크 입장에서 태그 목록 */
data class LinkWithTags(
    @Embedded val link: LinkEntity,
    @Relation(
        parentColumn = "linkId",
        entityColumn = "tagId",
        associateBy = Junction(LinkTagRef::class)
    ) val tags: List<TagEntity>
)