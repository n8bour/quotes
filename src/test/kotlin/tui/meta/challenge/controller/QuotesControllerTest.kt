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
import tui.meta.challenge.service.QuotesServiceImpl

@MicronautTest
class QuotesControllerTest(
    private val quotesService: QuotesService,
    @Client private val client: QuotesClient
) : BehaviorSpec({
    given("No data is available to fetch") {
        `when`("QuotesController find all quotes is invoked") {
            then("returns empty list") {
                val mock = getMock(quotesService)
                every { mock.findAll() } answers {
                    emptyList()
                }

                val result = client.getQuotes()
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe 0
            }
        }
        `when`("QuotesController find quotes by id") {
            then("returns not found") {
                val mock = getMock(quotesService)
                every { mock.findBy(any()) } answers {
                    null
                }
                val result = client.getQuoteById("valid_id")
                result.status shouldBe HttpStatus.NOT_FOUND
                result.body() shouldBe null
            }
        }
        `when`("QuotesController find quotes by author") {
            then("returns empty list") {
                val mock = getMock(quotesService)
                every { mock.findByAuthor(any()) } answers {
                    emptyList()
                }
                val result = client.getQuotesByAuthor("some author")
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe 0
            }
        }
    }

    given("Data to fetch") {
        `when`("QuotesController find all quotes is called") {
            then("returns a list of quotes") {
                val mock = getMock(quotesService)
                every { mock.findAll() } answers {
                    LIST_DATA
                }

                val result = client.getQuotes()
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe LIST_DATA.size
                result.body()[0].quoteGenre shouldBe "someGenre"
            }
        }
        `when`("QuotesController find quotes by id is called with a valid id") {
            then("returns not found") {
                val mock = getMock(quotesService)
                val id = "someId"
                val quote =  LIST_DATA.firstOrNull { quote -> quote._id == id }
                every { mock.findBy(id) } answers {
                    quote
                }
                val result = client.getQuoteById(id)
                result.status shouldBe HttpStatus.OK
                result.body() shouldBe quote
            }
        }
        `when`("QuotesController find quotes by id is called with invalid id") {
            then("returns not found") {
                val mock = getMock(quotesService)
                val id = "invalid_id"
                every { mock.findBy(id) } answers {
                    LIST_DATA.firstOrNull { quote -> quote._id == id }
                }
                val result = client.getQuoteById(id)
                result.status shouldBe HttpStatus.NOT_FOUND
                result.body() shouldBe null
            }
        }
        `when`("QuotesController find quotes by author is called with a valid author") {
            then("returns a list of quotes filtered by author") {
                val mock = getMock(quotesService)
                val author = "someAuthor"
                val authorQuotes = LIST_DATA.filter { quote: Quote ->  quote.quoteAuthor == author }
                every { mock.findByAuthor(author) } answers {
                    authorQuotes
                }
                val result = client.getQuotesByAuthor(author)
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe authorQuotes.size
            }
        }
        `when`("QuotesController find quotes by author is called with a invalid author") {
            then("returns a empty list of quotes") {
                val mock = getMock(quotesService)
                val author = "invalid author"
                every { mock.findByAuthor(author) } answers {
                   emptyList()
                }
                val result = client.getQuotesByAuthor(author)
                result.status shouldBe HttpStatus.OK
                result.body().size shouldBe 0
            }
        }
    }

}) {

    @MockBean(QuotesServiceImpl::class)
    fun mathService(): QuotesService {
        return mockk()
    }

    companion object {
        val LIST_DATA = listOf(
            Quote("someId", "someGenre", "someAuthor", "some quote", 0),
            Quote("someOtherId", "someOtherGenre", "someOtherAuthor", "some other quote", 0),
            Quote("someIdAuthor", "someOtherGenre", "someAuthor", "some other quote", 0),
        )
    }
}



