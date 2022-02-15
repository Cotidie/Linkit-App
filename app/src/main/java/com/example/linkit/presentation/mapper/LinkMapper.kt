package com.example.linkit.presentation.mapper

import androidx.compose.runtime.mutableStateOf
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.interfaces.Mapper
import com.example.linkit.domain.model.Link
import com.example.linkit.presentation.model.LinkView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
/** LinkView와 ILink 간 변환을 담당하는 맵퍼 */
class LinkMapper @Inject constructor() {
    private val linkToView: LinkToView = LinkToView()
    private val viewToLink: ViewToLink = ViewToLink()

    fun map(link: ILink) : LinkView = linkToView.map(link)
    fun map(view: LinkView) : ILink = viewToLink.map(view)
}

class LinkToView: Mapper<ILink, LinkView> {
    override fun map(input: ILink): LinkView {
        return LinkView(
            id = input.id,
            parentFolder = input.parentFolder,
            name = mutableStateOf(input.name),
            memo = mutableStateOf(input.memo),
            url = mutableStateOf(input.url),
            favicon = mutableStateOf(input.favicon),
            image = mutableStateOf(input.image),
            tags = input.tags.toMutableList(),
            created = mutableStateOf(input.created)
        )
    }
}

class ViewToLink: Mapper<LinkView, ILink> {
    override fun map(input: LinkView): ILink {
        return Link(
            id = input.id,
            parentFolder = input.parentFolder,
            name = input.name.value,
            memo = input.memo.value,
            url = input.url.value,
            favicon = input.favicon.value,
            image = input.image.value,
            tags = ArrayList(input.tags.toList()),
            created = input.created.value
        )
    }
}