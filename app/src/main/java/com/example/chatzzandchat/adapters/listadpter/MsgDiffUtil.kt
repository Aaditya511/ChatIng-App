package com.example.chatzzandchat.adapters.listadpter

import androidx.recyclerview.widget.DiffUtil
import com.example.chatzzandchat.models.Messages

class MsgDiffUtil : DiffUtil.ItemCallback<Messages>() {
    override fun areItemsTheSame(
        oldItem: Messages,
        newItem: Messages
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Messages,
        newItem: Messages
    ): Boolean {
        return newItem == oldItem
    }
}