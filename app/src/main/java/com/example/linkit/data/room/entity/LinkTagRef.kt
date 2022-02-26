package com.example.linkit.data.room.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


/** Link 테이블과 Tag 테이블 간 다대다 관계를 정의한다. */
@Entity(
    tableName = "linkTagTable",
    primaryKeys = ["linkId", "name"],
    foreignKeys = [
        ForeignKey(
            entity = LinkEntity::class,
            parentColumns = ["linkId"],
            childColumns = ["linkId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = TagEntity::class,
            parentColumns = ["name"],
            childColumns = ["name"],
            onDelete = CASCADE
        )
    ]
)
data class LinkTagRef(
    @ColumnInfo(name="linkId")
    val linkId : Long,
    @ColumnInfo(name="name")
    val tag : String
)

/** 링크 입장에서 태그 목록 */
data class LinkWithTags(
    @Embedded val link: LinkEntity,
    @Relation(
        parentColumn = "linkId",
        entityColumn = "name",
        associateBy = Junction(LinkTagRef::class)
    ) val tags: List<TagEntity>
)