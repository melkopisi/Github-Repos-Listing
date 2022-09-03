package me.melkopisi.githubreposlisting.features.reposlist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import me.melkopisi.domain.usecases.GetReposUseCase
import me.melkopisi.githubreposlisting.features.reposlist.models.GithubReposUiModel
import me.melkopisi.githubreposlisting.features.reposlist.models.mappers.mapToGithubReposDomainModel
import javax.inject.Inject

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@HiltViewModel
class ReposListViewModel @Inject constructor(
  private val useCase: GetReposUseCase
) : ViewModel() {

  private val compositeDisposable = CompositeDisposable()

  internal val screenStates by lazy { MutableLiveData<ReposListState>() }

  fun getRepos(pageNumber: Int) {
    useCase(pageNumber)
      .subscribeOn(Schedulers.io())
      .doOnSubscribe { screenStates.value = ReposListState.Loading }
      .observeOn(AndroidSchedulers.mainThread())
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