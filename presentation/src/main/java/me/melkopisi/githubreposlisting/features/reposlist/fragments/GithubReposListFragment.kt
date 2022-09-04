package me.melkopisi.githubreposlisting.features.reposlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import me.melkopisi.data.extensions.isInternetAvailable
import me.melkopisi.githubreposlisting.MainActivity
import me.melkopisi.githubreposlisting.R
import me.melkopisi.githubreposlisting.databinding.FragmentGithubReposListBinding
import me.melkopisi.githubreposlisting.features.repodetails.RepoDetailsFragment
import me.melkopisi.githubreposlisting.features.reposlist.adapters.ReposAdapter
import me.melkopisi.githubreposlisting.features.reposlist.viewmodels.ReposListState.Fail
import me.melkopisi.githubreposlisting.features.reposlist.viewmodels.ReposListState.FirstLoading
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

  private val adapter by lazy { ReposAdapter() }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentGithubReposListBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel.getRepos()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupActionbar()
    observeOnLiveData()
    setupRecyclerView()
    setupItemNavigation()
  }

  private fun setupActionbar() {
    val actionBar = (requireActivity() as MainActivity).supportActionBar
    actionBar?.let {
      it.title = "Repos List"
      it.setDisplayHomeAsUpEnabled(false)
    }
  }

  private fun setupItemNavigation() {
    adapter.onItemClick {
      findNavController().navigate(
        R.id.action_repos_list_fragment_to_repoDetailsFragment,
        bundleOf(RepoDetailsFragment.ARG_REPO_DETAILS to it)
      )
    }
  }

  private fun setupRecyclerView() {
    binding.reposRecyclerview.apply {
      itemAnimator = null
      addOnScrollListener(object : EndlessRecyclerViewScrollListener(
        layoutManager as LinearLayoutManager
      ) {
        override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
          if (requireContext().isInternetAvailable()) viewModel.getRepos()
        }
      })
      adapter = this@GithubReposListFragment.adapter
    }
  }

  private fun observeOnLiveData() {
    viewModel.screenStates.observe(viewLifecycleOwner) {
      setFirstLoading(it is FirstLoading)
      when (it) {
        is Success -> {
          adapter.setItems(it.list)
        }
        is Fail -> {
          Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
        }
        else -> Unit
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