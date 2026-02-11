package app.tktn.feature_feed.landing.composable.stories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.tktn.service_feed.domain.entity.StoryEntity
import coil3.compose.AsyncImage

@Composable
fun StoryItem(
	item: StoryEntity
) {
	val gradient = Brush.linearGradient(
		colors = listOf(
			Color(0xFFFBAA47),
			Color(0xFFD91A46),
			Color(0xFFA60F93)
		)
	)

	Column(horizontalAlignment = Alignment.CenterHorizontally) {
		Box(
			modifier = Modifier
				.size(74.dp) // Outer ring size
				.padding(2.dp) // Spacing for border
				.clip(CircleShape)
				.background(gradient) // Gradient Border
				.padding(2.5.dp) // Inner spacing (white gap)
				.background(
					Color.White,
					CircleShape
				) // White background behind image
		) {
			AsyncImage(
				model = item.image,
				contentDescription = "Story",
				modifier = Modifier
					.fillMaxSize()
					.clip(CircleShape),
				contentScale = ContentScale.Crop
			)
		}
		Spacer(modifier = Modifier.height(4.dp))
		Text(
			text = item.user,
			style = MaterialTheme.typography.bodySmall.copy(
				fontSize = 12.sp
			)
		)
	}
}