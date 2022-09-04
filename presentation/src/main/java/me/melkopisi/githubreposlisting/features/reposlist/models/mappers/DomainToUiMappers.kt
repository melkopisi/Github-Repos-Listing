package me.melkopisi.githubreposlisting.features.reposlist.models.mappers

import me.melkopisi.domain.models.GithubReposDomainModel
import me.melkopisi.githubreposlisting.features.reposlist.models.GithubReposUiModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */
fun GithubReposDomainModel.Owner.mapToOwnerDomainModel(): GithubReposUiModel.Owner =
  GithubReposUiModel.Owner(
    login = login,
  )

fun GithubReposDomainModel.mapToGithubReposDomainModel(): GithubReposUiModel =
  GithubReposUiModel(
    id = id,
    name = name,
    owner = owner.mapToOwnerDomainModel(),
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    visibility = visibility
  )