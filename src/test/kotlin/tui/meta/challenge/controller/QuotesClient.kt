package tui.meta.challenge.controller

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import kotlinx.coroutines.flow.Flow
import tui.meta.challenge.model.QuoteDTO

@Client("/quotes")
interface QuotesClient {

    fun getQuoteById(@PathVariable id: String): HttpResponse<Flow<QuoteDTO?>>

    @Get("/pageable")
    fun getQuotes(pageable: Pageable): HttpResponse<Flow<Page<QuoteDTO>>>

    @Get("/authors/{author}")
    fun getQuotesByAuthor(@PathVariable author: String): HttpResponse<Flow<QuoteDTO>>

    @Get
    fun getQuotes(): HttpResponse<Flow<QuoteDTO>>
}