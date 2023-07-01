package com.ra.budgetplan.presentation.ui.transaction

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
import com.ra.budgetplan.R
import com.ra.budgetplan.customview.dialog.date.DateViewTypeDialog
import com.ra.budgetplan.data.local.preferences.UserSettingPref
import com.ra.budgetplan.databinding.FragmentTransactionBinding
import com.ra.budgetplan.presentation.ui.transaction.adapter.DateViewType
import com.ra.budgetplan.presentation.ui.transaction.adapter.TransactionPagerAdapter
import com.ra.budgetplan.presentation.ui.transaction.adapter.getDateViewType
import com.ra.budgetplan.presentation.ui.transaction.fragment.ExpenseFragment
import com.ra.budgetplan.presentation.ui.transaction.fragment.IncomeFragment
import com.ra.budgetplan.presentation.ui.transaction.fragment.TransferFragment
import com.ra.budgetplan.presentation.viewmodel.TransactionViewModel
import com.ra.budgetplan.util.DAILY_DATE_FORMAT
import com.ra.budgetplan.util.LOCALE_ID
import com.ra.budgetplan.util.MONTHLY_DATE_FORMAT
import com.ra.budgetplan.util.OnItemChangedListener
import com.ra.budgetplan.util.toStringFormat
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

  private var GLOBAL_CURRENT_DATE: LocalDate = LocalDate.now()

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
    Timber.tag("TransactionFragment").d("OnCreateView() - $GLOBAL_CURRENT_DATE")
    return binding?.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Timber.tag("TransactionFragment").d("OnViewCreated() - $GLOBAL_CURRENT_DATE")
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
          binding?.tvCurrentDate?.text = GLOBAL_CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
        }
        else -> {
          setCurrentDate(it)
          binding?.tvCurrentDate?.text = GLOBAL_CURRENT_DATE.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
        }
      }
    }
  }

  private fun setupButtonDate() {
    Timber.tag("TransactionFragment").d("setupDate() - $GLOBAL_CURRENT_DATE")
    refreshDate()

    binding?.imgBtnPrevDate?.setOnClickListener {
      viewLifecycleOwner.lifecycleScope.launch {
        val viewType = userSettingPref.getDateViewType().first()
        when (getDateViewType(viewType)) {
          DateViewType.MONTHLY -> {
            GLOBAL_CURRENT_DATE = GLOBAL_CURRENT_DATE.minusMonths(1)
            setCurrentDate(viewType)
            binding?.tvCurrentDate?.text = GLOBAL_CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
          }
          else -> {
            GLOBAL_CURRENT_DATE = GLOBAL_CURRENT_DATE.minusDays(1)
            setCurrentDate(viewType)
            binding?.tvCurrentDate?.text = GLOBAL_CURRENT_DATE.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
          }
        }
      }
    }

    binding?.imgBtnNextDate?.setOnClickListener {
      viewLifecycleOwner.lifecycleScope.launch {
        val viewType = userSettingPref.getDateViewType().first()
        when (getDateViewType(viewType)) {
          DateViewType.MONTHLY -> {
            GLOBAL_CURRENT_DATE = GLOBAL_CURRENT_DATE.plusMonths(1)
            setCurrentDate(viewType)
            binding?.tvCurrentDate?.text =
              GLOBAL_CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
          }

          else -> {
            GLOBAL_CURRENT_DATE = GLOBAL_CURRENT_DATE.plusDays(1)
            setCurrentDate(viewType)
            binding?.tvCurrentDate?.text =
              GLOBAL_CURRENT_DATE.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
          }
        }
      }
    }

    binding?.btnViewType?.setOnClickListener {
      val dialog = DateViewTypeDialog()
      dialog.show(parentFragmentManager, "dialog_date_view_type")
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
        }
        startActivity(i)
      }
    }
  }

  private fun setCurrentDate(viewType: String) {
    val date: Pair<LocalDateTime, LocalDateTime> = when(getDateViewType(viewType)) {
      DateViewType.MONTHLY -> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, GLOBAL_CURRENT_DATE.year)
        calendar.set(Calendar.MONTH, GLOBAL_CURRENT_DATE.month.value - 1)
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
        val fromDate = GLOBAL_CURRENT_DATE.atStartOfDay()
        val toDate = GLOBAL_CURRENT_DATE.atTime(LocalTime.MAX)

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
    }
    tabLayoutMediator.attach()
  }

  companion object {
    const val EXTRA_TRANSACTION_TYPE = "transaction-type"
  }

  override fun onStart() {
    super.onStart()
    refreshDate()
    GLOBAL_CURRENT_DATE = LocalDate.now()
    Timber.tag("TransactionFragment").d("OnStart() - $GLOBAL_CURRENT_DATE")
  }

  override fun onResume() {
    super.onResume()
    Timber.tag("TransactionFragment").d("OnResume() - $GLOBAL_CURRENT_DATE")
  }

  override fun onDestroyView() {
    Timber.tag("TransactionFragment").d("OnDestroyView() - $GLOBAL_CURRENT_DATE")
    sharedViewModel.getDateViewType().removeObservers(viewLifecycleOwner)
    super.onDestroyView()
  }

  override fun onStop() {
    super.onStop()
    Timber.tag("TransactionFragment").d("OnStop() - $GLOBAL_CURRENT_DATE")
  }

  override fun onItemChanged() {
    refreshDate()
    transactionPagerAdapter.notifyItemRangeChanged(0, transactionPagerAdapter.itemCount)
  }
}