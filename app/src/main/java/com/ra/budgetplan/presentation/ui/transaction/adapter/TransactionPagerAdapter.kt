package com.ra.budgetplan.presentation.ui.transaction.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TransactionPagerAdapter(
  fragment: FragmentActivity
): FragmentStateAdapter(fragment) {

  private val fragments = mutableListOf<Fragment>()
  private val fragmentTitle = mutableListOf<String>()

  fun addFragment(item: Fragment, title: String) {
    fragments.add(item)
    fragmentTitle.add(title)
  }

  fun getTitle(position: Int) = fragmentTitle[position]

  override fun getItemCount(): Int {
    return fragments.size
  }

  override fun createFragment(position: Int): Fragment {
    return fragments[position]
  }
}