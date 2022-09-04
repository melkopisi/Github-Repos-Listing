package me.melkopisi.githubreposlisting.features.reposlist.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import me.melkopisi.domain.models.GithubReposDomainModel
import me.melkopisi.domain.usecases.GetReposUseCase
import me.melkopisi.githubreposlisting.di.qualifiers.IOThread
import me.melkopisi.githubreposlisting.di.qualifiers.MainThread
import me.melkopisi.githubreposlisting.features.reposlist.adapters.BaseReposItem
import me.melkopisi.githubreposlisting.features.reposlist.adapters.BaseReposItem.ItemLoading
import me.melkopisi.githubreposlisting.features.reposlist.adapters.BaseReposItem.ItemReposUiModel
import me.melkopisi.githubreposlisting.features.reposlist.models.GithubReposUiModel
import me.melkopisi.githubreposlisting.features.reposlist.models.mappers.mapToGithubReposUiModel
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

  private var pageNumber = 1
  private var isLoadingEnabled = false
  private var isLastPage = false
  private var cachedList: MutableList<GithubReposUiModel> = mutableListOf()

  fun getRepos() {
    if (isLastPage) return
    if (isLoadingEnabled && pageNumber > 1) return
    if (pageNumber == 1) screenStates.value = ReposListState.FirstLoading
    isLoadingEnabled = true

    useCase(pageNumber)
      .subscribeOn(ioThread)
      .doOnSubscribe {
        if (pageNumber > 1 && !isLastPage) {
          screenStates.value = ReposListState.Success(cachedList.mapToLoadingAdapterModel())
        }
      }
      .observeOn(mainThread)
      .subscribe({
        if (it.isEmpty() || pageNumber == 9) isLastPage = true
        pageNumber++
        screenStates.value = ReposListState.Success(it.mapToAdapterModel())
        isLoadingEnabled = false
      }, {
        it.printStackTrace()
        screenStates.value = ReposListState.Fail(it.message ?: "Something went wrong.")
        isLoadingEnabled = false
      })
      .addTo(compositeDisposable)
  }

  private fun List<GithubReposDomainModel>.mapToAdapterModel(): MutableList<BaseReposItem> {
    cachedList.addAll(this.map { domainModel -> domainModel.mapToGithubReposUiModel() })
    return cachedList.map { uiModel -> ItemReposUiModel(uiModel) }.toMutableList()
  }

  private fun List<GithubReposUiModel>.mapToLoadingAdapterModel(): MutableList<BaseReposItem> {
    val list = mutableListOf<BaseReposItem>()
    this.onEach { uiModel ->
      list.add(ItemReposUiModel(uiModel))
    }
    list.add(list.size, ItemLoading)
    return list
  }

  override fun onCleared() {
    compositeDisposable.clear()
  }
}

sealed class ReposListState {
  object FirstLoading : ReposListState()
  data class Success(val list: MutableList<BaseReposItem>) : ReposListState()
  data class Fail(val msg: String) : ReposListState()
}