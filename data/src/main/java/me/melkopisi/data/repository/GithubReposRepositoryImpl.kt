package me.melkopisi.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import me.melkopisi.data.local.GithubReposLocalDataSource
import me.melkopisi.data.local.entities.mappers.mapGithubRepoFromEntityToDomain
import me.melkopisi.data.local.entities.mappers.mapToGithubReposEntity
import me.melkopisi.data.remote.GithubReposRemoteDataSource
import me.melkopisi.data.remote.models.GithubReposResponse
import me.melkopisi.data.remote.models.mappers.mapToGithubReposDomainModel
import me.melkopisi.domain.exceptions.NetworkNotAvailableException
import me.melkopisi.domain.models.GithubReposDomainModel
import me.melkopisi.domain.repository.GithubReposRepository
import javax.inject.Inject

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

class GithubReposRepositoryImpl @Inject constructor(
  private val remote: GithubReposRemoteDataSource,
  private val local: GithubReposLocalDataSource
) : GithubReposRepository {

  override fun getRepos(pageNumber: Int): Single<List<GithubReposDomainModel>> {
    return remote.getRepos(pageNumber = pageNumber)
      .flatMap { repos -> repos.saveToDatabaseAndEmitRemoteData() }
      .onErrorResumeNext { throwable -> throwable.mapErrors() }
  }

  private fun List<GithubReposResponse>.saveToDatabaseAndEmitRemoteData(): Single<List<GithubReposDomainModel>> {
    return Completable.fromCallable {
      local.saveAllRepos(this.map { response ->
        response.mapToGithubReposEntity()
      })
    }.andThen(Single.just(this.map { response ->
      response.mapToGithubReposDomainModel()
    }))
  }

  private fun Throwable.mapErrors(): Single<List<GithubReposDomainModel>> {
    return if (this is NetworkNotAvailableException) {
      local.getAllRepos()
        .map { entityList -> entityList.map { entity -> entity.mapGithubRepoFromEntityToDomain() } }
    } else {
      throw this
    }
  }
}