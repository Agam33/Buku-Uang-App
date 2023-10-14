package com.ra.bkuang.presentation.ui.transaction

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ra.bkuang.R
import com.ra.bkuang.data.local.preferences.UserSettingPref
import com.ra.bkuang.databinding.FragmentTransactionBinding
import com.ra.bkuang.presentation.ui.transaction.adapter.DateViewType
import com.ra.bkuang.presentation.ui.transaction.adapter.TransactionPagerAdapter
import com.ra.bkuang.presentation.ui.transaction.adapter.getDateViewType
import com.ra.bkuang.presentation.ui.transaction.fragment.ExpenseFragment
import com.ra.bkuang.presentation.ui.transaction.fragment.IncomeFragment
import com.ra.bkuang.presentation.ui.transaction.fragment.TransactionBottomSheet
import com.ra.bkuang.presentation.ui.transaction.fragment.TransferFragment
import com.ra.bkuang.presentation.viewmodel.TransactionViewModel
import com.ra.bkuang.util.ActionType
import com.ra.bkuang.util.Constants.DAILY_DATE_FORMAT
import com.ra.bkuang.util.Constants.LOCALE_ID
import com.ra.bkuang.util.Constants.MONTHLY_DATE_FORMAT
import com.ra.bkuang.util.Extension.toStringFormat
import com.ra.bkuang.util.OnItemChangedListener
import com.ra.bkuang.util.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class TransactionFragment : Fragment(), OnItemChangedListener {

  @Inject
  lateinit var userSettingPref: UserSettingPref

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  private var _binding: FragmentTransactionBinding? = null
  private val binding get() = _binding

  private lateinit var transactionPagerAdapter: TransactionPagerAdapter
  private lateinit var tabLayoutMediator: TabLayoutMediator

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentTransactionBinding.inflate(inflater, container, false)
    setupViewPager()
    observer()
    setupButtonDate()
    createTransaction()
    setupOverallMoney()
    Timber.tag("TransactionFragment").d("OnCreateView() - $TRANSACTION_CURRENT_DATE")
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Timber.tag("TransactionFragment").d("OnViewCreated() - $TRANSACTION_CURRENT_DATE")
  }

  private fun observer() {
    binding?.vm = sharedViewModel
    binding?.lifecycleOwner = viewLifecycleOwner
    binding?.overviewIncomeExpenseTotal?.vm = sharedViewModel
    binding?.overviewIncomeExpenseTotal?.lifecycleOwner = viewLifecycleOwner
  }

  private fun refreshDate() {
    sharedViewModel.getDateViewType().observe(viewLifecycleOwner) {
      when (getDateViewType(it)) {
        DateViewType.MONTHLY -> {
          setCurrentDate(it)
          binding?.tvCurrentDate?.text = TRANSACTION_CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
        }
        else -> {
          setCurrentDate(it)
          binding?.tvCurrentDate?.text = TRANSACTION_CURRENT_DATE.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
        }
      }
    }
  }

  private fun setupButtonDate() {
    Timber.tag("TransactionFragment").d("setupDate() - $TRANSACTION_CURRENT_DATE")
    refreshDate()

    binding?.imgBtnPrevDate?.setOnClickListener {
      viewLifecycleOwner.lifecycleScope.launch {
        val viewType = userSettingPref.getDateViewType().first()
        when (getDateViewType(viewType)) {
          DateViewType.MONTHLY -> {
            TRANSACTION_CURRENT_DATE = TRANSACTION_CURRENT_DATE.minusMonths(1)
            setCurrentDate(viewType)
            binding?.tvCurrentDate?.text = TRANSACTION_CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
          }
          else -> {
            TRANSACTION_CURRENT_DATE = TRANSACTION_CURRENT_DATE.minusDays(1)
            setCurrentDate(viewType)
            binding?.tvCurrentDate?.text = TRANSACTION_CURRENT_DATE.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
          }
        }
      }
    }

    binding?.imgBtnNextDate?.setOnClickListener {
      viewLifecycleOwner.lifecycleScope.launch {
        val viewType = userSettingPref.getDateViewType().first()
        when (getDateViewType(viewType)) {
          DateViewType.MONTHLY -> {
            TRANSACTION_CURRENT_DATE = TRANSACTION_CURRENT_DATE.plusMonths(1)
            setCurrentDate(viewType)
            binding?.tvCurrentDate?.text =
              TRANSACTION_CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
          }

          else -> {
            TRANSACTION_CURRENT_DATE = TRANSACTION_CURRENT_DATE.plusDays(1)
            setCurrentDate(viewType)
            binding?.tvCurrentDate?.text =
              TRANSACTION_CURRENT_DATE.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
          }
        }
      }
    }

    binding?.btnOptions?.setOnClickListener {
      val bottomSheet = TransactionBottomSheet()
      bottomSheet.show(childFragmentManager, TransactionBottomSheet.TAG)
    }
  }

  private fun setupOverallMoney() {
    sharedViewModel.currentDate.observe(viewLifecycleOwner) {
      sharedViewModel.getTotalPengeluaranByDate(it.first, it.second)
    }

    sharedViewModel.currentDate.observe(viewLifecycleOwner) {
      sharedViewModel.getTotalPendapatanByDate(it.first, it.second)
    }

    sharedViewModel.currentDate.observe(viewLifecycleOwner) {
      sharedViewModel.getTotalByDate(it.first, it.second)
    }

    viewLifecycleOwner.lifecycleScope.launch {
      sharedViewModel.textPendapatan.collect {
        binding?.overviewIncomeExpenseTotal?.tvIncome?.text = it
      }
    }

    viewLifecycleOwner.lifecycleScope.launch {
      sharedViewModel.textPengeluaran.collect {
        binding?.overviewIncomeExpenseTotal?.tvExpense?.text = it
      }
    }

    viewLifecycleOwner.lifecycleScope.launch {
      sharedViewModel.textTotal.collect {
        binding?.overviewIncomeExpenseTotal?.tvTotal?.text = it
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

  private fun setCurrentDate(viewType: String) {
    val date: Pair<LocalDateTime, LocalDateTime> = when(getDateViewType(viewType)) {
      DateViewType.MONTHLY -> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, TRANSACTION_CURRENT_DATE.year)
        calendar.set(Calendar.MONTH, TRANSACTION_CURRENT_DATE.month.value - 1)
        val localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

        val fromDate = localDateTime.withDayOfMonth(1)
          .withHour(0)
          .withMinute(0)
        val toDate = localDateTime.with(TemporalAdjusters.lastDayOfMonth())
          .withHour(23)
          .withMinute(59)

        fromDate to toDate
      }
      DateViewType.DAILY -> {
        val fromDate = TRANSACTION_CURRENT_DATE.atStartOfDay()
        val toDate = TRANSACTION_CURRENT_DATE.atTime(LocalTime.MAX)

        fromDate to toDate
      }
    }
    sharedViewModel.setCurrentDate(date)
  }

  private fun setupViewPager() {
    transactionPagerAdapter = TransactionPagerAdapter(this).apply {
      addFragment(
        ExpenseFragment().apply {
            onItemChangedListener = this@TransactionFragment
        }, getString(R.string.title_expense), TransactionType.EXPENSE)

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
    refreshDate()
    setupOverallMoney()
    TRANSACTION_CURRENT_DATE = LocalDate.now()
    Timber.tag("TransactionFragment").d("OnStart() - $TRANSACTION_CURRENT_DATE")
  }

  override fun onResume() {
    super.onResume()
    Timber.tag("TransactionFragment").d("OnResume() - $TRANSACTION_CURRENT_DATE")
  }

  override fun onDestroyView() {
    Timber.tag("TransactionFragment").d("OnDestroyView() - $TRANSACTION_CURRENT_DATE")
    sharedViewModel.getDateViewType().removeObservers(viewLifecycleOwner)
    super.onDestroyView()
  }

  override fun onStop() {
    super.onStop()
    Timber.tag("TransactionFragment").d("OnStop() - $TRANSACTION_CURRENT_DATE")
  }

  override fun onItemChanged() {
    refreshDate()
    setupOverallMoney()
    transactionPagerAdapter.notifyItemRangeChanged(0, transactionPagerAdapter.itemCount)
  }

  companion object {
    private var TRANSACTION_CURRENT_DATE: LocalDate = LocalDate.now()
    const val EXTRA_TRANSACTION_TYPE = "transaction-type"
    const val EXTRA_TRANSACTION_CREATE_OR_EDIT = "transaction-create-or-edit"
  }
}