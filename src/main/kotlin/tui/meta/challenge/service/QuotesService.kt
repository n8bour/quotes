package tui.meta.challenge.service

import tui.meta.challenge.model.QuoteDTO

interface QuotesService {

    fun findAll(): List<QuoteDTO>
    fun findById(id: String): QuoteDTO?
    fun findByAuthor(author: String): List<QuoteDTO>
}