package kz.grand_hotel.ui.menu.ui.home.Hotel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kz.grand_hotel.R

class ReviewAdapter : ListAdapter<Review, ReviewAdapter.ReviewViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: ImageView       = itemView.findViewById(R.id.avatarImageView)
        private val name: TextView          = itemView.findViewById(R.id.nameTextView)
        private val rating: TextView        = itemView.findViewById(R.id.ratingTextView)
        private val comment: TextView       = itemView.findViewById(R.id.commentTextView)

        fun bind(item: Review) {
            // Загрузка аватара через Glide
            Glide.with(itemView)
                .load(item.avatarResId)
                .circleCrop()
                .into(avatar)

            name.text    = item.authorName
            rating.text  = item.rating.toString()
            comment.text = item.comment
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(old: Review, new: Review) =
            old === new

        override fun areContentsTheSame(old: Review, new: Review) =
            old == new
    }
}
