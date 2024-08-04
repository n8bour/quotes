package tui.meta.challenge

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import tui.meta.challenge.model.Quote

@Client("/quotes")
interface QuotesClient {

    @Get("/{id}")
    fun getQuoteById(@PathVariable id: String): HttpResponse<Quote>

    @Get("/authors/{author}")
    fun getQuotesByAuthor(@PathVariable author: String): HttpResponse<List<Quote>>

    @Get
    fun getQuotes(): HttpResponse<List<Quote>>
}