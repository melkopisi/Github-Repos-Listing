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
  @SerializedName("private")
  val isPrivateRepo: Boolean,
  @SerializedName("owner")
  val owner: Owner,
  @SerializedName("description")
  val description: String,
  @SerializedName("fork")
  val fork: Boolean,
  @SerializedName("url")
  val url: String,
  @SerializedName("size")
  val size: Int,
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
    val login: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("url")
    val url: String
  )
}