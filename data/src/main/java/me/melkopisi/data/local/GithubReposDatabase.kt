package me.melkopisi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.melkopisi.data.local.entities.GithubReposEntity

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@Database(entities = [GithubReposEntity::class], version = 1, exportSchema = false)
@TypeConverters(OwnerConverters::class)
abstract class GithubReposDatabase : RoomDatabase() {

  abstract fun githubReposDao(): GithubReposDao
}