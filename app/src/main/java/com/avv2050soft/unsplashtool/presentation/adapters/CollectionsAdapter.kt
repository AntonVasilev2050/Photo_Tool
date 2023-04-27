package com.avv2050soft.unsplashtool.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.unsplashtool.databinding.ItemCollectionBinding
import com.avv2050soft.unsplashtool.domain.models.collections.CollectionsListItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class CollectionsAdapter(
    private val onClick: (CollectionsListItem) -> Unit
) : PagingDataAdapter<CollectionsListItem, CollectionViewHolder>(CollectionDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(
            ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(imageViewCollection.context)
                    .load(it.coverPhoto.urls.regular)
                    .into(imageViewCollection)
                Glide
                    .with(imageViewAvatar.context)
                    .load(it.user.profileImage.medium)
                    .transform(CircleCrop())
                    .into(imageViewAvatar)
                textViewTotalPotos.text = it.totalPhotos.toString()
                textViewTitle.text = it.title
                textViewDescription.text = it.description
                textViewAuthorName.text = it.user.name
                textViewUserName.text = it.user.username
            }
            root.setOnClickListener { onClick }
        }
    }
}

class CollectionDiffUtilCallback : DiffUtil.ItemCallback<CollectionsListItem>() {
    override fun areItemsTheSame(
        oldItem: CollectionsListItem,
        newItem: CollectionsListItem
    ): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CollectionsListItem,
        newItem: CollectionsListItem
    ): Boolean =
        oldItem == newItem
}

class CollectionViewHolder(val binding: ItemCollectionBinding) :
    RecyclerView.ViewHolder(binding.root)