package com.example.linkit.domain.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Patterns
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
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