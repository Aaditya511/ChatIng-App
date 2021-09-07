package com.example.chatzzandchat.all_activites.all_act

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatzzandchat.R
import com.example.chatzzandchat.all_activites.MyViewModel
import com.example.chatzzandchat.all_activites.all_adapter_viewholder.listadpter.MyListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import com.example.chatzzandchat.dataclasses.MyMessagesDataClass
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.collections.ArrayList

class PersnolChatActivity : AppCompatActivity() {
    var name: String? = null
    var id: String? = null
    var img: String? = null
    lateinit var senderRoom: String
    lateinit var reciverRoom: String
    lateinit var auth: FirebaseAuth
    lateinit var myViewModel: MyViewModel
    lateinit var userName: TextView
    lateinit var backBtn: ImageView
    lateinit var userImg: CircleImageView
    lateinit var msgTypeEt: EditText
    lateinit var sendMsgBtn: CircleImageView
    lateinit var recylerView: RecyclerView


  //  lateinit var myMsgAdpter: MyMsgAdpter
  //  val msgList = ArrayList<MyMessagesDataClass>()
    lateinit var myListAdapter: MyListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persnol)

        auth = FirebaseAuth.getInstance()

        recylerView = findViewById(R.id.rvPCA)
        userName = findViewById(R.id.userNamePCA)
        backBtn = findViewById(R.id.imageViewPCA)
        userImg = findViewById(R.id.userImgPCA)
        msgTypeEt = findViewById(R.id.msgType)
        sendMsgBtn = findViewById(R.id.sendMsgImg)


        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        myViewModel.chatListLiveData.observe(this) { chatMessageList ->
            chatMessageList?.let {
            //    msgList.clear()
              //  msgList.addAll(it)
               // setRv()
                setRVWithListAdapter(it)
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
            startActivity(Intent(this, ChatActivity::class.java))
        }

        sendMsgBtn.setOnClickListener {
            if (msgTypeEt.text.isEmpty()) {
                Toast.makeText(this, "Type Your Msg First", Toast.LENGTH_SHORT).show()
            } else {
                sendMsgBtnClickListner()
                msgTypeEt.text = null
            }
        }

        myViewModel.getChat(reciverRoom)
    }

    fun sendMsgBtnClickListner() {
        myViewModel.spesificChat(msgTypeEt.text.toString(), senderRoom, reciverRoom)
    }
 /*   fun setRv() {
       mvvm.getChat(reciverRoom,object : ValueEventListener {
           override fun onDataChange(snapshot: DataSnapshot) {
               //            arrayList.clear()
                val chatList = ArrayList<MyMessagesDataClass>()
                for (snapshot1 in snapshot.children){
                    val msg = snapshot1.getValue(MyMessagesDataClass::class.java)
                   msg?.let { chatList.add(it) }

                }
               recylerView.layoutManager = LinearLayoutManager(this@PersnolChatActivity)
               myMsgAdpter = MyMsgAdpter(this@PersnolChatActivity, chatList)
                recylerView.adapter = myMsgAdpter
            }

            override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
            }
        })

   }*/
   /* fun setRv() {
        if (recylerView.adapter != null) {
            recylerView.adapter?.notifyDataSetChanged()
        }
        else {
            recylerView.layoutManager = LinearLayoutManager(this@PersnolChatActivity)
            myMsgAdpter = MyMsgAdpter(this@PersnolChatActivity, msgList)
            recylerView.scrollToPosition(msgList.size - 1)
           recylerView.adapter = myMsgAdpter
        }
    }*/

    fun setRVWithListAdapter(arrayList: ArrayList<MyMessagesDataClass>){
        myListAdapter = MyListAdapter(this,arrayList)
        myListAdapter.submitList(arrayList)
        recylerView.layoutManager = LinearLayoutManager(this@PersnolChatActivity)
        recylerView.scrollToPosition(arrayList.size - 1)
        recylerView.adapter = myListAdapter

    }



}