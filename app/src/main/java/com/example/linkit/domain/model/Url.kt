package com.example.linkit.domain.model

import android.util.Patterns
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.net.MalformedURLException
import java.net.URL

/**
 *  URL 문자열로부터 프로토콜, 도메인 등을 해석하는 객체
 *  scheme://[username:[email protected]]domain[:port]/path?query_string#fragment_id
 */
class Url constructor() {
    private var _url: URL? = null

    constructor(urlString: String) : this() {
        parse(urlString)
    }

    fun parse(url: String) : Url{
        val potentialUrl = attachHttp(url)

        try {
            if (!isValidUrlString(potentialUrl))
                throw MalformedURLException()

            _url = URL(potentialUrl)
        }
        catch (e: MalformedURLException) {
            _url = null
        }

        return this
    }

    fun isValid() : Boolean = (_url != null)

    fun getString(showProtocol: Boolean = false) : String {
        if (!isValid()) return ""

        var urlString = _url.toString()
        if (!showProtocol) urlString = detachProtocol(_url!!)

        return urlString
    }

    fun getDomain(showProtocol: Boolean = false) : String {
        if (showProtocol)
            return "${_url!!.protocol}://${attachWww(_url!!.host)}"

        return attachWww(_url!!.host)
    }

    fun getFaviconUrl(size: Int = 128) : String {
//        return "https://t2.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=${getDomain()}&size=$size"
        return "https://www.google.com/s2/favicons?sz=$size&domain_url=${getDomain(true)}"
    }

    fun getMetaImg(): HashMap<String, String>? {

        val map = HashMap<String, String>()

        try {
            val con: Connection = Jsoup.connect("https://github.com/")
            val doc: Document = con.get()
            var orgTags: Elements = doc.select("meta[property^=og:]")
            if (orgTags.size != 0) {
                for (i in 0 until orgTags.size) {
                    val tag: Element = orgTags[i]
                    when (tag.attr("property")) {
                        "og:image" -> {
                            map["image"] = tag.attr("content")

                        }
//                        "og:url" -> {
//                            map["url"] = tag.attr("content")
//                        }
//                        "og:title" -> {
//                            map["title"] = tag.attr("content")
//
//                        }
//                        "og:description" -> {
//                            map["description"] = tag.attr("content")
//                        }
                    }
                }
            } else {
                orgTags = doc.select("meta[name^=twitter:]")
                for (i in 0 until orgTags.size) {
                    val tag: Element = orgTags[i]
                    when (tag.attr("name")) {
                        "twitter:image" -> {
                            map["image"] = tag.attr("content")

                        }
//                        "twitter:title" -> {
//                            map["title"] = tag.attr("content")
//
//                        }
//                        "twitter:description" -> {
//                            map["description"] = tag.attr("content")
//                        }
                    }
                }
            }
            return map
        } catch (e: Exception) {
            "에러 : $e".log()
            return null
        }
    }

    private fun attachHttp(url: String) : String {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            return "https://$url"

        return url
    }

    /** www가 없는 도메인에 www를 붙인다.*/
    private fun attachWww(domain: String) : String {
        if (!domain.startsWith("www."))
            return "www.$domain"

        return domain
    }

    private fun detachProtocol(url: URL) : String {
        return url.toString().removePrefix("${url.protocol}://")
    }

    private fun isValidUrlString(urlString: String) : Boolean {
        return Patterns.WEB_URL.matcher(urlString).matches()
    }

    override fun toString(): String {
        if (!isValid()) return "Invalid URL!"

        return "Url(Protocol: ${_url!!.protocol}, Host: ${_url!!.host}, Path: ${_url!!.path}, Query: ${_url!!.query}"
    }
}