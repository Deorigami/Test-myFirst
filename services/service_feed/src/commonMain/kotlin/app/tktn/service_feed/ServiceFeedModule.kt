package app.tktn.service_feed

import app.tktn.service_feed.data.FeatureFeedService
import app.tktn.service_feed.data.createFeatureFeedService
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("app.tktn")
class ServiceFeedModule {
	@Single
	fun createFeatureFeedService(ktorfit: Ktorfit) : FeatureFeedService = ktorfit.createFeatureFeedService()
}