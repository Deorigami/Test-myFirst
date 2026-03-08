package app.tktn.attendees_check.di

import androidx.room.Room
import androidx.room.RoomDatabase
import app.tktn.attendees_check.db.NewsDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSUserDomainMask
import kotlinx.cinterop.ExperimentalForeignApi


actual val roomDatabaseModule: Module = module {
    single<NewsDatabase>{
      val dbFile = documentDirectory() + "news.db"
      Room
        .databaseBuilder<NewsDatabase>(dbFile)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
    }
  }

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
  val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = false,
    error = null,
  )
  return requireNotNull(documentDirectory?.path)
}