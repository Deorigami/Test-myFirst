package app.tktn.feature_feed.landing.composable.post

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.tktn.feature_feed.landing.model.Post
import coil3.compose.AsyncImage
import io.github.kdroidfilter.composemediaplayer.VideoPlayerSurface
import io.github.kdroidfilter.composemediaplayer.rememberVideoPlayerState

@Composable
fun PostItem(post: Post) {
    val totalPages = (if (post.hasVideo) 1 else 0) + post.images.size
    val pagerState = rememberPagerState(pageCount = { totalPages })
    val playerState = rememberVideoPlayerState()

    // Initialize video only once when the post is composed
    LaunchedEffect(post.videoUrl, post.hasVideo) {
        if (post.hasVideo && post.videoUrl != null) {
            playerState.openUri(post.videoUrl)
        }
    }

    // Autoplay logic if video is present on the current page
    LaunchedEffect(pagerState.currentPage, post.hasVideo) {
        if (post.hasVideo) {
            if (pagerState.currentPage == 0) {
                playerState.play()
            } else {
                playerState.pause()
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = post.userAvatar,
                    contentDescription = "User Avatar",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = post.username,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "Options"
            )
        }

        // Media Pager
        Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                beyondViewportPageCount = pagerState.pageCount,
            ) { page ->
                if (post.hasVideo && page == 0) {
                    VideoPlayerSurface(
                        playerState = playerState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(modifier = Modifier.fillMaxSize().clickable {
                            if (playerState.isPlaying) {
                                playerState.pause()
                            } else {
                                playerState.play()
                            }
                        })
                    }
                } else {
                    val imageIndex = if (post.hasVideo) page - 1 else page
                    AsyncImage(
                        model = post.images[imageIndex],
                        contentDescription = "Post Item $page",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        // Actions
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Like",
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Outlined.Send, // Placeholder for Comment
                    contentDescription = "Comment",
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Share",
                    modifier = Modifier.size(28.dp)
                )
            }

            if (totalPages > 1) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(totalPages) { iteration ->
                        val color = if (pagerState.currentPage == iteration)
                            MaterialTheme.colorScheme.primary
                        else
                            Color.LightGray
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.Favorite, // Placeholder for Bookmark
                contentDescription = "Save",
                tint = Color.Black,
                modifier = Modifier.size(28.dp).align(Alignment.CenterEnd)
            )
        }

        // Likes
        Text(
            text = "${post.likesCount} likes",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        // Caption
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("${post.username} ")
                }
                append(post.caption)
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 4.dp
            )
        )

        // Comments count
        if (post.commentCount > 0) {
            Text(
                text = "View all ${post.commentCount} comments",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        // Time
        Text(
            text = post.timeAgo,
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color.Gray,
                fontSize = 10.sp
            ),
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 2.dp
            )
        )
    }
}
