package com.example.chatzzandchat.repository.msgesrepo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.chatzzandchat.models.Messages
import java.text.SimpleDateFormat
import java.util.*

class ChatRepo {

    val auth = FirebaseAuth.getInstance()
    val firebaseDb = FirebaseDatabase.getInstance()


    fun sendMsg(msg: String, senderRoom: String, reciverRoom: String) {
        val date = Date()
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat()
        val currentTime = simpleDateFormat.format(calendar.time)

        val msg = Messages(msg, auth.uid, date.time, currentTime)
        firebaseDb.getReference("Chats").child(senderRoom).child("Messages").push().setValue(msg)
            .addOnCompleteListener {
                firebaseDb.getReference("Chats").child(reciverRoom).child("Messages").push()
                    .setValue(msg)

            }

    }

    fun getPreviousChat(reciverRoom: String, chatHistoryCallback: ValueEventListener) {
        val databaseReference =
            firebaseDb.getReference("Chats").child(reciverRoom).child("Messages")
        databaseReference.addValueEventListener(chatHistoryCallback)
    }


}