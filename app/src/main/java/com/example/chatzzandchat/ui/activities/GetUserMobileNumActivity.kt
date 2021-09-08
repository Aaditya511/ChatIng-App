package com.example.chatzzandchat.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.chatzzandchat.R
import com.google.firebase.auth.FirebaseAuth
import com.hbb20.CountryCodePicker

class GetUserMobileNumActivity : AppCompatActivity() {
    lateinit var phoneNumber: EditText
    lateinit var submitBtn: Button
    lateinit var menuDot: ImageView
    lateinit var ccpCountry: CountryCodePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_user_num)
        phoneNumber = findViewById(R.id.phoneNumberMainAct)
        submitBtn = findViewById(R.id.buttonMainAct)
        ccpCountry = findViewById(R.id.ccp)
        menuDot = findViewById(R.id.imageView3)
        menuDot.setOnClickListener {
            Toast.makeText(this, "This is not Avaliable Now", Toast.LENGTH_SHORT).show()
        }
        changeColourOfStatusBar()
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
                finish()

            }
        }

    }

    private fun changeColourOfStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.grey)
    }


}