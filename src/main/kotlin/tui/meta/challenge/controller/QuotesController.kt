package tui.meta.challenge.controller

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import tui.meta.challenge.model.QuoteDTO
import tui.meta.challenge.service.QuotesService

@Controller("/quotes")
class QuotesController(private val service: QuotesService) {

    @Get("/{id}")
    fun getQuoteById(@PathVariable id: String): HttpResponse<QuoteDTO> {
        val quote = service.findById(id)

        return quote?.let { HttpResponse.ok(it) } ?: HttpResponse.notFound()
    }

    @Get("/authors/{author}")
    fun getQuotesByAuthor(@PathVariable author: String): HttpResponse<List<QuoteDTO>> {
        return service.findByAuthor(author).let { HttpResponse.ok(it) }
    }

    @Get("/pageable")
    fun getQuotes(pageable: Pageable): HttpResponse<Page<QuoteDTO>> {
        return HttpResponse.ok(service.findAll(pageable))
    }

    @Get
    fun getQuotes(): HttpResponse<List<QuoteDTO>> {
        return HttpResponse.ok(service.findAll())
    }


}