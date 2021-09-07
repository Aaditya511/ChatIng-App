package com.example.chatzzandchat.all_activites.repository

import android.util.Log
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.chatzzandchat.dataclasses.MyFireStoreDataClass

class ChatFragRepo {
    fun allUserInChatFrag(): FirestoreRecyclerOptions<MyFireStoreDataClass> {
        val firebaseFireStore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val query = firebaseFireStore.collection("Users").whereNotEqualTo("Id", auth.uid)
        val allUserName = FirestoreRecyclerOptions.Builder<MyFireStoreDataClass>().setQuery(query,MyFireStoreDataClass::class.java).build()
        return allUserName
    }
}