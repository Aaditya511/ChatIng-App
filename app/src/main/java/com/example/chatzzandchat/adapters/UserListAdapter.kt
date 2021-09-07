package com.example.chatzzandchat.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.chatzzandchat.R
import com.example.chatzzandchat.ui.activities.ChatActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso
import com.example.chatzzandchat.models.MyFireStoreDataClass
import com.example.chatzzandchat.viewholders.UserListViewHolder

class UserListAdapter(
    options: FirestoreRecyclerOptions<MyFireStoreDataClass>
) : FirestoreRecyclerAdapter<MyFireStoreDataClass, UserListViewHolder>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.chat_layout_rv, parent, false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: UserListViewHolder,
        position: Int,
        model: MyFireStoreDataClass
    ) {
        holder.nameData.text = model.Name
        holder.statusData.text = model.Status
        Picasso.get().load(model.ImgUri).into(holder.circleImageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ChatActivity::class.java)
            intent.putExtra("name", model.Name)
            intent.putExtra("id", model.Id)
            intent.putExtra("Img", model.ImgUri)
            holder.itemView.context.startActivity(intent)
        }
    }
}