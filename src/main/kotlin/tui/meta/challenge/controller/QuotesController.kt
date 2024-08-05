package tui.meta.challenge.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Produces
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tui.meta.challenge.model.QuoteDTO
import tui.meta.challenge.service.QuotesService

@Controller("/quotes")
@Produces(MediaType.APPLICATION_JSON)
class QuotesController(private val service: QuotesService) {

    @Get("/{id}")
    fun getQuoteById(@PathVariable id: String): HttpResponse<Mono<QuoteDTO?>> {
        val quote = service.findById(id)

        return quote.let { HttpResponse.ok(it) } ?: HttpResponse.notFound()
    }

    @Get("/authors/{author}")
    fun getQuotesByAuthor(@PathVariable author: String): HttpResponse<Flux<QuoteDTO>> {
        return service.findByAuthor(author).let { HttpResponse.ok(it) }
    }

    @Get
    fun getQuotes(): HttpResponse<Flux<QuoteDTO>> {
        return HttpResponse.ok(service.findAll())
    }


}