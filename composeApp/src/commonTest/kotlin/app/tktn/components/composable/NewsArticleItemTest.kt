package app.tktn.components.composable

import androidx.compose.ui.test.*
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class NewsArticleItemTest {

    @Test
    fun testNewsArticleItemDisplaysContent() = runComposeUiTest {
        var clicked = false
        var bookmarked = false
        
        setContent {
            NewsArticleItem(
                title = "Test News Title",
                author = "John Doe",
                description = "This is a test description for the news article.",
                url = "https://example.com",
                urlToImage = null,
                publishedAt = "13 Feb 2026",
                content = "Full content here.",
                isBookmarked = false,
                onClick = { clicked = true },
                onBookmarkClick = { bookmarked = true }
            )
        }

        // Verify title is displayed
        onNodeWithText("Test News Title").assertExists()
        
        // Verify author is displayed
        onNodeWithText("John Doe").assertExists()
        
        // Verify description is displayed
        onNodeWithText("This is a test description for the news article.").assertExists()

        // Perform click and verify action
        onNodeWithText("Test News Title").performClick()
        assertTrue(clicked)
        
        // Verify bookmark button works
        onNodeWithContentDescription("Bookmark").performClick()
        
        // Check if actions were triggered
        assertTrue(bookmarked)
    }

    @Test
    fun testNewsArticleItemBookmarkState() = runComposeUiTest {
        setContent {
            NewsArticleItem(
                title = "Test News Title",
                author = "John Doe",
                description = "Desc",
                url = "https://example.com",
                urlToImage = null,
                publishedAt = "13 Feb 2026",
                content = null,
                isBookmarked = true, // Bookmarked state
                onClick = { },
                onBookmarkClick = { }
            )
        }

        // Content description should be "Unbookmark" when isBookmarked is true
        onNodeWithContentDescription("Unbookmark").assertExists()
    }
}
