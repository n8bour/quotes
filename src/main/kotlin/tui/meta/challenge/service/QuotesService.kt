package tui.meta.challenge.service

import tui.meta.challenge.model.Quote

interface QuotesService {

    fun findAll(): List<Quote>
    fun findById(id: String): Quote?
    fun findByAuthor(author: String): List<Quote>
}