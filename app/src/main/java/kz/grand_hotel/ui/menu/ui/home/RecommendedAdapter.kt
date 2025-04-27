package kz.grand_hotel.ui.menu.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.databinding.ItemRecommendedBinding

class RecommendedAdapter : ListAdapter<Property, RecommendedAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemRecommendedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = getItem(position)
        holder.bind(property)
    }

    class PropertyViewHolder(private val binding: ItemRecommendedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(property: Property) {
            binding.recommendedImageView.setImageResource(property.imageResId)
            binding.propertyNameTextView.text = property.name
            binding.propertyLocationTextView.text = property.location
            binding.propertyPriceTextView.text = property.price
            binding.propertyRatingTextView.text = property.rating.toString()
        }
    }

    class PropertyDiffCallback : DiffUtil.ItemCallback<Property>() {
        override fun areItemsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Property, newItem: Property): Boolean {
            return oldItem == newItem
        }
    }
}
