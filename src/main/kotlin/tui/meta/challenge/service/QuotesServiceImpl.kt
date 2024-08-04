package tui.meta.challenge.service

import jakarta.inject.Singleton
import tui.meta.challenge.model.Quote
import tui.meta.challenge.repository.QuotesRepository

@Singleton
class QuotesServiceImpl(private val quotesRepository: QuotesRepository) : QuotesService {
    override fun findAll(): List<Quote> {
        return quotesRepository.findAll()
    }

    override fun findBy(id: String): Quote? {
        return quotesRepository.findById(id).orElse(null)
    }

    override fun findByAuthor(author: String): List<Quote> {
        return quotesRepository.findByQuoteAuthor(author)
    }
}