package tui.meta.challenge.service

import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tui.meta.challenge.mapper.QuoteMapper.toDTO
import tui.meta.challenge.model.QuoteDTO
import tui.meta.challenge.repository.QuotesRepository

@Singleton
class QuotesServiceImpl(private val quotesRepository: QuotesRepository) : QuotesService {
    override fun findAll(): Flux<QuoteDTO> {
        return quotesRepository.findAll()
            .map { it.toDTO() }
    }

    override fun findById(id: String): Mono<QuoteDTO?> {
        return quotesRepository.findById(id)
            .map { it.toDTO() }
            .switchIfEmpty(Mono.empty())
    }

    override fun findByAuthor(author: String): Flux<QuoteDTO> {
        return quotesRepository.findByQuoteAuthor(author)
            .map { it.toDTO() }
    }
}