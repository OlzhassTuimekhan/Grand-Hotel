package kz.grand_hotel.ui.menu.ui.home.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.R

class RecentSearchAdapter(
    private val onItemClick: (RecentSearch) -> Unit
) : ListAdapter<RecentSearch, RecentSearchAdapter.VH>(DIFF) {

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<RecentSearch>() {
            override fun areItemsTheSame(a: RecentSearch, b: RecentSearch) =
                a.placeName == b.placeName
            override fun areContentsTheSame(a: RecentSearch, b: RecentSearch) = a == b
        }
    }

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        private val title    = view.findViewById<TextView>(R.id.titleText)
        private val subtitle = view.findViewById<TextView>(R.id.subtitleText)
        init {
            view.setOnClickListener {
                onItemClick(getItem(bindingAdapterPosition))
            }
        }
        fun bind(item: RecentSearch) {
            title.text    = item.placeName
            subtitle.text = item.placeLocation
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_search, parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(getItem(position))
}
