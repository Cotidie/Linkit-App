package com.example.linkit.data.room.dto

import com.example.linkit.data.room.entity.LinkEntity
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.interfaces.Mapper

class LinkToEntity : Mapper<ILink, LinkEntity> {
    override fun map(input: ILink): LinkEntity {
        return LinkEntity(
            folderId = input.parentFolder,
            url = input.url.getString(true),
            image = input.image
        )
    }

}