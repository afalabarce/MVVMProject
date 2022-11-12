package io.github.afalalabarce.mvvmproject.datasource.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import io.github.afalalabarce.mvvmproject.datasource.cache.dao.AppDatabaseDao
import io.github.afalalabarce.mvvmproject.datasource.cache.db.converter.AppDatabaseConverter
import io.github.afalalabarce.mvvmproject.datasource.cache.model.CacheExampleEntity

/* TODO Needed by room migrations
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
*/

@TypeConverters(AppDatabaseConverter::class)
@Database(entities = [CacheExampleEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun dao(): AppDatabaseDao

    companion object{
        const val DATABASE_NAME = "app_database.db"
        val migrations: List<Migration> = listOf(
            /*
            object : Migration(1, 2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // TODO Create some alter/create table, if needed
                    database.execSQL("")
                }
            },
            // Some Migrations [...]
             */
        )
    }
}