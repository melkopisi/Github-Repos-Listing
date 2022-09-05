package me.melkopisi.data.local

import io.reactivex.Single
import me.melkopisi.data.local.entities.GithubReposEntity
import me.melkopisi.domain.exceptions.NoLocalDataException
import javax.inject.Inject

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

class GithubReposLocalDataSource @Inject constructor(
  private val githubReposDao: GithubReposDao
) {

  fun saveAllRepos(repos: List<GithubReposEntity>) = githubReposDao.insertRepos(repos)
  fun getAllRepos(): Single<List<GithubReposEntity>> = Single.just(githubReposDao.getRepos())
    .map { it.ifEmpty { throw NoLocalDataException() } }
}