package com.example.linkit.domain.interfaces

interface ListMapper<I, O> : Mapper<List<I>, List<O>>

open class ListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : ListMapper<I, O> {
    override fun mapTo(input: List<I>): List<O> {
        return input.map { mapper.mapTo(it) }
    }
    override fun mapFrom(input: List<O>) : List<I> {
        return input.map { mapper.mapFrom(it) }
    }
}