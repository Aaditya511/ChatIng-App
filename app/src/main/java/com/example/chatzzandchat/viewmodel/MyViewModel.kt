package com.example.chatzzandchat.all_activites

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatzzandchat.all_activites.repository.ChatFragRepo
import com.example.chatzzandchat.all_activites.repository.UserSingUpRepo
import com.example.chatzzandchat.all_activites.repository.PersnolChatRepo
import com.example.chatzzandchat.all_activites.repository.SetProfileRepo
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.example.chatzzandchat.dataclasses.MyFireStoreDataClass
import com.example.chatzzandchat.dataclasses.MyMessagesDataClass
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class MyViewModel : ViewModel() {
    val othRepo = UserSingUpRepo()
    val setProfileRepo = SetProfileRepo()
    val chatFragRepo = ChatFragRepo()
    val persnolChatRepo = PersnolChatRepo()

    var singInSucessFullLiveData = MutableLiveData<String>()

    fun SendingVerificationCodeMVVM(phoneNumber: String, context: Context) {
        othRepo.sendingVerificationCode(phoneNumber, context, OnCompleteListener {  })
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential, context: Context) {
        othRepo.signInWithPhoneAuthCredential(credential, context, OnCompleteListener { task ->
            if (task.isSuccessful) {
                singInSucessFullLiveData.value = "You Are SuccesFully Register"
                //Toast.makeText(context, "" + singInSucessFullLiveData, Toast.LENGTH_SHORT).show()
            } else {
                // Sign in failed, display a message and update the UI
                Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "Invalid O.T.P ", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    fun getOtp(): String {
        return othRepo.storedVerificationId
    }

    fun sendDataToFB(userName: String, context: Context, imagePathUri: Uri) {
        setProfileRepo.sendUserNametoRealtime(userName, context)
        setProfileRepo.sendImageToStorage(
            context,
            imagePathUri,
            object : OnSuccessListener<Uri> {
                override fun onSuccess(p0: Uri?) {
                    p0.let {
                        //     Log.i("Url", it.toString())
                        setProfileRepo.sendDataToCloudFireStore(context, userName, it.toString())
                    }

                }
            })

    }

    fun chatFragAllUser(): FirestoreRecyclerOptions<MyFireStoreDataClass> {
        val users = chatFragRepo.allUserInChatFrag()
        return users
    }

    fun spesificChat(message: String, senderRoom: String, reciverRoom: String) {
        persnolChatRepo.chatUser(message, senderRoom, reciverRoom)

    }


    val chatListLiveData = MutableLiveData<ArrayList<MyMessagesDataClass>>()

    fun getChat(reciverRoom: String) {
        persnolChatRepo.getChat(reciverRoom, object : ValueEventListener {
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
