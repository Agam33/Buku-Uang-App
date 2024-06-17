package com.ra.bkuang.features.transaction.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.DateViewType
import com.ra.bkuang.common.util.ZoomOutPageTransformer
import com.ra.bkuang.databinding.FragmentTransactionBinding
import com.ra.bkuang.features.transaction.presentation.adapter.TransactionPagerAdapter
import com.ra.bkuang.features.transaction.presentation.component.TransactionBottomSheet
import com.ra.bkuang.features.transaction.presentation.tab.expense.ExpenseFragment
import com.ra.bkuang.features.transaction.presentation.tab.income.IncomeFragment
import com.ra.bkuang.features.transaction.presentation.tab.transfer.TransferFragment
import com.ra.bkuang.features.transaction.presentation.viewmodel.TransactionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionFragment :
  BaseFragment<FragmentTransactionBinding>(R.layout.fragment_transaction),
  TransactionBottomSheet.OnTransactionViewTypeListener {

  private val viewModel: TransactionViewModel by activityViewModels()

  private lateinit var transactionPagerAdapter: TransactionPagerAdapter
  private lateinit var tabLayoutMediator: TabLayoutMediator

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    setupViewPager()
    createTransaction()
    setupButton()
  }

  private fun observer() {
    viewModel.initTransaction()

    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->

        viewModel.getIncomeByDate(uiState.currDate.first, uiState.currDate.second)
        viewModel.getExpenseByDate(uiState.currDate.first, uiState.currDate.second)
        viewModel.getTransferByDate(uiState.currDate.first, uiState.currDate.second)

        viewModel.getTotalExpenseByDate(uiState.currDate.first, uiState.currDate.second)
        viewModel.getTotalIncomeByDate(uiState.currDate.first, uiState.currDate.second)
        viewModel.getTotalByDate(uiState.currDate.first, uiState.currDate.second)

        setupTransactionCard(uiState)
      }
    }
  }

  private fun setupButton() {

    binding?.imgBtnPrevDate?.setOnClickListener {
      viewModel.prevDate()
    }

    binding?.imgBtnNextDate?.setOnClickListener {
      viewModel.nextDate()
    }

    binding?.btnOptions?.setOnClickListener {
      val bottomSheet = TransactionBottomSheet()
      bottomSheet.onTransactionViewTypeListener = this@TransactionFragment
      bottomSheet.show(childFragmentManager, TransactionBottomSheet.TAG)
    }
  }

  private fun setupTransactionCard(uiState: TransactionUiState) {
    binding?.overviewIncomeExpenseTotal?.tvIncome?.text = uiState.incomeText
    binding?.overviewIncomeExpenseTotal?.tvExpense?.text = uiState.expenseText
    binding?.overviewIncomeExpenseTotal?.tvTotal?.text = uiState.totalTransactionText
    binding?.tvCurrentDate?.text = uiState.currDateText
  }

  private fun createTransaction() {
    binding?.run {
      fabAddTransaction.setOnClickListener {
        val i = Intent(requireContext(), CreateTransactionActivity::class.java).apply {
          putExtra(
            EXTRA_TRANSACTION_TYPE,
            transactionPagerAdapter.getTransactionType(vPagerTransaction.currentItem).name
          )
          putExtra(
            EXTRA_TRANSACTION_CREATE_OR_EDIT, ActionType.CREATE.name
          )
        }
        startActivity(i)
      }
    }
  }

  private fun setupViewPager() {
    transactionPagerAdapter = TransactionPagerAdapter(this).apply {
      addFragment(
        ExpenseFragment().apply {
        }, getString(R.string.title_expense), TransactionType.EXPENSE
      )

      addFragment(IncomeFragment().apply {
      }, getString(R.string.title_income), TransactionType.INCOME)

      addFragment(TransferFragment().apply {
      }, getString(R.string.title_transfer), TransactionType.TRANSFER)
    }

    binding?.vPagerTransaction?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    binding?.vPagerTransaction?.adapter = transactionPagerAdapter
    binding?.vPagerTransaction?.setPageTransformer(ZoomOutPageTransformer())

    binding?.run {
      tabLayoutMediator = TabLayoutMediator(tabLayout, vPagerTransaction) { tab, position ->
        tab.text = transactionPagerAdapter.getTitle(position)
      }
    }

    tabLayoutMediator.attach()
  }

  override fun onStart() {
    super.onStart()
    viewModel.initTransaction()
  }

  override fun onTransactionViewType(type: DateViewType) {
    viewModel.changeViewDate()
  }

  companion object {
    const val EXTRA_TRANSACTION_TYPE = "transaction-type"
    const val EXTRA_TRANSACTION_CREATE_OR_EDIT = "transaction-create-or-edit"
  }
}