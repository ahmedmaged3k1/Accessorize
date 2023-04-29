package com.example.araccessories.data.dataSource.localDataSource.entities

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters
class Converters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<String>? {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, listType)
    }


    @TypeConverter
    fun fromArrayList(list: ArrayList<String>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
    @TypeConverter
    fun fromPosition(position: Position?): String? {
        if (position == null) {
            return null
        }
        return "${position.x},${position.y},${position.z}"
    }

    @TypeConverter
    fun toPosition(positionString: String?): Position? {
        if (positionString == null) {
            return null
        }
        val parts = positionString.split(",")
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid Position String: $positionString")
        }
        return Position(parts[0].toFloat(), parts[1].toFloat(), parts[2].toFloat())
    }

    @TypeConverter
    fun fromScale(scale: Scale?): String? {
        if (scale == null) {
            return null
        }
        return "${scale.x},${scale.y},${scale.z}"
    }

    @TypeConverter
    fun toScale(scaleString: String?): Scale? {
        if (scaleString == null) {
            return null
        }
        val parts = scaleString.split(",")
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid Scale String: $scaleString")
        }
        return Scale(parts[0].toFloat(), parts[1].toFloat(), parts[2].toFloat())
    }

}