package com.ra.bkuang.features.transaction.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Constants.DAILY_DATE_FORMAT
import com.ra.bkuang.common.util.Constants.LOCALE_ID
import com.ra.bkuang.common.util.Constants.MONTHLY_DATE_FORMAT
import com.ra.bkuang.common.util.DateViewType
import com.ra.bkuang.common.util.Extension.toStringFormat
import com.ra.bkuang.common.util.OnItemChangedListener
import com.ra.bkuang.common.util.ZoomOutPageTransformer
import com.ra.bkuang.common.util.getDateViewType
import com.ra.bkuang.databinding.FragmentTransactionBinding
import com.ra.bkuang.features.transaction.presentation.adapter.TransactionPagerAdapter
import com.ra.bkuang.features.transaction.presentation.component.TransactionBottomSheet
import com.ra.bkuang.features.transaction.presentation.tab.expense.ExpenseFragment
import com.ra.bkuang.features.transaction.presentation.tab.income.IncomeFragment
import com.ra.bkuang.features.transaction.presentation.tab.transfer.TransferFragment
import com.ra.bkuang.features.transaction.utils.dateByViewType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionBinding>(R.layout.fragment_transaction),
  OnItemChangedListener,
  TransactionBottomSheet.OnTransactionViewTypeListener {

  private val sharedViewModel: TransactionViewModel by viewModels()

  private lateinit var transactionPagerAdapter: TransactionPagerAdapter
  private lateinit var tabLayoutMediator: TabLayoutMediator

  var onDateChangedListener: OnDateChangedListener? = null

  interface OnDateChangedListener {
    fun onDateChanged(currDate: Pair<LocalDateTime, LocalDateTime>)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    setupViewPager()
    createTransaction()

    binding?.btnOptions?.setOnClickListener {
      val bottomSheet = TransactionBottomSheet()
      bottomSheet.onTransactionViewTypeListener = this@TransactionFragment
      bottomSheet.show(childFragmentManager, TransactionBottomSheet.TAG)
    }
  }

  private fun observer() {
    sharedViewModel.initDateView()
    sharedViewModel.initTransaction(TRANSACTION_CURRENT_DATE)

    lifecycleScope.launch {
      sharedViewModel.uiState.collect { uiState ->
        onDateChangedListener?.onDateChanged(uiState.currDate)

        sharedViewModel.getTotalExpenseByDate(uiState.currDate.first, uiState.currDate.second)
        sharedViewModel.getTotalIncomeByDate(uiState.currDate.first, uiState.currDate.second)
        sharedViewModel.getTotalByDate(uiState.currDate.first, uiState.currDate.second)

        setupButtonDate(uiState.dateViewType)
        setupTransactionCard(uiState)
      }
    }
  }

  private fun setupButtonDate(dateViewType: String) {
    binding?.imgBtnPrevDate?.setOnClickListener {
      when (getDateViewType(dateViewType)) {
        DateViewType.MONTHLY -> {
          TRANSACTION_CURRENT_DATE = TRANSACTION_CURRENT_DATE.minusMonths(1)
          sharedViewModel.setCurrentDate(TRANSACTION_CURRENT_DATE.dateByViewType(dateViewType))
          binding?.tvCurrentDate?.text =
            TRANSACTION_CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
        }
        else -> {
          TRANSACTION_CURRENT_DATE = TRANSACTION_CURRENT_DATE.minusDays(1)
          sharedViewModel.setCurrentDate(TRANSACTION_CURRENT_DATE.dateByViewType(dateViewType))
          binding?.tvCurrentDate?.text =
            TRANSACTION_CURRENT_DATE.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
        }
      }
    }

    binding?.imgBtnNextDate?.setOnClickListener {
      when (getDateViewType(dateViewType)) {
        DateViewType.MONTHLY -> {
          TRANSACTION_CURRENT_DATE = TRANSACTION_CURRENT_DATE.plusMonths(1)
          sharedViewModel.setCurrentDate(TRANSACTION_CURRENT_DATE.dateByViewType(dateViewType))
          binding?.tvCurrentDate?.text =
            TRANSACTION_CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
        }

        else -> {
          TRANSACTION_CURRENT_DATE = TRANSACTION_CURRENT_DATE.plusDays(1)
          sharedViewModel.setCurrentDate(TRANSACTION_CURRENT_DATE.dateByViewType(dateViewType))
          binding?.tvCurrentDate?.text =
            TRANSACTION_CURRENT_DATE.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
        }
      }
    }
  }

  private fun setupTransactionCard(uiState: TransactionUiState) {
    binding?.overviewIncomeExpenseTotal?.tvIncome?.text = uiState.incomeText
    binding?.overviewIncomeExpenseTotal?.tvExpense?.text = uiState.expenseText
    binding?.overviewIncomeExpenseTotal?.tvTotal?.text = uiState.totalTransactionText
    when (getDateViewType(uiState.dateViewType)) {
      DateViewType.MONTHLY -> {
        binding?.tvCurrentDate?.text =
          TRANSACTION_CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
      }

      else -> {
        binding?.tvCurrentDate?.text =
          TRANSACTION_CURRENT_DATE.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
      }
    }
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
            onItemChangedListener = this@TransactionFragment
        }, getString(R.string.title_expense), TransactionType.EXPENSE
      )

      addFragment(IncomeFragment().apply {
        onItemChangedListener = this@TransactionFragment
      }, getString(R.string.title_income), TransactionType.INCOME)

      addFragment(TransferFragment().apply {
        onItemChangedListener = this@TransactionFragment
      }, getString(R.string.title_transfer), TransactionType.TRANSFER)
    }

    binding?.run {
      tabLayoutMediator = TabLayoutMediator(tabLayout, vPagerTransaction) { tab, position ->
        tab.text = transactionPagerAdapter.getTitle(position)
      }

      vPagerTransaction.orientation = ViewPager2.ORIENTATION_HORIZONTAL
      vPagerTransaction.adapter = transactionPagerAdapter
      vPagerTransaction.setPageTransformer(ZoomOutPageTransformer())
    }
    tabLayoutMediator.attach()
  }

  override fun onStart() {
    super.onStart()
    TRANSACTION_CURRENT_DATE = LocalDate.now()
  }

  override fun onTransactionViewType(type: DateViewType) {
    sharedViewModel.setCurrentDate(TRANSACTION_CURRENT_DATE.dateByViewType(type.name))
  }

  override fun onItemChanged() {
    transactionPagerAdapter.notifyItemRangeChanged(0, transactionPagerAdapter.itemCount)
  }

  companion object {
    private var TRANSACTION_CURRENT_DATE: LocalDate = LocalDate.now()
    const val EXTRA_TRANSACTION_TYPE = "transaction-type"
    const val EXTRA_TRANSACTION_CREATE_OR_EDIT = "transaction-create-or-edit"
  }
}