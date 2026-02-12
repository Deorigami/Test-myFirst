package app.tktn.attendees_check.di

import app.tktn.attendees_check.db.NewsDatabase
import app.tktn.core_service.connection.ServiceModule
import app.tktn.feature_bookmarks.di.FeatureBookmarksModule
import app.tktn.feature_headlines.di.FeatureHeadlinesModule
import app.tktn.feature_search.di.FeatureSearchModule
import app.tktn.service_news.ServiceNewsModule
import app.tktn.core_feature.di.CoreFeatureModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module(
	includes = [
		ServiceModule::class,
		ServiceNewsModule::class,
		FeatureBookmarksModule::class,
		FeatureHeadlinesModule::class,
		FeatureSearchModule::class,
		CoreFeatureModule::class
	]
)
@ComponentScan("app.tktn")
class RootAppModule {
	@Single
	fun providesNewsDao(newsDatabase: NewsDatabase) =
		newsDatabase.newsDao()

	@Single
	fun providesConnectivity() = createConnectivity()
}

expect val roomDatabaseModule: org.koin.core.module.Module