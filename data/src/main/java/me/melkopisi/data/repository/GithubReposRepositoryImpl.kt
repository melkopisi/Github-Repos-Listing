package me.melkopisi.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import me.melkopisi.data.local.GithubReposLocalDataSource
import me.melkopisi.data.local.entities.mappers.mapGithubRepoFromEntityToDomain
import me.melkopisi.data.local.entities.mappers.mapToGithubReposEntity
import me.melkopisi.data.remote.GithubReposRemoteDataSource
import me.melkopisi.data.remote.models.mappers.mapToGithubReposDomainModel
import me.melkopisi.domain.NetworkNotAvailableException
import me.melkopisi.domain.NoLocalDataException
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
      .flatMap {
        Completable.fromCallable {
          local.saveAllRepos(it.map { response ->
            response.mapToGithubReposEntity()
          })
        }.andThen(Single.just(it.map { response ->
          response.mapToGithubReposDomainModel()
        }))
      }.onErrorResumeNext { throwable ->
        if (throwable is NetworkNotAvailableException) {
          local.getAllRepos().map { entityList ->
            if (entityList.isNotEmpty()) {
              entityList.map { entity ->
                entity.mapGithubRepoFromEntityToDomain()
              }
            } else {
              throw NoLocalDataException()
            }
          }
        } else {
          throw NoLocalDataException()
        }
      }
  }
}