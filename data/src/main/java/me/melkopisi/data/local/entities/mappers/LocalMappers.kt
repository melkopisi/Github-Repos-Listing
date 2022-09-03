package me.melkopisi.data.local.entities.mappers

import me.melkopisi.data.local.entities.GithubReposEntity
import me.melkopisi.data.remote.models.GithubReposResponse
import me.melkopisi.domain.models.GithubReposDomainModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

fun GithubReposResponse.Owner.mapToOwnerEntity(): GithubReposEntity.Owner =
  GithubReposEntity.Owner(
    login = login,
    id = id,
    avatarUrl = avatarUrl,
    url = url
  )

fun GithubReposResponse.mapToGithubReposEntity(): GithubReposEntity =
  GithubReposEntity(
    id = id,
    name = name,
    isPrivateRepo = isPrivateRepo,
    owner = owner.mapToOwnerEntity(),
    description = description,
    fork = fork,
    url = url,
    size = size,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    visibility = visibility
  )

fun GithubReposEntity.Owner.mapOwnerFromEntityToDomain(): GithubReposDomainModel.Owner =
  GithubReposDomainModel.Owner(
    login = login,
    id = id,
    avatarUrl = avatarUrl,
    url = url
  )

fun GithubReposEntity.mapGithubRepoFromEntityToDomain(): GithubReposDomainModel =
  GithubReposDomainModel(
    id = id,
    name = name,
    isPrivateRepo = isPrivateRepo,
    owner = owner.mapOwnerFromEntityToDomain(),
    description = description,
    fork = fork,
    url = url,
    size = size,
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    visibility = visibility
  )