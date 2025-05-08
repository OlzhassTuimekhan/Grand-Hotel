package kz.grand_hotel.ui.menu.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.databinding.ItemRecommendedBinding
import kz.grand_hotel.ui.menu.ui.home.Hotel.Hotels

class RecommendedAdapter(
    private val onItemClick: (Hotels) -> Unit
) : ListAdapter<Hotels, RecommendedAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemRecommendedBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PropertyViewHolder(
        private val binding: ItemRecommendedBinding,
        private val onItemClick: (Hotels) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(property: Hotels) {
            binding.recommendedImageView.setImageResource(property.image)
            binding.propertyNameTextView .text = property.name
            binding.propertyLocationTextView.text = property.location
            binding.propertyPriceTextView   .text = property.price
            binding.propertyRatingTextView  .text = property.rating

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

    override fun getItemCount(): Int {
        return minOf(super.getItemCount(), 2)
    }
}
