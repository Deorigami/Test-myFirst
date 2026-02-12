package app.tktn.attendees_check.di

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object NewsDatabaseConstructor :
	RoomDatabaseConstructor<NewsDatabase> {
	override fun initialize(): NewsDatabase
}