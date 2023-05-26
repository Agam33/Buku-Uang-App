package com.ra.budgetplan.presentation.ui.transaction

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ra.budgetplan.R
import com.ra.budgetplan.databinding.FragmentTransactionBinding
import com.ra.budgetplan.presentation.ui.transaction.adapter.TransactionPagerAdapter
import com.ra.budgetplan.presentation.ui.transaction.fragment.ExpenseFragment
import com.ra.budgetplan.presentation.ui.transaction.fragment.IncomeFragment
import com.ra.budgetplan.presentation.ui.transaction.fragment.TransferFragment
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionFragment : Fragment() {

  private val viewModel: TransactionViewModel by viewModels()

  private var _binding: FragmentTransactionBinding? = null
  private val binding get() = _binding

  private lateinit var transactionPagerAdapter: TransactionPagerAdapter
  private lateinit var tabLayoutMediator: TabLayoutMediator

  private var transactionType = TransactionType.EXPENSE

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentTransactionBinding.inflate(inflater, container, false)
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupViewPager()
    createTransaction()
  }

  private fun createTransaction() {
    binding?.run {
      fabAddTransaction.setOnClickListener {
        val i = Intent(requireContext(), CreateTransactionActivity::class.java).apply {
          putExtra(
            EXTRA_TRANSACTION_TYPE,
            transactionPagerAdapter.getTransactionType(vPagerTransaction.currentItem).name
          )
        }
//        shortToast("${vPagerTransaction.currentItem} && ${transactionPagerAdapter.getTransactionType(vPagerTransaction.currentItem).name}")
        startActivity(i)
      }
    }
  }

  private fun setupViewPager() {
    transactionPagerAdapter = TransactionPagerAdapter(requireActivity()).apply {
      addFragment(IncomeFragment(), getString(R.string.title_income), TransactionType.INCOME)
      addFragment(ExpenseFragment(), getString(R.string.title_expense), TransactionType.EXPENSE)
      addFragment(TransferFragment(), getString(R.string.title_transfer), TransactionType.TRANSFER)
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

  companion object {
    const val EXTRA_TRANSACTION_TYPE = "transaction-type"
  }
}