package com.example.linkit.domain.interfaces

interface Mapper<I, O> {
    fun mapTo(input: I) : O
    fun mapFrom(input: O) : I
}