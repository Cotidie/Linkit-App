package com.apptive.linkit.domain.mapper

import com.apptive.linkit.data.room.dto.*
import com.apptive.linkit.data.room.entity.LinkWithTags
import com.apptive.linkit.domain.interfaces.ILink
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkMappers @Inject constructor(
    private val linkMapper : LinkToEntity,
    private val entityMapper : EntityToLink,
    private val linkListMapper : LinkListMapper,
    private val entityListMapper : LinkEntityListMapper,
) {
    fun map(link: ILink) : LinkWithTags = linkMapper.map(link)
    fun map(link: LinkWithTags) : ILink = entityMapper.map(link)
    @JvmName("LinkListToEntities")
    fun map(links: List<ILink>) : List<LinkWithTags> = linkListMapper.map(links)
    @JvmName("EntitiesToLinkList")
    fun map(links: List<LinkWithTags>) : List<ILink> = entityListMapper.map(links)
}