package com.example.chatzzandchat.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.chatzzandchat.R
import com.google.firebase.auth.FirebaseAuth
import com.hbb20.CountryCodePicker

class GetUserMobileNumActivity : AppCompatActivity() {
    lateinit var phoneNumber: EditText
    lateinit var submitBtn: Button
    lateinit var ccpCountry: CountryCodePicker
    val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_user_num)
        phoneNumber = findViewById(R.id.phoneNumberMainAct)
        submitBtn = findViewById(R.id.buttonMainAct)
        ccpCountry = findViewById(R.id.ccp)




        submitBtn.setOnClickListener {
            if (phoneNumber.text.isEmpty()) {
                Toast.makeText(this, "Plz Enter Mobile Number", Toast.LENGTH_SHORT).show()
            } else if (phoneNumber.text.length < 10) {
                Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show()
            } else {
//            Toast.makeText(this, ""+phoneNumber.text, Toast.LENGTH_SHORT).show()
                val fullNumber = ccpCountry.selectedCountryCodeWithPlus + phoneNumber.text
                //  Toast.makeText(this, ""+fullNumber, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UserSingUpActivity::class.java)
                intent.putExtra("MobileNumber", fullNumber)
                startActivity(intent)

            }
        }

    }

}