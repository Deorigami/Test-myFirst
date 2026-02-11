package app.tktn.feature_feed.landing
sealed interface FeedScreenEvent {
    data object RefreshData : FeedScreenEvent
}