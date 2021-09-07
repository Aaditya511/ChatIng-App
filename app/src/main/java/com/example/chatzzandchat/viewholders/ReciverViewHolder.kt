package com.example.chatzzandchat.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatzzandchat.R


class ReciverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val msgReciver: TextView = itemView.findViewById(R.id.message_textReciver)
    val timeOFMsgReciver: TextView = itemView.findViewById(R.id.date_textReciver)
}