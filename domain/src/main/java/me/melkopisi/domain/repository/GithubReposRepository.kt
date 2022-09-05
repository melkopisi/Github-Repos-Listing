package me.melkopisi.domain.repository

import io.reactivex.Single
import me.melkopisi.domain.models.GithubReposDomainModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

interface GithubReposRepository {
  fun getRepos(pageNumber: Int): Single<List<GithubReposDomainModel>>
}