package com.example.chatzzandchat.all_activites.all_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatzzandchat.R
import com.example.chatzzandchat.all_activites.MyViewModel
import com.example.chatzzandchat.all_activites.all_adapter_viewholder.MyFireBaseAdapter

class ChatFragment : Fragment() {
    lateinit var myViewModel: MyViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter:MyFireBaseAdapter
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
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        val data = myViewModel.chatFragAllUser()
        context?.let {
            myAdapter = MyFireBaseAdapter(it,data)
            recyclerView.layoutManager = LinearLayoutManager(it)
            recyclerView.adapter = myAdapter
        }


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