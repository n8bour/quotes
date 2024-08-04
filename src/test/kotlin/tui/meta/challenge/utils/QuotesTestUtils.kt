package tui.meta.challenge.utils

import tui.meta.challenge.model.Quote

object QuotesTestUtils {
    val LIST_DATA = listOf(
        Quote("someId", "someGenre", "someAuthor", "some quote", 0),
        Quote("someOtherId", "someOtherGenre", "someOtherAuthor", "some other quote", 0),
        Quote("someIdAuthor", "someOtherGenre", "someAuthor", "some other quote", 0),
    )
}