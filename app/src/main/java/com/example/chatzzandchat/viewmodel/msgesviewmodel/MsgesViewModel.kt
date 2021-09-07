package com.example.chatzzandchat.viewmodel.msgesviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatzzandchat.repository.msgesrepo.ChatRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.example.chatzzandchat.models.Messages

class MsgesViewModel : ViewModel() {

    val chatRepo = ChatRepo()

    fun sendMsg(message: String, senderRoom: String, reciverRoom: String) {
        chatRepo.sendMsg(message, senderRoom, reciverRoom)

    }


    val chatListLiveData = MutableLiveData<ArrayList<Messages>>()

    fun getPreviousChat(reciverRoom: String) {
        chatRepo.getPreviousChat(reciverRoom, object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatList = ArrayList<Messages>()
                for (snapshot1 in snapshot.children) {
                    val msg = snapshot1.getValue(Messages::class.java)
                    msg?.let { chatList.add(it) }
                }
                chatListLiveData.value = chatList
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
