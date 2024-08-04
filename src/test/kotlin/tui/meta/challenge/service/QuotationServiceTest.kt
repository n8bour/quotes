package tui.meta.challenge.repository

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension.getMock
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import tui.meta.challenge.model.Quote
import tui.meta.challenge.service.QuotesService
import tui.meta.challenge.utils.QuotesTestUtils.LIST_DATA
import java.util.*

@MicronautTest
class QuotationServiceTest(
    private val quotesService: QuotesService,
    private val quotesRepository: QuotesRepository
) : BehaviorSpec({
    given("QuotesService") {
        `when`("find all quotes") {
            then("returns a list of quotes") {
                val repository = getMock(quotesRepository)
                every { repository.findAll() } answers {
                    LIST_DATA
                }

                val result = quotesService.findAll()
                result.size shouldBe LIST_DATA.size
                result[0].quoteGenre shouldBe "someGenre"
            }
        }
        `when`("find all quotes (no quotes in repo)") {
            then("returns empty list") {
                val repository = getMock(quotesRepository)
                every { repository.findAll() } answers {
                    emptyList()
                }

                val result = quotesService.findAll()
                result.size shouldBe 0
            }
        }
        `when`("find by id is called with a valid id") {
            then("returns a quote") {
                val repository = getMock(quotesRepository)
                val id = "someId"
                val quote = LIST_DATA.first { quote -> quote._id == id }
                every { repository.findById(id) } answers {
                    Optional.of(quote)
                }
                val result = quotesService.findById(id)
                result shouldBe quote
            }
        }
        `when`("find by id is called with invalid id") {
            then("returns null") {
                val repository = getMock(quotesRepository)
                val id = "invalid_id"
                every { repository.findById(id) } answers {
                    Optional.ofNullable(LIST_DATA.firstOrNull { quote -> quote._id == id })
                }
                val result = quotesService.findById(id)
                result shouldBe null
            }
        }
        `when`("find by id no results") {
            then("returns null") {
                val repository = getMock(quotesRepository)
                every { repository.findById(any()) } answers {
                    Optional.ofNullable(null)
                }
                val result = quotesService.findById("valid_id")
                result shouldBe null
            }
        }
        `when`("find by author is called with a valid author") {
            then("returns a list of quotes filtered by author") {
                val repository = getMock(quotesRepository)
                val author = "someAuthor"
                val authorQuotes = LIST_DATA.filter { quote: Quote -> quote.quoteAuthor == author }
                every { repository.findByQuoteAuthor(author) } answers {
                    authorQuotes
                }
                val result = quotesService.findByAuthor(author)
                result.size shouldBe authorQuotes.size
            }
        }
        `when`("find by author is called with a invalid author") {
            then("returns a empty list") {
                val repository = getMock(quotesRepository)
                val author = "invalid author"
                every { repository.findByQuoteAuthor(author) } answers {
                    emptyList()
                }
                val result = quotesService.findByAuthor(author)
                result.size shouldBe 0
            }
        }
        `when`("find by author (empty results)") {
            then("returns empty list") {
                val repository = getMock(quotesRepository)
                every { repository.findByQuoteAuthor(any()) } answers {
                    emptyList()
                }
                val result = quotesService.findByAuthor("someAuthor")
                result.size shouldBe 0
            }
        }
    }

}) {
    @MockBean(QuotesRepository::class)
    fun mockedPostRepository() = mockk<QuotesRepository>()
}