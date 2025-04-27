package kz.grand_hotel.ui.menu.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.R

class PropertyAdapter : ListAdapter<Property, PropertyAdapter.PropertyViewHolder>(PropertyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_popular, parent, false)
        return PropertyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val currentProperty = getItem(position)
        holder.bind(currentProperty)
    }

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.propertyImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.propertyNameTextView)
        private val locationTextView: TextView = itemView.findViewById(R.id.propertyLocationTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.propertyPriceTextView)
        private val ratingTextView: TextView = itemView.findViewById(R.id.propertyRatingTextView)

        fun bind(property: Property) {
            imageView.setImageResource(property.imageResId)
            nameTextView.text = property.name
            locationTextView.text = property.location
            priceTextView.text = property.price
            ratingTextView.text = property.rating
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
