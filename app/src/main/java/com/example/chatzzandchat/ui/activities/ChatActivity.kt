package com.example.chatzzandchat.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatzzandchat.R
import com.example.chatzzandchat.viewmodel.MsgesViewModel
import com.example.chatzzandchat.adapters.listadpter.MsgListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.example.chatzzandchat.models.Messages
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {
    var name: String? = null
    var id: String? = null
    var img: String? = null
    lateinit var senderRoom: String
    lateinit var reciverRoom: String
    lateinit var auth: FirebaseAuth
    lateinit var msgesViewModel: MsgesViewModel
    lateinit var userName: TextView
    lateinit var backBtn: ImageView
    lateinit var userImg: CircleImageView
    lateinit var msgTypeEt: EditText
    lateinit var sendMsgBtn: ImageView
    lateinit var recylerView: RecyclerView
    lateinit var msgListAdapter: MsgListAdapter
    lateinit var imageViewVideoCall: ImageView
    lateinit var imageViewCall: ImageView
    lateinit var imageViewMenu: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        changeColourOfStatusBar()
        auth = FirebaseAuth.getInstance()
        imageViewCall = findViewById(R.id.imageViewCall)
        imageViewVideoCall = findViewById(R.id.imageViewVideoCall)
        imageViewMenu = findViewById(R.id.imageViewMenu)
        recylerView = findViewById(R.id.rvPCA)
        userName = findViewById(R.id.userNamePCA)
        backBtn = findViewById(R.id.imageViewPCA)
        userImg = findViewById(R.id.userImgPCA)
        msgTypeEt = findViewById(R.id.msgType)
        sendMsgBtn = findViewById(R.id.sendMsgImg)

        msgesViewModel = ViewModelProvider(this)[MsgesViewModel::class.java]

        onclickForBtnDidNotWork()

        msgesViewModel.chatListLiveData.observe(this) { chatMessageList ->
            chatMessageList?.let {
                //    msgList.clear()
                //  msgList.addAll(it)
                // setRv()
                setAdapter(it)
            }
        }

        name = intent.getStringExtra("name")
        id = intent.getStringExtra("id")
        img = intent.getStringExtra("Img")

        senderRoom = auth.uid + id
        reciverRoom = id + auth.uid

        userName.text = name
        Picasso.get().load(img).into(userImg)
        backBtn.setOnClickListener {
            startActivity(Intent(this, UserListActivity::class.java))
        }

        sendMsgBtn.setOnClickListener {
            if (msgTypeEt.text.isEmpty()) {
                Toast.makeText(this, "Type Your Msg First", Toast.LENGTH_SHORT).show()
            } else {
                sendMsgBtnClickListner()
                msgTypeEt.text = null
            }
        }

        msgesViewModel.getPreviousChat(reciverRoom)
    }

    fun sendMsgBtnClickListner() {
        msgesViewModel.sendMsg(msgTypeEt.text.toString(), senderRoom, reciverRoom)
    }


    fun setAdapter(arrayList: ArrayList<Messages>) {
        msgListAdapter = MsgListAdapter(this, arrayList)
        msgListAdapter.submitList(arrayList)
        recylerView.layoutManager = LinearLayoutManager(this@ChatActivity)
        recylerView.scrollToPosition(arrayList.size - 1)
        recylerView.adapter = msgListAdapter

    }

    fun changeColourOfStatusBar() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.teal_700)
    }

    fun toastMsgForBtnDidNotWork() {
        Toast.makeText(this, "This Feature Is Not Avaliable Now ", Toast.LENGTH_SHORT).show()
    }

    fun onclickForBtnDidNotWork(){
        imageViewMenu.setOnClickListener { toastMsgForBtnDidNotWork() }
        imageViewVideoCall.setOnClickListener { toastMsgForBtnDidNotWork() }
        imageViewCall.setOnClickListener { toastMsgForBtnDidNotWork() }
    }
}