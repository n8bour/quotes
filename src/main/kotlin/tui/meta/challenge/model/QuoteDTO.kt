package tui.meta.challenge.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class QuoteDTO(
    val genre: String,
    val author: String,
    val text: String,
)