package app.tktn.attendees_check.di

import androidx.room.Room
import app.tktn.attendees_check.db.NewsDatabase
import io.github.orioneee.AxerBundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual val roomDatabaseModule: org.koin.core.module.Module = module {
	single<NewsDatabase> {
		Room
			.databaseBuilder(
				context = get(),
				NewsDatabase::class.java,
				"news_database"
			)
			.setQueryCoroutineContext(Dispatchers.IO)
			.fallbackToDestructiveMigration(false)
			.build()
	}
}