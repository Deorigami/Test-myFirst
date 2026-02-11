package app.tktn.feature_feed.landing.composable.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StoriesRow() {
	val viewModel = koinViewModel<StoriesModel>()
	val uiState by viewModel.uiState.collectAsStateWithLifecycle()
	LazyRow(
		modifier = Modifier.fillMaxWidth()
			.padding(vertical = 10.dp),
		contentPadding = PaddingValues(horizontal = 8.dp),
		horizontalArrangement = Arrangement.spacedBy(16.dp)
	) {
		item {
			// Your Story
			Column(horizontalAlignment = Alignment.CenterHorizontally) {
				Box(contentAlignment = Alignment.BottomEnd) {
					AsyncImage(
						model = "https://i.pravatar.cc/150?img=60",
						contentDescription = "Your Story",
						modifier = Modifier
							.size(70.dp)
							.clip(CircleShape)
							.border(
								2.dp,
								Color.LightGray,
								CircleShape
							),
						contentScale = ContentScale.Crop
					)
					Icon(
						imageVector = Icons.Default.AddCircle,
						contentDescription = "Add Story",
						tint = Color(0xFF1976D2), // Instagram blue-ish
						modifier = Modifier
							.size(24.dp)
							.background(Color.White, CircleShape)
							.border(
								2.dp,
								Color.White,
								CircleShape
							)
					)
				}
				Spacer(modifier = Modifier.height(4.dp))
				Text(
					text = "Your story",
					style = MaterialTheme.typography.bodySmall.copy(
						fontSize = 12.sp
					)
				)
			}
		}

		items(uiState.stories) { item ->
			StoryItem(item)
		}
	}
}

