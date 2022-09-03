package me.melkopisi.githubreposlisting.features.reposlist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import me.melkopisi.domain.usecases.GetReposUseCase
import me.melkopisi.githubreposlisting.di.qualifiers.IOThread
import me.melkopisi.githubreposlisting.di.qualifiers.MainThread
import me.melkopisi.githubreposlisting.features.reposlist.models.GithubReposUiModel
import me.melkopisi.githubreposlisting.features.reposlist.models.mappers.mapToGithubReposDomainModel
import javax.inject.Inject

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@HiltViewModel
class ReposListViewModel @Inject constructor(
  private val useCase: GetReposUseCase,
  @IOThread private val ioThread: Scheduler,
  @MainThread private val mainThread: Scheduler
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  internal val screenStates by lazy { MutableLiveData<ReposListState>() }

  fun getRepos(pageNumber: Int) {
    useCase(pageNumber)
      .subscribeOn(ioThread)
      .doOnSubscribe { screenStates.value = ReposListState.Loading }
      .observeOn(mainThread)
      .subscribe({
        screenStates.value = ReposListState.Success(it.map { domainModel ->
          domainModel.mapToGithubReposDomainModel()
        })
      }, {
        screenStates.value = ReposListState.Fail(it.message ?: "Something went wrong.")
      })
      .addTo(compositeDisposable)
  }

  override fun onCleared() {
    compositeDisposable.clear()
  }
}

sealed class ReposListState {
  object Loading : ReposListState()
  class Success(uiModel: List<GithubReposUiModel>) : ReposListState()
  class Fail(msg: String) : ReposListState()
}