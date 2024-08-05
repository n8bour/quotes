package tui.meta.challenge.controller

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension.getMock
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import tui.meta.challenge.entity.Quote
import tui.meta.challenge.mapper.QuoteMapper.toDTO
import tui.meta.challenge.model.QuoteDTO
import tui.meta.challenge.repository.QuotesRepository

@MicronautTest
class QuotesControllerTest(
    private val quotesRepository: QuotesRepository,
    @Client private val client: QuotesClient
) : BehaviorSpec({

    given("QuotesController") {
        `when`("get all quotes") {
            then("returns a list of quotes") {
                val repository = getMock(quotesRepository)
                every { repository.findAll() } answers {
                    LIST_DATA
                }

                val result = client.getQuotes()
                result.status shouldBe HttpStatus.OK
                val body = result.body()!!
                body shouldBe LIST_DATA
                //result.body()[0].genre shouldBe "someGenre"
            }
        }
        `when`("get all quotes (no quotes available)") {
            then("returns empty list") {
                val repository = getMock(quotesRepository)
                every { repository.findAll() } answers {
                    Flux.empty<Quote>()
                }

                val result = client.getQuotes()
                validateEmptyResponse(result)
            }
        }
        `when`("get quotes by id with a valid id") {
            then("returns a quote with OK status") {
                val repository = getMock(quotesRepository)
                val id = "someId"
                val quote = LIST_DATA.filter { quote -> quote.id == id }.blockFirst()!!
                every { repository.findById(id) } answers {
                    Mono.just(quote)
                }
                val result = client.getQuoteById(id)
                result.status shouldBe HttpStatus.OK
                result.body() shouldBe quote.toDTO()
            }
        }
        `when`("get quotes by id with invalid id") {
            then("returns not found status") {
                val repository = getMock(quotesRepository)
                val id = "invalid_id"
                every { repository.findById(id) } answers {
                    Mono.empty()
                }
                val result = client.getQuoteById(id)
                validateNotFoundResponse(result)
            }
        }
        `when`("get quotes by id no results") {
            then("returns a not found status") {
                val repository = getMock(quotesRepository)
                every { repository.findById(any()) } answers {
                    Mono.empty()
                }
                val result = client.getQuoteById("valid_id")
                validateNotFoundResponse(result)
            }
        }
        `when`("get quotes by author is called with a valid author") {
            then("returns a list of quotes filtered by author") {
                val repository = getMock(quotesRepository)
                val author = "someAuthor"
                val authorQuotes = LIST_DATA.filter { quote: Quote -> quote.quoteAuthor == author }
                every { repository.findByQuoteAuthor(author) } answers {
                    authorQuotes
                }
                val result = client.getQuotesByAuthor(author)
                result.status shouldBe HttpStatus.OK
                //  result.body().size shouldBe authorQuotes.size
            }
        }
        `when`("get quotes by author is called with a invalid author") {
            then("returns a empty list with status OK") {
                val repository = getMock(quotesRepository)
                val author = "invalid author"
                every { repository.findByQuoteAuthor(author) } answers {
                    Flux.empty<Quote>()
                }
                val result = client.getQuotesByAuthor(author)
                validateEmptyResponse(result)
            }
        }
        `when`("get quotes by author (empty results)") {
            then("returns empty list") {
                val repository = getMock(quotesRepository)
                every { repository.findByQuoteAuthor(any()) } answers {
                    Flux.empty<Quote>()
                }
                val result = client.getQuotesByAuthor("some author")
                validateEmptyResponse(result)
            }
        }
    }

}) {
    @MockBean(QuotesRepository::class)
    fun mockedPostRepository() = mockk<QuotesRepository>()

    companion object {
        val LIST_DATA: Flux<Quote> = Flux.just(
            Quote("someId", "someGenre", "someAuthor", "some quote", 0),
            Quote("someOtherId", "someOtherGenre", "someOtherAuthor", "some other quote", 0),
            Quote("someIdAuthor", "someOtherGenre", "someAuthor", "some other quote", 0),
        )


        fun validateEmptyResponse(result: HttpResponse<Flux<QuoteDTO>>) {
            result.status shouldBe HttpStatus.OK
            //result.body().size shouldBe 0
        }

        fun validateNotFoundResponse(result: HttpResponse<Mono<QuoteDTO>>) {
            result.status shouldBe HttpStatus.NOT_FOUND
            result.body() shouldBe null
        }
    }
}



