package tui.meta.challenge.mapper

import tui.meta.challenge.entity.Quote
import tui.meta.challenge.model.QuoteDTO

object QuoteMapper {
    fun Quote.toDTO(): QuoteDTO =
        QuoteDTO(id, quoteGenre, quoteAuthor, quoteText)
}