package app.tktn.attendees_check.di

import androidx.room.Room
import org.koin.dsl.module

actual val roomDatabaseModule: org.koin.core.module.Module = module {
	single<NewsDatabase> {
		Room.databaseBuilder(
			context = get(),
			NewsDatabase::class.java,
			"news_database"
		).build()
	}
}