package com.example.chatzzandchat.all_activites.all_adapter_viewholder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.chatzzandchat.all_activites.all_fragments.CallFragment
import com.example.chatzzandchat.all_activites.all_fragments.ChatFragment
import com.example.chatzzandchat.all_activites.all_fragments.StatusFragment

class MyViewPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
      return 3
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 ->{
                return ChatFragment()
            }
            1 ->{
                return StatusFragment()
            }
            2 ->{
                return CallFragment()
            }
            else -> {
                return ChatFragment()
        }
    }
}

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Chats"
            }
            1 -> {
                return "Status"
            }
            2 -> {
                return "Calls"
            }
        }
        return super.getPageTitle(position)
    }
}