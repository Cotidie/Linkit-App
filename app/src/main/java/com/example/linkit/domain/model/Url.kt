package com.example.linkit.domain.model

import android.util.Patterns
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
        if (showProtocol) return detachProtocol(_url!!)
        return _url.toString()
    }

    private fun attachHttp(url: String) : String {
        if (!url.startsWith("http://") || !url.startsWith("https://"))
            return "http://$url"

        return url
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