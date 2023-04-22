package com.avv2050soft.unsplashtool.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avv2050soft.unsplashtool.R
import com.avv2050soft.unsplashtool.databinding.ItemPhotoBinding
import com.avv2050soft.unsplashtool.domain.models.photos.Photo
import com.avv2050soft.unsplashtool.presentation.utils.toStringWithKNotation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class PhotosAdapter(
    private val onClick: (Photo) -> Unit
) : PagingDataAdapter<Photo, PhotoViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            item?.let {
                Glide
                    .with(imageViewPhoto.context)
                    .load(it.urls.small)
                    .into(imageViewPhoto)
                Glide
                    .with(imageViewAvatar.context)
                    .load(it.user.profileImage.medium)
                    .transform(CircleCrop())
                    .into(imageViewAvatar)
                textViewAuthorName.text = item.user.name
                textViewUserName.text = item.user.username
                val totalLikes = item.likes.toStringWithKNotation()
                textViewTotalLikeCount.text = totalLikes
                if (item.likedByUser){
                    imageViewLikeYesNo.setImageResource(R.drawable.like_yes)
                }else {
                    imageViewLikeYesNo.setImageResource(R.drawable.like_no)
                }
            }
            root.setOnClickListener {
                item?.let(onClick)
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
}

class PhotoViewHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)