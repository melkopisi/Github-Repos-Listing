package me.melkopisi.githubreposlisting.features.reposlist.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import me.melkopisi.githubreposlisting.databinding.FragmentGithubReposListBinding
import me.melkopisi.githubreposlisting.features.reposlist.adapters.LoadingListCustomAdapter
import me.melkopisi.githubreposlisting.features.reposlist.viewmodels.ReposListState.Fail
import me.melkopisi.githubreposlisting.features.reposlist.viewmodels.ReposListState.FirstLoading
import me.melkopisi.githubreposlisting.features.reposlist.viewmodels.ReposListState.Loading
import me.melkopisi.githubreposlisting.features.reposlist.viewmodels.ReposListState.Success
import me.melkopisi.githubreposlisting.features.reposlist.viewmodels.ReposListViewModel
import me.melkopisi.githubreposlisting.general.EndlessRecyclerViewScrollListener

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@AndroidEntryPoint
class GithubReposListFragment : Fragment() {

  private var _binding: FragmentGithubReposListBinding? = null
  private val binding get() = _binding!!

  private val viewModel: ReposListViewModel by viewModels()

  private val adapter by lazy { LoadingListCustomAdapter() }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentGithubReposListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observeOnLiveData()
    viewModel.getRepos(resetPageNumber = true)
    setupRecyclerView()
  }

  private fun setupRecyclerView() {
    binding.reposRecyclerview.apply {
      adapter = this@GithubReposListFragment.adapter
      itemAnimator = null

      addOnScrollListener(object :
        EndlessRecyclerViewScrollListener(this.layoutManager as LinearLayoutManager) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
          viewModel.getRepos(resetPageNumber = false)
        }
      })
    }
  }

  private fun observeOnLiveData() {
    viewModel.screenStates.observe(viewLifecycleOwner) {
      when (it) {
        is FirstLoading -> setFirstLoading(true)
        is Loading -> {
        }
        is Success -> {
          setFirstLoading(false)
          // adapter.removeNull()
          adapter.setItems(it.uiModelList)
          // if (this::endlessScrolling.isInitialized) endlessScrolling.setLoaded()
        }
        is Fail -> {
          setFirstLoading(false)
          Log.e("error is", it.msg)
          Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
        }
      }
    }
  }

  private fun setFirstLoading(isLoading: Boolean) {
    binding.progressBar.isVisible = isLoading
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}