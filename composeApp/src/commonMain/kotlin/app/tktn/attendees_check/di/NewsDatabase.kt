package app.tktn.attendees_check.di

import androidx.room.*
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import app.tktn.service_news.data.local.dao.NewsDao
import app.tktn.service_news.data.local.entity.NewsArticleEntity
import coil3.PlatformContext

@Database(entities = [NewsArticleEntity::class], version = 1)
@ConstructedBy(NewsDatabaseConstructor::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

