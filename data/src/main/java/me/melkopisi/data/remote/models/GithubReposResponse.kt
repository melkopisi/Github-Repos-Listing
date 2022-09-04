package me.melkopisi.data.remote.models

import com.google.gson.annotations.SerializedName

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */
data class GithubReposResponse(
  @SerializedName("id")
  val id: Int,
  @SerializedName("name")
  val name: String,
  @SerializedName("owner")
  val owner: Owner,
  @SerializedName("stargazers_count")
  val stargazersCount: Int,
  @SerializedName("watchers_count")
  val watchersCount: Int,
  @SerializedName("language")
  val language: String?,
  @SerializedName("visibility")
  val visibility: String,
) {
  data class Owner(
    @SerializedName("login")
    val login: String
  )
}