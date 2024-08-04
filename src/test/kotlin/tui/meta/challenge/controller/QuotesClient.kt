package tui.meta.challenge.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import tui.meta.challenge.model.QuoteDTO

@Client("/quotes")
interface QuotesClient {

    @Get("/{id}")
    fun getQuoteById(@PathVariable id: String): HttpResponse<QuoteDTO>

    @Get("/authors/{author}")
    fun getQuotesByAuthor(@PathVariable author: String): HttpResponse<List<QuoteDTO>>

    @Get
    fun getQuotes(): HttpResponse<List<QuoteDTO>>
}