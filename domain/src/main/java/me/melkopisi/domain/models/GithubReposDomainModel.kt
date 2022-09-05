package me.melkopisi.domain.models

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

data class GithubReposDomainModel(
  val id: Int,
  val name: String,
  val owner: Owner,
  val stargazersCount: Int,
  val watchersCount: Int,
  val language: String?,
  val visibility: String
) {
  data class Owner(
    val login: String,
  )
}