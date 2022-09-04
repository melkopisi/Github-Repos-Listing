package me.melkopisi.data.local.entities.mappers

import me.melkopisi.data.local.entities.GithubReposEntity
import me.melkopisi.data.remote.models.GithubReposResponse
import me.melkopisi.domain.models.GithubReposDomainModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

fun GithubReposResponse.Owner.mapToOwnerEntity(): GithubReposEntity.Owner =
  GithubReposEntity.Owner(login = login)

fun GithubReposResponse.mapToGithubReposEntity(): GithubReposEntity =
  GithubReposEntity(
    id = id,
    name = name,
    owner = owner.mapToOwnerEntity(),
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    visibility = visibility
  )

fun GithubReposEntity.Owner.mapOwnerFromEntityToDomain(): GithubReposDomainModel.Owner =
  GithubReposDomainModel.Owner(
    login = login,
  )

fun GithubReposEntity.mapGithubRepoFromEntityToDomain(): GithubReposDomainModel =
  GithubReposDomainModel(
    id = id,
    name = name,
    owner = owner.mapOwnerFromEntityToDomain(),
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    visibility = visibility
  )