package com.example.chatzzandchat.ui.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.chatzzandchat.R
import com.example.chatzzandchat.viewmodel.UsersViewModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UpdateProfileActivity : AppCompatActivity() {
    lateinit var profilePic: CircleImageView
    lateinit var name: EditText
    lateinit var btn: Button
    lateinit var viewModel: UsersViewModel
    var imagePathUri: Uri? = null
    val imagePickCode = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        profilePic = findViewById(R.id.profile_imageSetProfileAct)
        name = findViewById(R.id.personNameSetProfileAct)
        btn = findViewById(R.id.buttonSetProfileAct)
        viewModel = ViewModelProvider(this)[UsersViewModel::class.java]
        changeColourOfStatusBar()
        btn.setOnClickListener {
            if (name.text.isEmpty()) {
                Toast.makeText(this, "Plz Enter Your Name", Toast.LENGTH_SHORT).show()
            } else if (imagePathUri == null) {
                Toast.makeText(this, "Plz PiC Your Profile Img", Toast.LENGTH_SHORT).show()

            } else {
                imagePathUri?.let { it1 -> viewModel.sendDataToServer(name.text.toString(), this, it1) }
                startActivity(Intent(this, UserListActivity::class.java))
                finish()
            }

        }
        profilePic.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(intent, imagePickCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == imagePickCode && resultCode == RESULT_OK) {
            imagePathUri = data!!.data
            Picasso.get().load(imagePathUri).into(profilePic)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    private fun changeColourOfStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.teal_700)
    }

}