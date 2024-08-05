package tui.meta.challenge.repository

import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import jakarta.annotation.Nonnull
import reactor.core.publisher.Flux
import tui.meta.challenge.entity.Quote

@MongoRepository(databaseName = "challenge")
interface QuotesRepository : ReactorCrudRepository<Quote, String> {

    fun findByQuoteAuthor(@Nonnull quoteAuthor: String): Flux<Quote>

}