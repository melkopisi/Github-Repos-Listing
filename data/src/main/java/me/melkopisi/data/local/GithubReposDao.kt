package me.melkopisi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.melkopisi.data.local.entities.GithubReposEntity

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@Dao
interface GithubReposDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertRepos(repos: List<GithubReposEntity>)

  @Query("SELECT * FROM github_repos_table")
  fun getRepos(): List<GithubReposEntity>
}