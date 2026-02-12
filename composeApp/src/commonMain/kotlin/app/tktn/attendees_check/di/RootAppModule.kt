package app.tktn.attendees_check.di

import app.tktn.core_service.connection.ServiceModule
import app.tktn.feature_bookmarks.di.FeatureBookmarksModule
import app.tktn.feature_headlines.di.FeatureHeadlinesModule
import app.tktn.feature_news.di.FeatureNewsModule
import app.tktn.service_news.ServiceNewsModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(
	includes = [
		ServiceModule::class,
		ServiceNewsModule::class,
		FeatureNewsModule::class,
		FeatureBookmarksModule::class,
		FeatureHeadlinesModule::class,
	]
)
@ComponentScan("app.tktn")
class RootAppModule {
	@Single
	fun providesNewsDao(newsDatabase: NewsDatabase) =
		newsDatabase.newsDao()
}

expect val roomDatabaseModule: org.koin.core.module.Module