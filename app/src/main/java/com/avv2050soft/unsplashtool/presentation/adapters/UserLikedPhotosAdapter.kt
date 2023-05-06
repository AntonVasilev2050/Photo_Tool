package com.avv2050soft.unsplashtool.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.ItemPhotoBinding
import com.avv2050soft.unsplashtool.domain.models.userlikedphotos.UserLikedPhotoItem
import com.avv2050soft.unsplashtool.presentation.utils.toStringWithKNotation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class UserLikedPhotosAdapter(
    private val onClick: (UserLikedPhotoItem) -> Unit
) : PagingDataAdapter<UserLikedPhotoItem, UserLikedPhotoViewHolder>(UserLikedPhotoDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserLikedPhotoViewHolder {
        return UserLikedPhotoViewHolder(
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserLikedPhotoViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(imageViewPhoto.context)
                    .load(it.urls.small)
                    .placeholder(R.drawable.loading_icon)
                    .thumbnail(Glide.with(imageViewPhoto.context).load(R.drawable.loading_icon))
                    .error(R.drawable.error_sing)
                    .into(imageViewPhoto)
                Glide
                    .with(imageViewAvatar.context)
                    .load(it.user.profileImage.medium)
                    .transform(CircleCrop())
                    .into(imageViewAvatar)
                textViewAuthorName.text = it.user.name
                textViewUserName.text = it.user.username
                val totalLikes = it.likes.toStringWithKNotation()
                textViewTotalLikeCount.text = totalLikes
                if (it.likedByUser) {
                    imageViewLikeYesNo.setImageResource(R.drawable.like_yes)
                } else {
                    imageViewLikeYesNo.setImageResource(R.drawable.like_no)
                }
            }
            root.setOnClickListener {
                item?.let(onClick)
            }
        }
    }
}

class UserLikedPhotoDiffUtilCallback : DiffUtil.ItemCallback<UserLikedPhotoItem>() {
    override fun areItemsTheSame(
        oldItem: UserLikedPhotoItem,
        newItem: UserLikedPhotoItem
    ): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: UserLikedPhotoItem,
        newItem: UserLikedPhotoItem
    ): Boolean = oldItem == newItem
}

class UserLikedPhotoViewHolder(val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root)