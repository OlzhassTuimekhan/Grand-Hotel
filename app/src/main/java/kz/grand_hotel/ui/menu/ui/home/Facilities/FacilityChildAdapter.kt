package kz.grand_hotel.ui.menu.ui.home.Facilities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.databinding.ItemFacilityChildBinding

class FacilityChildAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<FacilityChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(
        private val binding: ItemFacilityChildBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.childTextView.text = name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding = ItemFacilityChildBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
