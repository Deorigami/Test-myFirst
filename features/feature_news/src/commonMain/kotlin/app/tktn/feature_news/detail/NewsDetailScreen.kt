package app.tktn.feature_news.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.tktn.core_feature.base.BaseScreen
import app.tktn.service_news.domain.entity.NewsArticle
import app.tktn.service_news.domain.repository.NewsRepository
import app.tktn.core_feature.navigation.LocalNavStack
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

data class NewsDetailScreen(val article: NewsArticle) : BaseScreen() {
    
    @OptIn(ExperimentalMaterial3Api::class)
	@Composable
    override fun ComposeContent() {
        val repository: NewsRepository = koinInject()
        val navStack = LocalNavStack.current
        val scope = rememberCoroutineScope()
        var isBookmarked by remember { mutableStateOf(article.isBookmarked) }
        
        // Ensure bookmark state is consistent with DB
        LaunchedEffect(article.url) {
            isBookmarked = repository.isBookmarked(article.url)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("News Detail", style = MaterialTheme.typography.titleMedium) },
                    navigationIcon = {
                        IconButton(onClick = { navStack.removeLastOrNull() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                repository.toggleBookmark(article)
                                isBookmarked = !isBookmarked
                            }
                        }) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Toggle Bookmark",
                                tint = if (isBookmarked) MaterialTheme.colorScheme.primary else LocalContentColor.current
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                article.urlToImage?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = article.author ?: "Unknown",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "·",
                            style = MaterialTheme.typography.labelMedium
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = article.publishedAt,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = article.description ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = article.content ?: "",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
