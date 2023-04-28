package com.avv2050soft.unsplashtool.presentation.adapters

import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.ItemCollectionPhotoBinding
import com.avv2050soft.unsplashtool.databinding.ItemPhotoBinding
import com.avv2050soft.unsplashtool.domain.models.collectionphotos.CollectionPhotoItem
import com.avv2050soft.unsplashtool.presentation.utils.toStringWithKNotation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class CollectionPhotosAdapter(
    private val onClick: (CollectionPhotoItem) -> Unit
) : PagingDataAdapter<CollectionPhotoItem, CollectionPhotoViewHolder>(
    CollectionPhotosDiffUtilCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionPhotoViewHolder {
        return CollectionPhotoViewHolder(
            ItemCollectionPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CollectionPhotoViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            item?.let {
                Glide
                    .with(imageViewCollectionPhoto.context)
                    .load(it.urls.small)
                    .into(imageViewCollectionPhoto)
                Glide
                    .with(imageViewCollectionAuthorAvatar.context)
                    .load(it.user.profileImage.medium)
                    .transform(CircleCrop())
                    .into(imageViewCollectionAuthorAvatar)
                textViewCollectionPhotoAuthorName.text = it.user.name
                textViewCollectionPhotoUserName.text = it.user.username
                val totalLikes = it.likes.toStringWithKNotation()
                textViewCollectionPhotoTotalLikeCount.text = totalLikes
                if (it.likedByUser){
                    imageViewCollectionPhotoLikeYesNo.setImageResource(R.drawable.like_yes)
                }else {
                    imageViewCollectionPhotoLikeYesNo.setImageResource(R.drawable.like_no)
                }
            }
            root.setOnClickListener {
                item?.let(onClick)
            }
        }
    }

}

class CollectionPhotosDiffUtilCallback : DiffUtil.ItemCallback<CollectionPhotoItem>() {
    override fun areItemsTheSame(
        oldItem: CollectionPhotoItem,
        newItem: CollectionPhotoItem
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: CollectionPhotoItem,
        newItem: CollectionPhotoItem
    ): Boolean = oldItem == newItem
}

class CollectionPhotoViewHolder(val binding: ItemCollectionPhotoBinding) :
    RecyclerView.ViewHolder(binding.root)