package me.melkopisi.githubreposlisting.features.reposlist.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import me.melkopisi.domain.DataRetrievingFailException
import me.melkopisi.domain.NetworkNotAvailableException
import me.melkopisi.domain.NoDataException
import me.melkopisi.domain.NoLocalDataException
import me.melkopisi.domain.models.GithubReposDomainModel
import me.melkopisi.domain.usecases.GetReposUseCase
import me.melkopisi.githubreposlisting.features.reposlist.adapters.AdapterItem.ItemRepo
import me.melkopisi.githubreposlisting.features.reposlist.models.mappers.mapToGithubReposUiModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/*
 * Authored by Kopisi on 04 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@RunWith(MockitoJUnitRunner::class)
class ReposListViewModelTest {
  @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

  private lateinit var viewModel: ReposListViewModel

  @Mock lateinit var useCase: GetReposUseCase

  @Mock lateinit var getReposLiveData: Observer<ReposListState>

  @Captor lateinit var getReposCaptor: ArgumentCaptor<ReposListState>

  @Before
  fun setup() {
    viewModel = ReposListViewModel(useCase, Schedulers.trampoline(), Schedulers.trampoline())
  }

  @After
  fun tearDown() {
    viewModel.screenStates.removeObserver(getReposLiveData)
  }

  @Test
  fun `when getRepos() then return success`() {

    viewModel.screenStates.observeForever(getReposLiveData)

    val domainOwner = GithubReposDomainModel.Owner("jack")
    val domainModel = GithubReposDomainModel(1, "abs.io", domainOwner, 3, 5, "java", "public")
    Mockito.lenient()
      .`when`(useCase(any()))
      .thenReturn(Single.just(listOf(domainModel)))

    viewModel.getRepos()

    Mockito.verify(getReposLiveData, Mockito.times(2))
      .onChanged(getReposCaptor.capture())

    val values = getReposCaptor.allValues
    assertEquals(ReposListState.FirstLoading, values[0])
    assertEquals(ReposListState.Success(mutableListOf(domainModel.mapToGithubReposUiModel()).map {
      ItemRepo(
        it
      )
    }.toMutableList()), values[1])
  }

  @Test
  fun `when getRepos() then return NetworkNotAvailableException`() {

    viewModel.screenStates.observeForever(getReposLiveData)

    val exception = NetworkNotAvailableException()
    Mockito.lenient()
      .`when`(useCase(any()))
      .thenReturn(Single.error(exception))

    viewModel.getRepos()

    Mockito.verify(getReposLiveData, Mockito.times(2))
      .onChanged(getReposCaptor.capture())
    val values = getReposCaptor.allValues
    assertEquals(ReposListState.FirstLoading, values[0])
    assertEquals(ReposListState.Fail(exception.message ?: ""), values[1])
  }

  @Test
  fun `when getRepos() then return DataRetrievingFailException`() {

    viewModel.screenStates.observeForever(getReposLiveData)

    val exception = DataRetrievingFailException()
    Mockito.lenient()
      .`when`(useCase(any()))
      .thenReturn(Single.error(exception))

    viewModel.getRepos()

    Mockito.verify(getReposLiveData, Mockito.times(2))
      .onChanged(getReposCaptor.capture())
    val values = getReposCaptor.allValues
    assertEquals(ReposListState.FirstLoading, values[0])
    assertEquals(ReposListState.Fail(exception.message ?: ""), values[1])
  }

  @Test
  fun `when getRepos() then return NoDataException`() {

    viewModel.screenStates.observeForever(getReposLiveData)

    val exception = NoDataException()
    Mockito.lenient()
      .`when`(useCase(any()))
      .thenReturn(Single.error(exception))

    viewModel.getRepos()

    Mockito.verify(getReposLiveData, Mockito.times(2))
      .onChanged(getReposCaptor.capture())
    val values = getReposCaptor.allValues
    assertEquals(ReposListState.FirstLoading, values[0])
    assertEquals(ReposListState.Fail(exception.message ?: ""), values[1])
  }

  @Test
  fun `when getRepos() then return NoLocalDataException`() {

    viewModel.screenStates.observeForever(getReposLiveData)

    val exception = NoLocalDataException()
    Mockito.lenient()
      .`when`(useCase(any()))
      .thenReturn(Single.error(exception))

    viewModel.getRepos()

    Mockito.verify(getReposLiveData, Mockito.times(2))
      .onChanged(getReposCaptor.capture())
    val values = getReposCaptor.allValues
    assertEquals(ReposListState.FirstLoading, values[0])
    assertEquals(ReposListState.Fail(exception.message ?: ""), values[1])
  }
}