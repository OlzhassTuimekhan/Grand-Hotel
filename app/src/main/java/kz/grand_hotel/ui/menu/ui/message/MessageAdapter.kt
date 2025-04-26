package kz.grand_hotel.ui.menu.ui.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.grand_hotel.R

class MessageAdapter : ListAdapter<MessageItem, MessageAdapter.MessageViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

    }

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImageView: ImageView = itemView.findViewById(R.id.profileImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val lastMessageTextView: TextView = itemView.findViewById(R.id.lastMessageTextView)
        private val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        private val notificationCountTextView: TextView = itemView.findViewById(R.id.notificationBadge)

        fun bind(messageItem: MessageItem) {
            profileImageView.setImageResource(messageItem.profileImageResId)
            nameTextView.text = messageItem.name
            lastMessageTextView.text = messageItem.lastMessage
            timeTextView.text = messageItem.time
            if (messageItem.notificationCount > 0) {
                notificationCountTextView.visibility = View.VISIBLE
                notificationCountTextView.text = messageItem.notificationCount.toString()
            } else {
                notificationCountTextView.visibility = View.GONE
            }
        }
    }

    class MessageDiffCallback : DiffUtil.ItemCallback<MessageItem>() {
        override fun areItemsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem.name == newItem.name // Пример сравнения
        }

        override fun areContentsTheSame(oldItem: MessageItem, newItem: MessageItem): Boolean {
            return oldItem == newItem
        }
    }
}
