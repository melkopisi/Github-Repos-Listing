package me.melkopisi.domain.usecases

import io.reactivex.Single
import me.melkopisi.domain.models.GithubReposDomainModel
import me.melkopisi.domain.repository.GithubReposRepository
import javax.inject.Inject

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */
class GetReposUseCase @Inject constructor(
  private val reposRepository: GithubReposRepository
) {
  operator fun invoke(pageNumber: Int): Single<List<GithubReposDomainModel>> =
    reposRepository.getRepos(pageNumber)
}