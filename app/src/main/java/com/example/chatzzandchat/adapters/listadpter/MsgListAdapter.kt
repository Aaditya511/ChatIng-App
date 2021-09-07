package com.example.chatzzandchat.all_activites.all_adapter_viewholder.listadpter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatzzandchat.R
import com.example.chatzzandchat.adapters.listadpter.MsgDiffUtil
import com.example.chatzzandchat.viewholders.ReciverViewHolder
import com.example.chatzzandchat.viewholders.SenderViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.example.chatzzandchat.models.MyMessagesDataClass

class MsgListAdapter(val context: Context, val arrayList: ArrayList<MyMessagesDataClass>) :
    ListAdapter<MyMessagesDataClass, RecyclerView.ViewHolder>(
        MsgDiffUtil()
    ) {
    var itemSend = 1
    var itemRecive = 2
    val auth = FirebaseAuth.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType.equals(itemSend)) {
            val view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false)
            return SenderViewHolder(view)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.reciver_layout, parent, false)
            return ReciverViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = arrayList.get(position)
        if (holder::class.java == SenderViewHolder::class.java) {
            val senderViewHolder: SenderViewHolder = holder as SenderViewHolder
            senderViewHolder.msgSender.text = msg.message
            senderViewHolder.timeOFMsgSender.text = msg.currenttime
        } else {
            val reciverViewHolder = holder as ReciverViewHolder
            reciverViewHolder.msgReciver.text = msg.message
            reciverViewHolder.timeOFMsgReciver.text = msg.currenttime
        }
    }

    override fun getItemViewType(position: Int): Int {
        val msg = arrayList.get(position)
        if (auth.uid.equals(msg.senderId)) {
            return itemSend
        } else {
            return itemRecive
        }
    }

}