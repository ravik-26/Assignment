package com.miletrips.assignment.ui.fragments.fragment_2

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.miletrips.assignment.models.Result

class ClipsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val currentItem = differ.currentList[position]
        return ClipsFragment.newInstance(currentItem)
    }
}