package kz.grand_hotel.ui.menu.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.databinding.ItemRecommendedBinding

class RecommendedAdapter(
    private val onItemClick: (Property) -> Unit
) : ListAdapter<Property, RecommendedAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

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
        private val onItemClick: (Property) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(property: Property) {
            binding.recommendedImageView.setImageResource(property.imageResId)
            binding.propertyNameTextView .text = property.name
            binding.propertyLocationTextView.text = property.location
            binding.propertyPriceTextView   .text = property.price
            binding.propertyRatingTextView  .text = property.rating

            binding.root.setOnClickListener {
                onItemClick(property)
            }
        }
    }

    class PropertyDiffCallback : DiffUtil.ItemCallback<Property>() {
        override fun areItemsTheSame(oldItem: Property, newItem: Property) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Property, newItem: Property) =
            oldItem == newItem
    }
}
