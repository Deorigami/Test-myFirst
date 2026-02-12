package app.tktn.attendees_check.di

import androidx.room.RoomDatabase
import app.tktn.core_service.connection.ServiceModule
import app.tktn.feature_feed.di.FeatureFeedModule
import app.tktn.service_news.ServiceNewsModule
import app.tktn.feature_news.di.FeatureNewsModule
import coil3.PlatformContext
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.dsl.module

@Module(
	includes = [
		ServiceModule::class,
		FeatureFeedModule::class,
		ServiceNewsModule::class,
		FeatureNewsModule::class
	]
)
@ComponentScan("app.tktn")
class RootAppModule {
	@Single
	fun providesNewsDao(newsDatabase: NewsDatabase) = newsDatabase.newsDao()
}

expect val roomDatabaseModule : org.koin.core.module.Module