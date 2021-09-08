package com.example.chatzzandchat.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatzzandchat.R
import com.example.chatzzandchat.adapters.UserListAdapter
import com.example.chatzzandchat.viewmodel.UsersViewModel

class ChatFragment : Fragment() {
    lateinit var msgesViewModel: UsersViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: UserListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvChatFrag)
        msgesViewModel = ViewModelProvider(this)[UsersViewModel::class.java]

        val data = msgesViewModel.allUserList()

        myAdapter = UserListAdapter(data)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myAdapter


    }

    override fun onStart() {
        super.onStart()
        myAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        myAdapter.stopListening()
    }


}