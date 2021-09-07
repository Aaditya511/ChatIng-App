package com.example.chatzzandchat.adapters.listadpter

import androidx.recyclerview.widget.DiffUtil
import com.example.chatzzandchat.models.MyMessagesDataClass

class MsgDiffUtil : DiffUtil.ItemCallback<MyMessagesDataClass>() {
    override fun areItemsTheSame(
        oldItem: MyMessagesDataClass,
        newItem: MyMessagesDataClass
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: MyMessagesDataClass,
        newItem: MyMessagesDataClass
    ): Boolean {
        return newItem == oldItem
    }
}