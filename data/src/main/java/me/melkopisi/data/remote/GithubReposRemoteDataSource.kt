package me.melkopisi.data.remote

import io.reactivex.Single
import me.melkopisi.data.extensions.parseResponse
import me.melkopisi.data.network.api.GithubReposApi
import me.melkopisi.data.remote.models.GithubReposResponse
import me.melkopisi.domain.NoDataException
import javax.inject.Inject

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

class GithubReposRemoteDataSource @Inject constructor(
  private val githubReposApi: GithubReposApi
) {
  fun getRepos(
    user: String = "JakeWharton",
    pageNumber: Int,
    perPage: Int = 15
  ): Single<List<GithubReposResponse>> {
    return githubReposApi.getRepos(user = user, pageNumber = pageNumber, perPage = perPage)
      .parseResponse().map {
        it.ifEmpty {
          throw NoDataException()
        }
      }
  }
}