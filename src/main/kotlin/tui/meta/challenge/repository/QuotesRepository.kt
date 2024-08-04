package tui.meta.challenge.repository

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import jakarta.annotation.Nonnull
import tui.meta.challenge.model.Quote

@MongoRepository(databaseName = "challenge")
interface QuotesRepository : CrudRepository<Quote, String> {

    fun findByQuoteAuthor(@Nonnull quoteAuthor: String): List<Quote>

}