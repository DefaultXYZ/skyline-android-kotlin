package com.defaultxyz.skyline.presentation.map.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.defaultxyz.skyline.R
import com.defaultxyz.skyline.databinding.LayoutReviewItemBinding
import com.defaultxyz.skyline.domain.model.Review

class ReviewAdapter(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<ReviewAdapter.ItemViewHolder>() {

    var data = emptyList<ReviewItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataBindingUtil.inflate<LayoutReviewItemBinding>(
            layoutInflater,
            R.layout.layout_review_item,
            parent,
            false
        ).let { ItemViewHolder(it) }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ItemViewHolder(
        private val binding: LayoutReviewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ReviewItem) {
            binding.item = item
        }

    }
}

data class ReviewItem(
    val name: String,
    val body: String
) {
    constructor(review: Review) : this(review.userName, review.text)
}