package tui.meta.challenge.controller

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension.getMock
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import tui.meta.challenge.QuotesClient
import tui.meta.challenge.model.Quote
import tui.meta.challenge.service.QuotesService
import tui.meta.challenge.utils.QuotesTestUtils.LIST_DATA

@MicronautTest
class QuotesControllerTest(
    private val quotesService: QuotesService,
    @Client private val client: QuotesClient
) : BehaviorSpec({

    given("QuotesController") {
        `when`("get all quotes") {
            then("returns a list of quotes") {
                val service = getMock(quotesService)
                every { service.findAll() } answers {
                    LIST_DATA
                }

                val result = client.getQuotes()
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe LIST_DATA.size
                result.body()[0].quoteGenre shouldBe "someGenre"
            }
        }
        `when`("get all quotes (no quotes available)") {
            then("returns empty list") {
                val service = getMock(quotesService)
                every { service.findAll() } answers {
                    emptyList()
                }

                val result = client.getQuotes()
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe 0
            }
        }
        `when`("get quotes by id is called with a valid id") {
            then("returns a quote with OK status") {
                val service = getMock(quotesService)
                val id = "someId"
                val quote = LIST_DATA.firstOrNull { quote -> quote._id == id }
                every { service.findById(id) } answers {
                    quote
                }
                val result = client.getQuoteById(id)
                result.status shouldBe HttpStatus.OK
                result.body() shouldBe quote
            }
        }
        `when`("get quotes by id is called with invalid id") {
            then("returns not found status") {
                val service = getMock(quotesService)
                val id = "invalid_id"
                every { service.findById(id) } answers {
                    LIST_DATA.firstOrNull { quote -> quote._id == id }
                }
                val result = client.getQuoteById(id)
                result.status shouldBe HttpStatus.NOT_FOUND
                result.body() shouldBe null
            }
        }
        `when`("get quotes by id no results") {
            then("returns a not found status") {
                val service = getMock(quotesService)
                every { service.findById(any()) } answers {
                    null
                }
                val result = client.getQuoteById("valid_id")
                result.status shouldBe HttpStatus.NOT_FOUND
                result.body() shouldBe null
            }
        }
        `when`("get quotes by author is called with a valid author") {
            then("returns a list of quotes filtered by author") {
                val service = getMock(quotesService)
                val author = "someAuthor"
                val authorQuotes = LIST_DATA.filter { quote: Quote -> quote.quoteAuthor == author }
                every { service.findByAuthor(author) } answers {
                    authorQuotes
                }
                val result = client.getQuotesByAuthor(author)
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe authorQuotes.size
            }
        }
        `when`("get quotes by author is called with a invalid author") {
            then("returns a empty list with status OK") {
                val service = getMock(quotesService)
                val author = "invalid author"
                every { service.findByAuthor(author) } answers {
                    emptyList()
                }
                val result = client.getQuotesByAuthor(author)
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe 0
            }
        }
        `when`("get quotes by author (empty results)") {
            then("returns empty list") {
                val service = getMock(quotesService)
                every { service.findByAuthor(any()) } answers {
                    emptyList()
                }
                val result = client.getQuotesByAuthor("some author")
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe 0
            }
        }
    }

}) {
    @MockBean(QuotesService::class)
    fun mockService() = mockk<QuotesService>()
}



