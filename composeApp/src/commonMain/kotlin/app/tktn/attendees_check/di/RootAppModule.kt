package app.tktn.attendees_check.di

import app.tktn.core_service.client.ServiceModule
import app.tktn.feature_feed.di.FeatureFeedModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(
	includes = [
		ServiceModule::class,
		FeatureFeedModule::class
	]
)
@ComponentScan("app.tktn")
class RootAppModule {
}