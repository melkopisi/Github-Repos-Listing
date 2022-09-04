package me.melkopisi.githubreposlisting.features.repodetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import me.melkopisi.githubreposlisting.MainActivity
import me.melkopisi.githubreposlisting.databinding.FragmentRepoDetailsBinding
import me.melkopisi.githubreposlisting.features.reposlist.models.GithubReposUiModel

/*
 * Authored by Kopisi on 04 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

  private var _binding: FragmentRepoDetailsBinding? = null
  private val binding get() = _binding!!

  private val repoDetails: GithubReposUiModel? by lazy { arguments?.getParcelable(ARG_REPO_DETAILS) }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupActionbar()
    initDetails()
  }

  private fun setupActionbar() {
    val actionBar = (requireActivity() as MainActivity).supportActionBar
    actionBar?.let {
      it.title = repoDetails?.name
      it.setDisplayHomeAsUpEnabled(true)
    }
  }

  private fun initDetails() {
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {
    const val ARG_REPO_DETAILS = "repo_details"
  }
}