package com.example.linkit.domain.interfaces

interface Mapper<I, O> {
    fun map(input: I) : O
}