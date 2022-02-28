package com.apptive.linkit.data.room.dto

import com.apptive.linkit.data.room.entity.LinkEntity
import com.apptive.linkit.data.room.entity.LinkWithTags
import com.apptive.linkit.data.room.entity.TagEntity
import com.apptive.linkit.domain.interfaces.ILink
import com.apptive.linkit.domain.interfaces.ListMapperImpl
import com.apptive.linkit.domain.interfaces.Mapper
import com.apptive.linkit.domain.model.EMPTY_BITMAP
import com.apptive.linkit.domain.model.Link
import com.apptive.linkit.domain.model.Url
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkToEntity @Inject constructor() : Mapper<ILink, LinkWithTags> {
    override fun map(input: ILink): LinkWithTags {
        val favicon = when (input.favicon == EMPTY_BITMAP) {
            true -> null
            false -> input.favicon
        }
        val image = when (input.image == EMPTY_BITMAP) {
            true -> null
            false -> input.image
        }
        val link = LinkEntity(
            id = input.id,
            folderId = input.parentFolder,
            url = input.url.originalString,
            memo = input.memo,
            favicon = favicon,
            image = image,
            created = input.created
        )

        return LinkWithTags(
            link = link,
            tags = input.tags.map { TagEntity(name = it) }
        )
    }
}

@Singleton
class EntityToLink @Inject constructor() : Mapper<LinkWithTags, ILink> {
    override fun map(input: LinkWithTags): ILink {
        val favicon = input.link.favicon ?: EMPTY_BITMAP
        val image = input.link.image ?: EMPTY_BITMAP


        return Link(
            id = input.link.id,
            parentFolder = input.link.folderId,
            url = Url(input.link.url),
            memo = input.link.memo,
            favicon = favicon,
            image = image,
            created = input.link.created,
            tags = ArrayList(input.tags.map { it.name })
        )
    }
}

@Singleton
class LinkListMapper @Inject constructor(
    linkMapper: LinkToEntity,
) : ListMapperImpl<ILink, LinkWithTags>(linkMapper)

@Singleton
class LinkEntityListMapper @Inject constructor(
    entityMapper: EntityToLink,
) : ListMapperImpl<LinkWithTags, ILink>(entityMapper)