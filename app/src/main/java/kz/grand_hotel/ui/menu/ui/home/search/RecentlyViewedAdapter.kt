package kz.grand_hotel.ui.menu.ui.home.search

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

class RecentlyViewedAdapter : ListAdapter<RecentlyViewed, RecentlyViewedAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<RecentlyViewed>() {
            override fun areItemsTheSame(a: RecentlyViewed, b: RecentlyViewed) = a.id == b.id
            override fun areContentsTheSame(a: RecentlyViewed, b: RecentlyViewed) = a == b
        }
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val img     = view.findViewById<ImageView>(R.id.hotelImageView)
        private val title   = view.findViewById<TextView>(R.id.hotelNameTextView)
        private val loc     = view.findViewById<TextView>(R.id.locationTextView)
        private val price   = view.findViewById<TextView>(R.id.priceTextView)
        private val rating  = view.findViewById<TextView>(R.id.ratingTextView)

        fun bind(item: RecentlyViewed) {
            title.text  = item.name
            loc.text    = item.location
            price.text  = item.price
            rating.text = item.rating
            Glide.with(img).load(item.imageResId).into(img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recently_viewed, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))
}
