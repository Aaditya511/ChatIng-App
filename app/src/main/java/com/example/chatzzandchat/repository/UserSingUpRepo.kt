package com.example.chatzzandchat.all_activites.repository

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.chatzzandchat.all_activites.all_act.SetProfileActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class UserSingUpRepo {
    val auth = FirebaseAuth.getInstance()
    lateinit var storedVerificationId:String

    fun sendingVerificationCode(phoneNumber:String,context:Context,completeListner:OnCompleteListener<AuthResult>){

      val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                signInWithPhoneAuthCredential(credential,context,completeListner)
       //         Toast.makeText(context, "U are Register", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(context, "Enter correct Otp", Toast.LENGTH_SHORT).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Toast.makeText(context, "Try After Some Time", Toast.LENGTH_SHORT).show()
                }

                // Show a message and update the UI
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

               // Log.d(TAG, "onCodeSent:$verificationId")

                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId

            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(context as Activity)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential,context:Context,completeListner: OnCompleteListener<AuthResult>) {
        auth.signInWithCredential(credential).addOnCompleteListener (context as Activity,completeListner)
    }

}