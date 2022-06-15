package com.androidproject.newsplanet1_0.repository.db

import androidx.room.TypeConverter
import com.androidproject.newsplanet1_0.model.Source

class Converters {
    @TypeConverter
    fun fromSource(source: Source) :String? {
        return source.name
    }
    @TypeConverter
    fun toSource(name: String) : Source {
        return Source(name, name)
    }
}