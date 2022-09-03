package me.melkopisi.data.network.api

import io.reactivex.Single
import me.melkopisi.data.remote.models.GithubReposResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

interface GithubReposApi {
  @GET("{user}/repos")
  fun getRepos(
    @Path("user") user: String,
    @Query("page") pageNumber: Int,
    @Query("per_page") perPage: Int
  ): Single<Response<List<GithubReposResponse>>>
}