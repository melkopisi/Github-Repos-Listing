package me.melkopisi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@Database(
  entities = [],
  version = 1,
  exportSchema = false
)
abstract class GithubReposDatabase : RoomDatabase() {
}