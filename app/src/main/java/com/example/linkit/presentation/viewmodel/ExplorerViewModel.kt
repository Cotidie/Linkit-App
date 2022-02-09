package com.example.linkit.presentation.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.linkit.data.repository.FolderRepository
import com.example.linkit.data.repository.LinkRepository
import com.example.linkit.domain.interfaces.IFolder
import com.example.linkit.domain.interfaces.ILink
import com.example.linkit.domain.model.*
import com.example.linkit.presentation.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExplorerViewModel @Inject constructor(
    private val linkRepo: LinkRepository,
    private val folderRepo: FolderRepository,
) : ViewModel() {
    val links = mutableStateOf(emptyList<ILink>())
    val currentFolder = MutableStateFlow(IFolder.DEFAULT)

    init {
        collectLinks()
    }

    fun clearScreen() {
        viewModelScope.launch {
            currentFolder.update { IFolder.DEFAULT }
        }
    }

    fun setCurrentFolder(id: Long) {
        runBlocking {
            withContext(Dispatchers.IO) {
                currentFolder.update {
                    folderRepo.getFolder(id)
                }
            }
        }
    }

    fun addLink(urlString: String) {
        val url = Url(urlString)
        if (!url.isValid()) {
            "부정확한 URL입니다.".toast()
            return
        }
        val link = createNewLink(url)
        viewModelScope.launch {
            imageAdd(link = link)
            Log.d("##12","link : ${link.image}")
            linkRepo.addLink(link)
        }
    }

    /** 현재 폴더가 변경되면 자동으로 폴더 내의 링크들을 불러온다. */
    private fun collectLinks() {
        viewModelScope.launch {
            currentFolder
                .flatMapLatest { folder ->
                    linkRepo.getLinksInFolder(folder.id)
                }
                .distinctUntilChanged()
                .collect {
                    links.value = it
                }
        }
    }

    /** url을 받아 Link 객체를 만든다. */
    private fun createNewLink(url: Url): ILink {
        val folder = currentFolder.value
//        val image = stringToBitmap(urlCrawling("https://www.naver.com")?.get("image"))
//        Log.d("##12","2 $image")
        return Link(parentFolder = folder.id, url = url)
    }

    private suspend fun imageAdd(link: ILink) {
        viewModelScope.launch(Dispatchers.IO) {
            val image = stringToBitmap(urlCrawling("https://www.naver.com")?.get("image"))
            Log.d("##12","2 $image")
            link.image = image
        }
    }

    private fun urlCrawling(url: String): HashMap<String, String>? {

        val map = HashMap<String, String>()

        try {
            val con: Connection = Jsoup.connect(url)
            val doc: Document = con.get()
            var orgTags: Elements = doc.select("meta[property^=og:]")
            if (orgTags.size != 0) {
                for (i in 0 until orgTags.size) {
                    val tag: Element = orgTags[i]
                    when (tag.attr("property")) {
//                        "og:url" -> {
//                            map["url"] = tag.attr("content")
//                        }
//                        "og:title" -> {
//                            map["title"] = tag.attr("content")
//
//                        }
                        "og:image" -> {
                            map["image"] = tag.attr("content")

                        }
//                        "og:description" -> {
//                            map["description"] = tag.attr("content")
//                        }
                    }
                }
            } else {
                orgTags = doc.select("meta[name^=twitter:]")
                Log.d("!@12", "$orgTags")
                for (i in 0 until orgTags.size) {
                    val tag: Element = orgTags[i]
                    when (tag.attr("name")) {
//                        "twitter:title" -> {
//                            map["title"] = tag.attr("content")
//
//                        }
                        "twitter:image" -> {
                            map["image"] = tag.attr("content")

                        }
//                        "twitter:description" -> {
//                            map["description"] = tag.attr("content")
//                        }
                    }
                }
            }
            return map
        } catch (e: Exception) {
            Log.d("##12", "에러 : $e")
            return null
        }
    }

    private fun stringToBitmap(encodedString: String?): Bitmap {
        return try {
            val encodeByte: ByteArray = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            EMPTY_BITMAP
        }
    }
}