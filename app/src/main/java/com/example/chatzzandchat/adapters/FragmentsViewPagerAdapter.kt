package com.example.chatzzandchat.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.chatzzandchat.ui.fragments.CallFragment
import com.example.chatzzandchat.ui.fragments.ChatFragment
import com.example.chatzzandchat.ui.fragments.StatusFragment

class FragmentsViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return ChatFragment()
            }
            1 -> {
                return StatusFragment()
            }
            2 -> {
                return CallFragment()
            }
            else -> {
                return ChatFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
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