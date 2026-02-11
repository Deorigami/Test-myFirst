package app.tktn.feature_profile.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.tktn.core_feature.base.BaseScreen
import coil3.compose.AsyncImage

object ProfileScreen : BaseScreen() {
    override val pageName: String?
        get() = this::class.simpleName

    @Composable
    override fun ComposeContent() {
        ProfileContent()
    }
}

@Composable
private fun ProfileContent() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = { ProfileTopBar(username = "jacob_w") }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(padding).fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            // Profile header section (spans full width)
            item(span = { GridItemSpan(3) }) {
                ProfileHeader()
            }

            // Bio section
            item(span = { GridItemSpan(3) }) {
                ProfileBio(
                    displayName = "Jacob West",
                    bio = "Digital goodies designer @pixsellz\nEverything is designed."
                )
            }

            // Edit Profile button
            item(span = { GridItemSpan(3) }) {
                EditProfileButton()
            }

            // Story highlights
            item(span = { GridItemSpan(3) }) {
                StoryHighlightsRow()
            }

            // Tab bar (Grid / Tagged)
            item(span = { GridItemSpan(3) }) {
                ProfileTabBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }

            // Photo grid
            items(18) { index ->
                PhotoGridItem(index = index)
            }
        }
    }
}

// region Top Bar
@Composable
private fun ProfileTopBar(username: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "Private",
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = username,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Switch Account",
                modifier = Modifier.size(20.dp)
            )
        }
        IconButton(onClick = {}) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
// endregion

// region Profile Header
@Composable
private fun ProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture
        AsyncImage(
            model = "https://i.pravatar.cc/300?img=60",
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(86.dp)
                .clip(CircleShape)
                .border(1.dp, Color.LightGray, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(24.dp))

        // Stats
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileStat(count = "54", label = "Posts")
            ProfileStat(count = "834", label = "Followers")
            ProfileStat(count = "162", label = "Following")
        }
    }
}

@Composable
private fun ProfileStat(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = count,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 13.sp,
                color = Color.DarkGray
            )
        )
    }
}
// endregion

// region Bio
@Composable
private fun ProfileBio(displayName: String, bio: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
    ) {
        Text(
            text = displayName,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = bio,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 14.sp
            )
        )
    }
}
// endregion

// region Edit Profile
@Composable
private fun EditProfileButton() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clickable { },
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.LightGray),
        color = Color.White
    ) {
        Text(
            text = "Edit Profile",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 7.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        )
    }
}
// endregion

// region Story Highlights
@Composable
private fun StoryHighlightsRow() {
    val highlights = listOf(
        "New" to null,
        "Friends" to 11,
        "Sport" to 22,
        "Design" to 33
    )

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(highlights.size) { index ->
            val (label, imgSeed) = highlights[index]
            HighlightItem(label = label, imageSeed = imgSeed)
        }
    }
}

@Composable
private fun HighlightItem(label: String, imageSeed: Int?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(68.dp)
    ) {
        if (imageSeed != null) {
            // Highlight with image
            AsyncImage(
                model = "https://i.pravatar.cc/150?img=$imageSeed",
                contentDescription = label,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            // "New" highlight with + icon
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add highlight",
                    modifier = Modifier.size(28.dp),
                    tint = Color.DarkGray
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
// endregion

// region Tab Bar
@Composable
private fun ProfileTabBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Column {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                if (selectedTab < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color.Black
                    )
                }
            },
            divider = {}
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { onTabSelected(0) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Grid",
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedTab == 0) Color.Black else Color.Gray
                    )
                }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { onTabSelected(1) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Tagged",
                        modifier = Modifier.size(24.dp),
                        tint = if (selectedTab == 1) Color.Black else Color.Gray
                    )
                }
            )
        }
        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 0.5.dp)
    }
}
// endregion

// region Photo Grid
@Composable
private fun PhotoGridItem(index: Int) {
    AsyncImage(
        model = "https://picsum.photos/seed/${index + 200}/400/400",
        contentDescription = "Post $index",
        modifier = Modifier
            .aspectRatio(1f)
            .background(Color.LightGray),
        contentScale = ContentScale.Crop
    )
}
// endregion
