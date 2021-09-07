package com.example.chatzzandchat.all_activites.all_adapter_viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatzzandchat.R
import de.hdodenhof.circleimageview.CircleImageView

class MyViewHolderChatFrag(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameData: TextView = itemView.findViewById(R.id.nameRecylerView)
    val statusData: TextView = itemView.findViewById(R.id.onlineStatusRecylerView)
    val circleImageView: CircleImageView = itemView.findViewById(R.id.profile_imageRecycler)
}