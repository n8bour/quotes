package tui.meta.challenge.entity

import io.micronaut.data.annotation.*

@MappedEntity(value = "quotes")
data class Quote(
    @field: Id
    @field: MappedProperty("_id")
    val id: String,
    val quoteGenre: String,
    val quoteAuthor: String,
    val quoteText: String,
    @field: MappedProperty("__v")
    val v: Int
)