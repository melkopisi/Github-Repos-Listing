package me.melkopisi.data.remote.models.mappers

import me.melkopisi.data.remote.models.GithubReposResponse
import me.melkopisi.domain.models.GithubReposDomainModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

fun GithubReposResponse.Owner.mapToOwnerDomainModel(): GithubReposDomainModel.Owner =
  GithubReposDomainModel.Owner(
    login = login,
    id = id,
    avatarUrl = avatarUrl,
    url = url
  )

fun GithubReposResponse.mapToGithubReposDomainModel(): GithubReposDomainModel =
  GithubReposDomainModel(
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