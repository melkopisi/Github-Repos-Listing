package me.melkopisi.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.melkopisi.data.local.entities.GithubReposEntity.Owner

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

class OwnerConverters {

  @TypeConverter
  fun fromJson(json: String?): Owner = Gson().fromJson(json, object : TypeToken<Owner>() {}.type)

  @TypeConverter
  fun toJson(owner: Owner): String = Gson().toJson(owner)
}