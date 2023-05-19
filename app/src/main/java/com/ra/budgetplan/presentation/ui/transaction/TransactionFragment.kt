package com.ra.budgetplan.presentation.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ra.budgetplan.R
import com.ra.budgetplan.customview.dialog.icon.IconListDialog
import com.ra.budgetplan.databinding.FragmentTransactionBinding
import com.ra.budgetplan.presentation.ui.transaction.adapter.TransactionPagerAdapter
import com.ra.budgetplan.presentation.ui.transaction.fragment.ExpenseFragment
import com.ra.budgetplan.presentation.ui.transaction.fragment.IncomeFragment
import com.ra.budgetplan.presentation.ui.transaction.fragment.TransferFragment

class TransactionFragment : Fragment() {

  private var _binding: FragmentTransactionBinding? = null
  private val binding get() = _binding

  private lateinit var transactionPagerAdapter: TransactionPagerAdapter
  private lateinit var tabLayoutMediator: TabLayoutMediator

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentTransactionBinding.inflate(layoutInflater, container, false)
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViewPager()

  }

  private fun setupViewPager() {
    transactionPagerAdapter = TransactionPagerAdapter(requireActivity()).apply {
      addFragment(IncomeFragment(), getString(R.string.title_income))
      addFragment(ExpenseFragment(), getString(R.string.title_expense))
      addFragment(TransferFragment(), getString(R.string.title_transfer))
    }

    binding?.run {
      tabLayoutMediator = TabLayoutMediator(tabLayout, vPagerTransaction) { tab, position ->
        tab.text = transactionPagerAdapter.getTitle(position)
      }

      vPagerTransaction.orientation= ViewPager2.ORIENTATION_HORIZONTAL
      vPagerTransaction.adapter = transactionPagerAdapter
    }

    tabLayoutMediator.attach()
  }

  override fun onDestroy() {
    _binding = null
    super.onDestroy()
  }
}