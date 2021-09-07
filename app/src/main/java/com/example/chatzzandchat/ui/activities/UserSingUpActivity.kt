package com.example.chatzzandchat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import com.example.chatzzandchat.all_activites.MyViewModel
import com.example.chatzzandchat.all_activites.all_act.SetProfileActivity

import com.google.firebase.auth.PhoneAuthProvider

class UserSingUpActivity : AppCompatActivity() {
    lateinit var otp: EditText
    lateinit var btn: Button
    lateinit var myMyViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oth)
        otp = findViewById(R.id.otpOthAct)
        btn = findViewById(R.id.buttonOthAct)
        val userNumber = intent.getStringExtra("MobileNumber")
        Toast.makeText(this, "" + userNumber, Toast.LENGTH_SHORT).show()
        myMyViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        userNumber?.let { myMyViewModel.SendingVerificationCodeMVVM(it, this) }

        btn.setOnClickListener {
            if (otp.text.isEmpty()) {
                Toast.makeText(this, "Please enter otp first", Toast.LENGTH_SHORT).show()
            } else if (otp.text.length < 6) {
                Toast.makeText(this, "Please enter full otp", Toast.LENGTH_SHORT).show()
            } else {
                val credential =
                    PhoneAuthProvider.getCredential(myMyViewModel.getOtp(), otp.text.toString())
                myMyViewModel.signInWithPhoneAuthCredential(credential, this)
                startActivity(Intent(this, SetProfileActivity::class.java))

            }
        }

    }
}