package com.example.linkit.data.network.model.request

data class LinkContent(
    val name: String,
    val url: String,
    val tags: List<String>,
    val memo: String,
    val image: String
)

data class FolderContent(
    val name: String,
    val image: String
)

data class Meta(
    val gid: Long,
    val owner: String,
    val snode: Long
)

data class LinkInsert(
    val meta: Meta,
    val data: List<LinkContent>
)

data class FolderInsert(
    val meta: Meta,
    val data: List<FolderContent>
)