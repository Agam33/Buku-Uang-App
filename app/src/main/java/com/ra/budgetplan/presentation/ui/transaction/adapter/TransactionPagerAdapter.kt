package com.ra.budgetplan.presentation.ui.transaction.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ra.budgetplan.domain.entity.TipeKategori
import com.ra.budgetplan.presentation.ui.transaction.TransactionType

class TransactionPagerAdapter(
  fragment: FragmentActivity
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