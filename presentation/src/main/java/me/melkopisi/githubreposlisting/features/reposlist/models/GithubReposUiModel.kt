package me.melkopisi.githubreposlisting.features.reposlist.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@Parcelize
data class GithubReposUiModel(
  val id: Int,
  val name: String,
  val owner: Owner,
  val stargazersCount: Int,
  val watchersCount: Int,
  val language: String?,
  val visibility: String,
) : Parcelable {
  @Parcelize
  data class Owner(
    val login: String,
  ) : Parcelable
}
