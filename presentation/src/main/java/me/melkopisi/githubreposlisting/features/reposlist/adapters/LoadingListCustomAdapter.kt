package me.melkopisi.githubreposlisting.features.reposlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import me.melkopisi.githubreposlisting.R
import me.melkopisi.githubreposlisting.databinding.ItemRepoBinding
import me.melkopisi.githubreposlisting.features.reposlist.adapters.LoadingListCustomAdapter.ReposViewHolder
import me.melkopisi.githubreposlisting.features.reposlist.models.GithubReposUiModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */

class LoadingListCustomAdapter : RecyclerView.Adapter<ReposViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ReposViewHolder(ItemRepoBinding.inflate(inflater, parent, false))
  }

  override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
    holder.bind(differ.currentList[position])
  }

  override fun getItemCount(): Int = differ.currentList.size

  class ReposViewHolder(private val binding: ItemRepoBinding) :
    ViewHolder(binding.root) {
    fun bind(item: GithubReposUiModel) {
      with(binding) {
        tvRepoTitle.text = item.name
        tvRepoOwner.text = item.owner.login
        tvRepoLikes.text = itemView.context.getString(R.string.stars_count, item.stargazersCount)
      }
    }
  }

  fun setItems(items: List<GithubReposUiModel?>) {
    differ.submitList(items)
  }

  private val diffCallback =
    object : DiffUtil.ItemCallback<GithubReposUiModel>() {
      override fun areItemsTheSame(
        oldItem: GithubReposUiModel,
        newItem: GithubReposUiModel
      ): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
        oldItem: GithubReposUiModel,
        newItem: GithubReposUiModel
      ): Boolean {
        return oldItem == newItem
      }
    }

  private val differ = AsyncListDiffer(this, diffCallback)
}