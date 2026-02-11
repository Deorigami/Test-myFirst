package app.tktn.feature_feed.di

import app.tktn.service_feed.ServiceFeedModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
	includes = [
		ServiceFeedModule::class
	]
)
@ComponentScan("app.tktn")
class FeatureFeedModule {
}