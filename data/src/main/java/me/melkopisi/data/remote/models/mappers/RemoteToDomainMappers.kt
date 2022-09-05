package me.melkopisi.data.remote.models.mappers

import me.melkopisi.data.remote.models.GithubReposResponse
import me.melkopisi.domain.models.GithubReposDomainModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

fun GithubReposResponse.Owner.mapToOwnerDomainModel(): GithubReposDomainModel.Owner =
  GithubReposDomainModel.Owner(login = login)

fun GithubReposResponse.mapToGithubReposDomainModel(): GithubReposDomainModel =
  GithubReposDomainModel(
    id = id,
    name = name,
    owner = owner.mapToOwnerDomainModel(),
    stargazersCount = stargazersCount,
    watchersCount = watchersCount,
    language = language,
    visibility = visibility
  )