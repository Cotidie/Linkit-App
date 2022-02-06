package com.example.linkit.data.room.dto

import com.example.linkit.data.room.entity.LinkWithTags
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.interfaces.ListMapperImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkListMapper @Inject constructor(
    linkMapper : LinkToEntity
) : ListMapperImpl<ILink, LinkWithTags>(linkMapper)

@Singleton
class LinkEntityListMapper @Inject constructor(
    entityMapper : EntityToLink
) : ListMapperImpl<LinkWithTags, ILink>(entityMapper)