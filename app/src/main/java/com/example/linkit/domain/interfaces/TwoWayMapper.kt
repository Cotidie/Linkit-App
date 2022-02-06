package com.example.linkit.domain.interfaces

interface TwoWayMapper<I, O> {
    fun mapFrom(input: O) : I
    fun mapTo(input: I) : O
}