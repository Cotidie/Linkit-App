package com.example.linkit.domain.model

import java.net.MalformedURLException
import java.net.URL

/**
 *  URL 문자열로부터 프로토콜, 도메인 등을 해석하는 객체
 *  scheme://[username:[email protected]]domain[:port]/path?query_string#fragment_id
 */
class Url {
    private var _url: URL? = null

    fun parse(url: String) : Url{
        val urlToParse = attachHttp(url)

        try {
            _url = URL(urlToParse)
        }
        catch (e: MalformedURLException) {
            _url = null
        }

        return this
    }

    fun isValid() : Boolean = (_url != null)

    fun getString() : String = _url?.toString() ?: ""
    
    private fun attachHttp(url: String) : String {
        if (!url.startsWith("http://") || !url.startsWith("https://"))
            return "http://$url"

        return url
    }

    override fun toString(): String {
        if (_url == null) return "Invalid URL!"

        return "Url(Protocol: ${_url!!.protocol}, Host: ${_url!!.host}, Path: ${_url!!.path}, Query: ${_url!!.query}"
    }
}