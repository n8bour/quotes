package tui.meta.challenge.service

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import kotlinx.coroutines.flow.Flow
import reactor.core.publisher.Flux
import tui.meta.challenge.model.QuoteDTO

interface QuotesService {

    fun findAll(): Flow<QuoteDTO>
    fun findById(id: String): Flow<QuoteDTO?>
    fun findByAuthor(author: String): Flow<QuoteDTO>
    fun findAllPageable(pageable: Pageable): Flow<Page<QuoteDTO>>
}