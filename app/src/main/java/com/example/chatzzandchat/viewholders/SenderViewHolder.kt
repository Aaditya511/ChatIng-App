package com.example.chatzzandchat.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatzzandchat.R

class SenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val msgSender: TextView = itemView.findViewById(R.id.message_textSenderrr)
    val timeOFMsgSender: TextView = itemView.findViewById(R.id.date_textSenderrrr)
}