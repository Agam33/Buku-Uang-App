package com.ra.bkuang.presentation.ui.transaction.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ra.bkuang.presentation.ui.transaction.TransactionType

class TransactionPagerAdapter(
  fragment: Fragment
): FragmentStateAdapter(fragment) {

  private val fragments = mutableListOf<Fragment>()
  private val fragmentTitle = mutableListOf<String>()
  private val fragmentTag = mutableListOf<TransactionType>()

  fun addFragment(
      item: Fragment,
      title: String,
      transactionType: TransactionType,
  ) {
    fragments.add(item)
    fragmentTitle.add(title)
    fragmentTag.add(transactionType)
  }

  fun getTransactionType(position: Int) = fragmentTag[position]

  fun getTitle(position: Int) = fragmentTitle[position]

  override fun getItemCount(): Int {
    return fragments.size
  }

  override fun createFragment(position: Int): Fragment {
    return fragments[position]
  }
}