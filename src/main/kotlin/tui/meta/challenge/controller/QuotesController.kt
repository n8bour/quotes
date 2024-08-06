package tui.meta.challenge.controller

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import kotlinx.coroutines.flow.Flow
import tui.meta.challenge.model.QuoteDTO
import tui.meta.challenge.service.QuotesService

@Controller("/quotes")
class QuotesController(private val service: QuotesService) {

    @Get("/{id}")
    fun getQuoteById(@PathVariable id: String): HttpResponse<Flow<QuoteDTO?>> {
        val quote = service.findById(id)

        return quote.let { HttpResponse.ok(it) } ?: HttpResponse.notFound()
    }

    @Get("/pageable")
    fun getQuotes(pageable: Pageable): HttpResponse<Flow<Page<QuoteDTO>>> {
        return HttpResponse.ok(service.findAllPageable(pageable))
    }

    @Get("/authors/{author}")
    fun getQuotesByAuthor(@PathVariable author: String): HttpResponse<Flow<QuoteDTO>> {
        return service.findByAuthor(author).let { HttpResponse.ok(it) }
    }

    @Get
    fun getQuotes(): HttpResponse<Flow<QuoteDTO>> {
        return HttpResponse.ok(service.findAll())
    }


}