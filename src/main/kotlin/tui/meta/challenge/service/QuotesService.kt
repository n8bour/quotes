package tui.meta.challenge.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tui.meta.challenge.model.QuoteDTO

interface QuotesService {

    fun findAll(): Flux<QuoteDTO>
    fun findById(id: String): Mono<QuoteDTO?>
    fun findByAuthor(author: String): Flux<QuoteDTO>
}