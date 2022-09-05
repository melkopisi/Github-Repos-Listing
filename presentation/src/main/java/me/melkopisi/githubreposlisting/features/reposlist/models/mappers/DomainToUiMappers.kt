package me.melkopisi.githubreposlisting.features.reposlist.models.mappers

import me.melkopisi.domain.models.GithubReposDomainModel
import me.melkopisi.githubreposlisting.features.reposlist.models.GithubReposUiModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */
fun GithubReposDomainModel.Owner.mapToOwnerUiModel(): GithubReposUiModel.Owner =
  GithubReposUiModel.Owner(
    login = login,
  )

fun GithubReposDomainModel.mapToGithubReposUiModel(): GithubReposUiModel =
  GithubReposUiModel(
    id = id,
    name = name,
    owner = owner.mapToOwnerUiModel(),
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    visibility = visibility
  )