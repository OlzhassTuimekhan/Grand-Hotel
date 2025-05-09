package kz.grand_hotel.ui.menu.ui.home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.databinding.ItemRecentlyViewedBinding
import kz.grand_hotel.databinding.ItemRecommendedBinding
import kz.grand_hotel.ui.menu.ui.home.Hotel.Hotels

class RecentlyViewedAdapter(
    private val onItemClick: (Hotels) -> Unit
) : ListAdapter<Hotels, RecentlyViewedAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemRecentlyViewedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PropertyViewHolder(
        private val binding: ItemRecentlyViewedBinding,
        private val onItemClick: (Hotels) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(property: Hotels) {
            binding.hotelImageView.setImageResource(property.image)
            binding.hotelNameTextView    .text = property.name
            binding.locationTextView.text = property.location
            binding.priceTextView   .text = property.price
            binding.ratingTextView  .text = property.rating

            binding.root.setOnClickListener {
                onItemClick(property)
            }
        }
    }

    class PropertyDiffCallback : DiffUtil.ItemCallback<Hotels>() {
        override fun areItemsTheSame(oldItem: Hotels, newItem: Hotels) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Hotels, newItem: Hotels) =
            oldItem == newItem
    }


}
