package com.example.chatzzandchat.viewmodel.msgesviewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatzzandchat.repository.msgesrepo.ChatRepo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.example.chatzzandchat.models.MyMessagesDataClass

class MsgesViewModel : ViewModel() {

    val chatRepo = ChatRepo()

    fun spesificChat(message: String, senderRoom: String, reciverRoom: String) {
        chatRepo.chatUser(message, senderRoom, reciverRoom)

    }


    val chatListLiveData = MutableLiveData<ArrayList<MyMessagesDataClass>>()

    fun getChat(reciverRoom: String) {
        chatRepo.getChat(reciverRoom, object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val chatList = ArrayList<MyMessagesDataClass>()
                for (snapshot1 in snapshot.children) {
                    val msg = snapshot1.getValue(MyMessagesDataClass::class.java)
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
