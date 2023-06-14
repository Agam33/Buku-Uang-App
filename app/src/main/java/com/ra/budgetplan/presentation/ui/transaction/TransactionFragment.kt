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
class TransactionFragment : Fragment() {

  @Inject
  lateinit var userSettingPref: UserSettingPref

  private val sharedViewModel: TransactionViewModel by activityViewModels()

  private var _binding: FragmentTransactionBinding? = null
  private val binding get() = _binding

  private lateinit var transactionPagerAdapter: TransactionPagerAdapter
  private lateinit var tabLayoutMediator: TabLayoutMediator

  private var currentDate = LocalDate.now()

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
    setupDate()
    createTransaction()
  }

  private fun setupDate() {
    sharedViewModel.setCurrentDate(currentDate)

    sharedViewModel.getDateViewType().observe(viewLifecycleOwner) {
      when (getDateViewType(it)) {
        DateViewType.MONTHLY -> {
          setCurrentDate(currentDate, DateViewType.MONTHLY)
          binding?.tvCurrentDate?.text = currentDate.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
        }
        else -> {
          setCurrentDate(currentDate, DateViewType.DAILY)
          binding?.tvCurrentDate?.text = currentDate.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
        }
      }
    }

    binding?.imgBtnPrevDate?.setOnClickListener {
      lifecycleScope.launch {
        val dateViewType = userSettingPref.getDateViewType().first()
        when (getDateViewType(dateViewType)) {
          DateViewType.MONTHLY -> {
            currentDate = currentDate.minusMonths(1)
            sharedViewModel.setCurrentDate(currentDate)
            setCurrentDate(currentDate, DateViewType.MONTHLY)
            binding?.tvCurrentDate?.text = currentDate.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
          }
          else -> {
            currentDate = currentDate.minusDays(1)
            sharedViewModel.setCurrentDate(currentDate)
            setCurrentDate(currentDate, DateViewType.DAILY)
            binding?.tvCurrentDate?.text = currentDate.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
          }
        }
      }
    }

    binding?.imgBtnNextDate?.setOnClickListener {
      lifecycleScope.launch {
        val dateViewType = userSettingPref.getDateViewType().first()
        when (getDateViewType(dateViewType)) {
          DateViewType.MONTHLY -> {
            currentDate = currentDate.plusMonths(1)
            sharedViewModel.setCurrentDate(currentDate)
            setCurrentDate(currentDate, DateViewType.MONTHLY)
            binding?.tvCurrentDate?.text = currentDate.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)
          }
          else -> {
            currentDate = currentDate.plusDays(1)
            sharedViewModel.setCurrentDate(currentDate)
            setCurrentDate(currentDate, DateViewType.DAILY)
            binding?.tvCurrentDate?.text = currentDate.toStringFormat(DAILY_DATE_FORMAT, LOCALE_ID)
          }
        }
      }
    }

    binding?.btnViewType?.setOnClickListener {
      val dialog = DateViewTypeDialog()
      dialog.show(parentFragmentManager, "dialog_date_view_type")
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

  private fun setCurrentDate(currentDate: LocalDate, viewType: DateViewType) {
      when(viewType) {
        DateViewType.MONTHLY -> {

          val calendar = Calendar.getInstance()
          calendar.set(Calendar.YEAR, currentDate.year)
          calendar.set(Calendar.MONTH, currentDate.month.value - 1)
          val localDateTime = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault())

          val fromDate = localDateTime.withDayOfMonth(1)
            .withHour(0)
            .withMinute(0)
          val toDate = localDateTime.with(TemporalAdjusters.lastDayOfMonth())
            .withHour(23)
            .withMinute(59)

          sharedViewModel.setTransactionDate(fromDate, toDate)
        }
        DateViewType.DAILY -> {

          val startOfDay = currentDate.atStartOfDay()
          val endOfDay = currentDate.atTime(LocalTime.MAX)

          sharedViewModel.setTransactionDate(startOfDay, endOfDay)
        }
      }
    }

  private fun setupViewPager() {
    transactionPagerAdapter = TransactionPagerAdapter(requireActivity()).apply {
      addFragment(ExpenseFragment(), getString(R.string.title_expense), TransactionType.EXPENSE)
      addFragment(IncomeFragment(), getString(R.string.title_income), TransactionType.INCOME)
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
    Timber.tag("TRANSACTION-fragment").d("onDestroy")
    super.onDestroy()
  }

  companion object {
    const val EXTRA_TRANSACTION_TYPE = "transaction-type"
  }
}