package me.melkopisi.githubreposlisting.features.reposlist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.melkopisi.githubreposlisting.databinding.FragmentGithubReposListBinding
import me.melkopisi.githubreposlisting.features.reposlist.viewmodels.ReposListViewModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

@AndroidEntryPoint
class GithubReposListFragment : Fragment() {

  private var _binding: FragmentGithubReposListBinding? = null
  private val binding get() = _binding!!

  private val viewModel: ReposListViewModel by viewModels()

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
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}