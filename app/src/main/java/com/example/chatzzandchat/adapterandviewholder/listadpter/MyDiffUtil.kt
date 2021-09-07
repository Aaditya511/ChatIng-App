package com.example.chatzzandchat.all_activites.all_adapter_viewholder.listadpter

import androidx.recyclerview.widget.DiffUtil
import com.example.chatzzandchat.dataclasses.MyMessagesDataClass

class MyDiffUtil:DiffUtil.ItemCallback<MyMessagesDataClass>() {
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