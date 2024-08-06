package tui.meta.challenge.service

import io.micronaut.core.annotation.NonNull
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tui.meta.challenge.mapper.QuoteMapper.toDTO
import tui.meta.challenge.model.QuoteDTO
import tui.meta.challenge.repository.QuotesRepository

@Singleton
class QuotesServiceImpl(private val quotesRepository: QuotesRepository) : QuotesService {
    override fun findAll(): Flow<QuoteDTO> {
        return quotesRepository.findAll().asFlow()
            .map { it.toDTO() }
    }

    override fun findById(id: String): Flow<QuoteDTO?> {
        return quotesRepository.findById(id).asFlow()
            .map { it.toDTO() }
    }

    override fun findByAuthor(author: String): Flow<QuoteDTO> {
        return quotesRepository.findByQuoteAuthor(author).asFlow()
            .map { it.toDTO() }
    }

    override fun findAllPageable(pageable: Pageable): Flow<Page<QuoteDTO>> {
        return quotesRepository.findAll(pageable).asFlow()
            .map { it.map { it.toDTO() } }
    }
}