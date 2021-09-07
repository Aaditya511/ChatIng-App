package com.example.chatzzandchat.all_activites.all_adapter_viewholder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.chatzzandchat.R
import com.example.chatzzandchat.all_activites.all_act.PersnolChatActivity
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.squareup.picasso.Picasso
import com.example.chatzzandchat.dataclasses.MyFireStoreDataClass

class MyFireBaseAdapter(
    val context: Context,
    options: FirestoreRecyclerOptions<MyFireStoreDataClass>
) : FirestoreRecyclerAdapter<MyFireStoreDataClass, MyViewHolderChatFrag>(options) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderChatFrag {
        val view = LayoutInflater.from(context).inflate(R.layout.chat_layout_rv, parent, false)
        return MyViewHolderChatFrag(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolderChatFrag,
        position: Int,
        model: MyFireStoreDataClass
    ) {
        holder.nameData.text = model.Name
        holder.statusData.text = model.Status
        Picasso.get().load(model.ImgUri).into(holder.circleImageView)
        holder.itemView.setOnClickListener {
            val intent = Intent(context,PersnolChatActivity::class.java)
            intent.putExtra("name",model.Name)
            intent.putExtra("id",model.Id)
            intent.putExtra("Img",model.ImgUri)
            context.startActivity(intent)
        }
    }
}