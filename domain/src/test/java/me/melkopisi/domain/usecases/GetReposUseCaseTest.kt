package me.melkopisi.domain.usecases

import com.nhaarman.mockito_kotlin.any
import io.reactivex.Single
import me.melkopisi.domain.models.GithubReposDomainModel
import me.melkopisi.domain.repository.GithubReposRepository
import me.melkopisi.domain.utils.RxImmediateSchedulerRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/*
 * Authored by Kopisi on 04 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@RunWith(MockitoJUnitRunner::class)
class GetReposUseCaseTest {
  @get:Rule var instantExecutorRule = RxImmediateSchedulerRule()

  @Mock
  lateinit var repo: GithubReposRepository

  @Mock
  lateinit var model: GithubReposDomainModel

  @Test
  fun `GetReposUseCase invoke() call getReposUseCase`() {

    Mockito.`when`(repo.getRepos(any()))
      .thenReturn(Single.just(listOf(model, model)))

    val useCase = GetReposUseCase(repo)
    val resultObserver = useCase(1).test()

    resultObserver.assertComplete()
    resultObserver.dispose()
  }
}