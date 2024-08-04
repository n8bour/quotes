package tui.meta.challenge.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.MappedProperty


@MappedEntity(value = "quotes")
data class Quote(
    @field: Id
    val _id: String,
    val quoteGenre: String,
    val quoteAuthor: String,
    val quoteText: String,
    @field: MappedProperty("__v")
    val v: Int
)