package me.melkopisi.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@Entity(tableName = "github_repos_table")
data class GithubReposEntity(
  @PrimaryKey(autoGenerate = false) val id: Int,
  val name: String,
  val isPrivateRepo: Boolean,
  val owner: Owner,
  val description: String,
  val fork: Boolean,
  val url: String,
  val size: Int,
  val stargazersCount: Int,
  val watchersCount: Int,
  val language: String?,
  val visibility: String,
) {
  data class Owner(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val url: String
  )
}