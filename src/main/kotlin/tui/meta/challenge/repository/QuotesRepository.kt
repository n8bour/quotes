package tui.meta.challenge.repository

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.PageableRepository
import jakarta.annotation.Nonnull
import tui.meta.challenge.entity.Quote

@MongoRepository(databaseName = "challenge")
interface QuotesRepository : PageableRepository<Quote, String> {

    fun findByQuoteAuthor(@Nonnull quoteAuthor: String): List<Quote>

}