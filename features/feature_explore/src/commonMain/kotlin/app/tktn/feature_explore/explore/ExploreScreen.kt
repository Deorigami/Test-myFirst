package app.tktn.feature_explore.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.tktn.core_feature.base.BaseScreen
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.AsyncImage
import org.koin.compose.koinInject
import kotlin.random.Random

object ExploreScreen : BaseScreen() {
    @Composable
    override fun ComposeContent() {
        ExploreContent()
    }
}

@Composable
private fun ExploreContent() {
    val exploreItems = remember { generateExploreItems(200) }

    // Build rows: pattern cycles every 4 rows
    // Row 0: MosaicRowLeft  (5 items: 1 tall + 4 small)
    // Row 1: ThreeSmallRow  (3 items)
    // Row 2: MosaicRowRight (5 items: 4 small + 1 tall)
    // Row 3: ThreeSmallRow  (3 items)
    val rows = remember(exploreItems) {
        val result = mutableListOf<Pair<Int, List<ExploreItem>>>()
        var cursor = 0
        var patternIndex = 0
        while (cursor < exploreItems.size) {
            val needed = if (patternIndex % 2 == 0) 5 else 3
            if (cursor + needed > exploreItems.size) break
            result.add(patternIndex % 4 to exploreItems.subList(cursor, cursor + needed))
            cursor += needed
            patternIndex++
        }
        result
    }

    Scaffold(
        topBar = {
            Column {
                ExploreSearchBar()
                CategoryChips()
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
        ) {
            items(rows.size) { index ->
                val (patternIndex, chunk) = rows[index]

                when (patternIndex) {
                    0 -> MosaicRowLeft(chunk)
                    2 -> MosaicRowRight(chunk)
                    else -> ThreeSmallRow(chunk)
                }

                // Add spacing
                Spacer(modifier = Modifier.height(1.dp))
            }
        }
    }
}

@Composable
private fun MosaicRowLeft(items: List<ExploreItem>) {
    // Tall vertical item on the left, 4 small squares (2 columns × 2 rows) on the right
    Row(
        modifier = Modifier.fillMaxWidth().height(250.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        // Tall Item (Left, 1/3 width, full height)
        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
            ExploreGridItem(items[0])
        }

        // 4 Small Items (Right, 2/3 width, 2 columns × 2 rows)
        Column(
            modifier = Modifier.weight(2f).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Box(modifier = Modifier.weight(1f).fillMaxSize()) { ExploreGridItem(items[1]) }
                Box(modifier = Modifier.weight(1f).fillMaxSize()) { ExploreGridItem(items[2]) }
            }
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Box(modifier = Modifier.weight(1f).fillMaxSize()) { ExploreGridItem(items[3]) }
                Box(modifier = Modifier.weight(1f).fillMaxSize()) { ExploreGridItem(items[4]) }
            }
        }
    }
}

@Composable
private fun MosaicRowRight(items: List<ExploreItem>) {
    // 4 small squares (2 columns × 2 rows) on the left, tall vertical item on the right
    Row(
        modifier = Modifier.fillMaxWidth().height(250.dp),
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        // 4 Small Items (Left, 2/3 width, 2 columns × 2 rows)
        Column(
            modifier = Modifier.weight(2f).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Box(modifier = Modifier.weight(1f).fillMaxSize()) { ExploreGridItem(items[0]) }
                Box(modifier = Modifier.weight(1f).fillMaxSize()) { ExploreGridItem(items[1]) }
            }
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Box(modifier = Modifier.weight(1f).fillMaxSize()) { ExploreGridItem(items[2]) }
                Box(modifier = Modifier.weight(1f).fillMaxSize()) { ExploreGridItem(items[3]) }
            }
        }

        // Tall Item (Right, 1/3 width, full height)
        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
            ExploreGridItem(items[4])
        }
    }
}

@Composable
private fun ThreeSmallRow(items: List<ExploreItem>) {
    Row(
        modifier = Modifier.fillMaxWidth().height(125.dp), // Half height of Large row
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items.forEach { item ->
             Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                 ExploreGridItem(item)
             }
        }
    }
}

@Composable
private fun ExploreGridItem(item: ExploreItem) {
    Box(modifier = Modifier.fillMaxSize().background(Color.LightGray)) {
        AsyncImage(
            model = "https://picsum.photos/seed/${item.id}/600/600",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        if (item.isVideo) {
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = "Video",
                tint = Color.White,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
            )
        }
        if (item.isCarousel) {
             Box(modifier = Modifier.align(Alignment.TopEnd).padding(8.dp).size(12.dp).background(Color.White))
        }
    }
}

@Composable
private fun ExploreSearchBar() {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        color = Color(0xFFEFEFEF),
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            var text by remember { mutableStateOf("") }
            BasicTextField(
                value = text,
                onValueChange = { text = it },
                singleLine = true,
                cursorBrush = SolidColor(Color.Black),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier, contentAlignment = Alignment.CenterStart){
						if (text.isEmpty()) {
							Text("Search", color = Color.Gray, fontSize = 16.sp)
						}
						innerTextField()
					}
                },
                modifier = Modifier.weight(1f).height(24.dp)
            )
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Scan",
                tint = Color.Gray,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun CategoryChips() {
    val categories = listOf("IGTV", "Shop", "Style", "Sports", "Auto", "Decor", "Travel", "Art")
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories.size) { index ->
            Surface(
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (index == 0) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                    } else if (index == 1) {
                         Icon(Icons.Outlined.ShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
                         Spacer(modifier = Modifier.width(4.dp))
                    }
                    Text(
                        text = categories[index],
                        style = MaterialTheme.typography.labelLarge.copy(color = Color.Black)
                    )
                }
            }
        }
    }
}

data class ExploreItem(val id: Int, val isVideo: Boolean = false, val isCarousel: Boolean = false)

private fun generateExploreItems(count: Int): List<ExploreItem> {
    return List(count) { i ->
        ExploreItem(
            id = i, 
            isVideo = i % 7 == 0,
            isCarousel = i % 5 == 0
        )
    }
}

