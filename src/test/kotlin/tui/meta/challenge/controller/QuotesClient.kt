package tui.meta.challenge.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tui.meta.challenge.model.QuoteDTO

@Client("/quotes")
interface QuotesClient {

    @Get("/{id}")
    fun getQuoteById(@PathVariable id: String): HttpResponse<Mono<QuoteDTO>>

    @Get("/authors/{author}")
    fun getQuotesByAuthor(@PathVariable author: String): HttpResponse<Flux<QuoteDTO>>

    @Get
    fun getQuotes(): HttpResponse<Flux<QuoteDTO>>
}