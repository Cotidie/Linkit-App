package com.example.linkit.data.room.dto

import com.example.linkit.data.room.entity.LinkEntity
import com.example.linkit.data.room.entity.LinkWithTags
import com.example.linkit.data.room.entity.TagEntity
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.interfaces.Mapper
import com.example.linkit.domain.model.Link
import com.example.linkit.domain.model.Url
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkToEntity @Inject constructor() : Mapper<ILink, LinkWithTags> {
    override fun map(input: ILink): LinkWithTags {
        val link =  LinkEntity(
            folderId = input.parentFolder,
            url = input.url.getString(true),
            image = input.image
        )

        return LinkWithTags(
            link = link,
            tags = input.tags.map { TagEntity(name = it) }
        )
    }
}

//@Singleton
//class LinkToTagEntity @Inject constructor() : Mapper<ILink, List<TagEntity>> {
//    override fun map(input: ILink): List<TagEntity> {
//        return input.tags.map { TagEntity(name = it) }
//    }
//}

@Singleton
class EntityToLink @Inject constructor() : Mapper<LinkWithTags, ILink> {
    override fun map(input: LinkWithTags): ILink {
        return Link(
            id = input.link.id,
            parentFolder = input.link.folderId,
            url = Url(input.link.url),
            image = input.link.image,
            tags = input.tags.map { it.name }
        )
    }
}