package com.apptive.linkit.domain.interfaces

interface Mapper<I, O> {
    fun map(input: I) : O
}