package io.github.afalalabarce.mvvmproject.datasource.cache.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.Instant
import java.util.*

class AppDatabaseConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(timeInMillis: Long?): Date? = try{
        if ((timeInMillis ?: 0L) == 0L)
            throw IllegalArgumentException()

        Calendar.getInstance().apply {
            setTimeInMillis(timeInMillis!!)
        }.time
    }catch (ex:Exception){
        null
    }

    @TypeConverter
    fun fromInstant(date: Instant?): Long? = date?.toEpochMilli()

    @TypeConverter
    fun toInstant(timeInMillis: Long?): Instant? = try{
        if ((timeInMillis ?: 0L) == 0L)
            throw IllegalArgumentException()

        Instant.ofEpochMilli(timeInMillis!!)
    }catch (ex:Exception){
        null
    }

    // TODO Add some useful transformations from / to, i.e., enums, etc.
}