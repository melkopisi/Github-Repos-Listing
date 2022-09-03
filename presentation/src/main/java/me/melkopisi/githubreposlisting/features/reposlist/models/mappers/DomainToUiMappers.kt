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
    id = id,
    avatarUrl = avatarUrl,
    url = url
  )

fun GithubReposDomainModel.mapToGithubReposDomainModel(): GithubReposUiModel =
  GithubReposUiModel(
    id = id,
    name = name,
    isPrivateRepo = isPrivateRepo,
    owner = owner.mapToOwnerDomainModel(),
    description = description,
    fork = fork,
    url = url,
    size = size,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    visibility = visibility
  )