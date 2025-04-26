package kz.grand_hotel.ui.menu.ui.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//class ChatAdapter(private val messages: List<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    companion object {
//        private const val VIEW_TYPE_USER = 1
//        private const val VIEW_TYPE_OTHER = 2
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return when (messages[position].sender) {
//            SenderType.USER -> VIEW_TYPE_USER
//            SenderType.OTHER -> VIEW_TYPE_OTHER
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return when (viewType) {
//            VIEW_TYPE_USER -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.item_user_message, parent, false)
//                UserMessageViewHolder(view)
//            }
//            VIEW_TYPE_OTHER -> {
//                val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.item_other_message, parent, false)
//                OtherMessageViewHolder(view)
//            }
//            else -> throw IllegalArgumentException("Invalid view type")
//        }
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val message = messages[position]
//        when (holder.itemViewType) {
//            VIEW_TYPE_USER -> (holder as UserMessageViewHolder).bind(message)
//            VIEW_TYPE_OTHER -> (holder as OtherMessageViewHolder).bind(message)
//        }
//    }
//
//    override fun getItemCount() = messages.size
//
//    inner class UserMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val messageTextView: TextView = itemView.findViewById(R.id.textViewUserMessage)
//        private val timestampTextView: TextView = itemView.findViewById(R.id.textViewUserTimestamp)
//
//        fun bind(message: Message) {
//            messageTextView.text = message.text
//            timestampTextView.text = message.timestamp
//        }
//    }
//
//    inner class OtherMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val messageTextView: TextView = itemView.findViewById(R.id.textViewOtherMessage)
//        private val timestampTextView: TextView = itemView.findViewById(R.id.textViewOtherTimestamp)
//
//        fun bind(message: Message) {
//            messageTextView.text = message.text
//            timestampTextView.text = message.timestamp
//        }
//    }
//}