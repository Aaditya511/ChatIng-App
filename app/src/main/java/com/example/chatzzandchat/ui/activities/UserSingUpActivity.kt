package com.example.chatzzandchat.ui.activities

import `in`.aabhasjindal.otptextview.OtpTextView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import com.example.chatzzandchat.R
import com.example.chatzzandchat.viewmodel.userviewmodel.UsersViewModel

import com.google.firebase.auth.PhoneAuthProvider

class UserSingUpActivity : AppCompatActivity() {
    lateinit var otp: OtpTextView
    lateinit var btn: Button
    lateinit var usersViewModel: UsersViewModel
    lateinit var mobNumTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_singup)
        otp = findViewById(R.id.otp_view)
        btn = findViewById(R.id.buttonOthAct)
        mobNumTv = findViewById(R.id.mobNumTv)
        val userNumber = intent.getStringExtra("MobileNumber")
        mobNumTv.text = userNumber
  //      Toast.makeText(this, "" + userNumber, Toast.LENGTH_SHORT).show()
        usersViewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        userNumber?.let { usersViewModel.SendingVerificationCodeMVVM(it, this) }

        btn.setOnClickListener {
            if (otp.otp!!.isEmpty()) {
                Toast.makeText(this, "Please enter otp first", Toast.LENGTH_SHORT).show()
            } else if (otp.otp!!.length < 6) {
                Toast.makeText(this, "Please enter full otp", Toast.LENGTH_SHORT).show()
            } else {
                val credential =
                    PhoneAuthProvider.getCredential(usersViewModel.getOtp(), otp.otp.toString())
                usersViewModel.signInWithPhoneAuthCredential(credential, this)
                usersViewModel.singInSucessFullLiveData.observe(this) {
                    startActivity(Intent(this, UpdateProfileActivity::class.java))
                }

            }
        }

    }
}