package app.tktn.attendees_check.db

import androidx.room.*
import app.tktn.service_news.data.local.dao.NewsDao
import app.tktn.service_news.data.local.entity.NewsArticleEntity

@Database(entities = [NewsArticleEntity::class], version = 1)
@ConstructedBy(NewsDatabaseConstructor::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

