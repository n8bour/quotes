package tui.meta.challenge.service

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import tui.meta.challenge.model.QuoteDTO

interface QuotesService {

    fun findAll(): List<QuoteDTO>
    fun findAll(pageable: Pageable): Page<QuoteDTO>
    fun findById(id: String): QuoteDTO?
    fun findByAuthor(author: String): List<QuoteDTO>
}