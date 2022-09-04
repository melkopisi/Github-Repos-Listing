package me.melkopisi.githubreposlisting.features.reposlist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import me.melkopisi.githubreposlisting.R
import me.melkopisi.githubreposlisting.databinding.ItemLoadingBinding
import me.melkopisi.githubreposlisting.databinding.ItemRepoBinding
import me.melkopisi.githubreposlisting.features.reposlist.adapters.BaseReposItem.ItemReposUiModel
import me.melkopisi.githubreposlisting.features.reposlist.models.GithubReposUiModel

/*
 * Authored by Kopisi on 03 Sep, 2022.
 * Contact Me : m.elkopisi@gmail.com
 */
class ReposAdapter :
  RecyclerView.Adapter<ViewHolder>() {

  private val diffCallback =
    object : DiffUtil.ItemCallback<BaseReposItem>() {
      override fun areItemsTheSame(
        oldItem: BaseReposItem,
        newItem: BaseReposItem
      ): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
      }

      override fun areContentsTheSame(
        oldItem: BaseReposItem,
        newItem: BaseReposItem
      ): Boolean {
        return oldItem == newItem
      }
    }

  private val differ = AsyncListDiffer(this, diffCallback)

  fun setItems(items: List<BaseReposItem>) {
    differ.submitList(items)
  }

  override fun getItemViewType(position: Int): Int {
    return if (differ.currentList[position] is ItemReposUiModel) 0 else 1
  }

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

  class LoadingViewHolder(binding: ItemLoadingBinding) : ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return if (viewType == 0) {
      ReposViewHolder(ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    } else {
      LoadingViewHolder(
        ItemLoadingBinding.inflate(
          LayoutInflater.from(parent.context),
          parent,
          false
        )
      )
    }
  }

  override fun getItemCount(): Int = differ.currentList.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (holder is ReposViewHolder) {
      holder.bind((differ.currentList[position] as ItemReposUiModel).item)
    }
  }
}

sealed class BaseReposItem {
  data class ItemReposUiModel(val item: GithubReposUiModel) : BaseReposItem()
  object ItemLoading : BaseReposItem()
}