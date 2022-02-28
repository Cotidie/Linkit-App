package com.apptive.linkit._enums

enum class SearchType(val text: String) {
    TAG("태그"), URL("링크");

    companion object {
        fun of(text: String): SearchType {
            return values().first { it.text == text }
        }
    }
}