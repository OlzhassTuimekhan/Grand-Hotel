package kz.grand_hotel.ui.menu.ui.home.Facilities


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.R
import kz.grand_hotel.databinding.ItemFacilityGroupBinding

class FacilityGroupAdapter(
    private val groups: List<FacilityGroup>
) : RecyclerView.Adapter<FacilityGroupAdapter.GroupViewHolder>() {

    inner class GroupViewHolder(
        private val binding: ItemFacilityGroupBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(group: FacilityGroup) {
            // Заголовок с количеством
            binding.groupTitleTextView.text =
                "${group.title} (${group.children.size} facilities)"

            binding.childRecyclerView.apply {
                if (adapter == null) {
                    layoutManager = LinearLayoutManager(context)
                    adapter = FacilityChildAdapter(group.children)
                }
                visibility = if (group.isExpanded) View.VISIBLE else View.GONE
            }

            binding.expandCollapseImageView.setImageResource(
                if (group.isExpanded) R.drawable.ic_add else R.drawable.ic_add
            )

            binding.headerLayout.setOnClickListener {
                group.isExpanded = !group.isExpanded
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemFacilityGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups[position])
    }

    override fun getItemCount(): Int = groups.size
}
