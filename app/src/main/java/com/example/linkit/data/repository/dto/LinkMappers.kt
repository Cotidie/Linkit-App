package com.example.linkit.data.repository.dto

import com.example.linkit.data.room.dto.LinkToEntity
import com.example.linkit.data.room.entity.LinkEntity
import com.example.linkit.domain.interfaces.ILink
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkMappers @Inject constructor(
    private val linkMapper : LinkToEntity
) {
    fun map(link: ILink) : LinkEntity = linkMapper.map(link)
}