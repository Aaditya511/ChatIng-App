package com.example.chatzzandchat.all_activites.all_act

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.chatzzandchat.GetUserMobileNumActivity
import com.example.chatzzandchat.R
import com.example.chatzzandchat.all_activites.all_adapter_viewholder.MyViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

class ChatActivity : AppCompatActivity() {
    lateinit var  tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var toolbar: Toolbar
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
        toolbar = findViewById(R.id.toolBar)
        auth = FirebaseAuth.getInstance()
        setSupportActionBar(toolbar)
        settingMenuOnToolBar()
        callingFragment()
    }
    fun callingFragment(){
        viewPager.adapter = MyViewPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id==R.id.logout){
            auth.signOut()
            Toast.makeText(this, "U are Logout", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,GetUserMobileNumActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
    fun settingMenuOnToolBar() {
        setSupportActionBar(toolbar)
        val drawable: Drawable? =
            ContextCompat.getDrawable(this, R.drawable.ic_baseline_more_vert_24)
        toolbar.overflowIcon = drawable
    }
}