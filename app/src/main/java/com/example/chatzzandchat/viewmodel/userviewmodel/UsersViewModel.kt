package com.example.chatzzandchat.viewmodel.userviewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatzzandchat.models.UsersList
import com.example.chatzzandchat.repository.userrepo.UsersRepo
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class UsersViewModel : ViewModel() {

    val userRepo = UsersRepo()
    var singInSucessFullLiveData = MutableLiveData<String>()


    fun SendingVerificationCodeMVVM(phoneNumber: String, context: Context) {
        userRepo.sendingVerificationCode(phoneNumber, context)
    }

    fun signInWithPhoneAuthCredential(otpString: String, context: Context) {

        userRepo.signInWithPhoneAuthCredential(otpString, context, OnCompleteListener { task ->
            if (task.isSuccessful) {
                singInSucessFullLiveData.value = "SuccesFully Register"
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

    fun getStoredVerificationId(): String {
        return userRepo.storedVerificationId
    }

    fun sendDataToServer(userName: String, context: Context, imagePathUri: Uri) {
        userRepo.sendUserNametoRealtime(userName, context)
        userRepo.sendImageToStorage(
            context,
            imagePathUri,
            object : OnSuccessListener<Uri> {
                override fun onSuccess(p0: Uri?) {
                    p0.let {
                        //     Log.i("Url", it.toString())
                        userRepo.sendDataToCloudFireStore(context, userName, it.toString())
                    }

                }
            })

    }

    fun allUserList(): FirestoreRecyclerOptions<UsersList> {
        val users = userRepo.allUserList()
        return users
    }

}