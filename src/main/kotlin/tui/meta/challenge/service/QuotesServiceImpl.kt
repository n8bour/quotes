package tui.meta.challenge.service

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import tui.meta.challenge.mapper.QuoteMapper.toDTO
import tui.meta.challenge.model.QuoteDTO
import tui.meta.challenge.repository.QuotesRepository

@Singleton
class QuotesServiceImpl(private val quotesRepository: QuotesRepository) : QuotesService {
    override fun findAll(): List<QuoteDTO> {
        return quotesRepository.findAll()
            .map { it.toDTO() }
    }

    override fun findAll(pageable: Pageable): Page<QuoteDTO> {
        return quotesRepository.findAll(pageable)
            .map { it.toDTO() }
    }

    override fun findById(id: String): QuoteDTO? {
        return quotesRepository.findById(id)
            .map { it.toDTO() }
            .orElse(null)
    }

    override fun findByAuthor(author: String): List<QuoteDTO> {
        return quotesRepository.findByQuoteAuthor(author)
            .map { it.toDTO() }
    }
}