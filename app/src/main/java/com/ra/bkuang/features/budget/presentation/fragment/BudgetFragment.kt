package com.ra.bkuang.features.budget.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Constants
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.util.Extension.toStringFormat
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.databinding.FragmentBudgetBinding
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.budget.data.local.DetailBudget
import com.ra.bkuang.features.budget.presentation.BudgetViewModel
import com.ra.bkuang.features.budget.presentation.CreateBudgetActivity
import com.ra.bkuang.features.budget.presentation.adapter.BudgetAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class BudgetFragment : BaseFragment<FragmentBudgetBinding>(R.layout.fragment_budget),
  BudgetAdapter.OnItemLongClickListener {

  private val viewModel: BudgetViewModel by viewModels()

  private var currentDate = LocalDate.now()

  @Inject lateinit var budgetAdapter: BudgetAdapter
  @Inject @IoDispatcherQualifier lateinit var ioDispatcher: CoroutineDispatcher

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupDate()
    createBudget()
    setupListBudget()
  }

  private fun setupListBudget() {
    binding?.rvBudget?.apply {
      adapter = budgetAdapter
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(requireContext())
    }
    budgetAdapter.onItemLongClickListener = this@BudgetFragment
  }

  private fun createBudget() {
    binding?.btnCreate?.setOnClickListener {
      val i = Intent(requireContext(), CreateBudgetActivity::class.java).apply {
        putExtra(CreateBudgetActivity.BUDGET_EXTRA_ACTION, ActionType.CREATE.name)
        putExtra(CreateBudgetActivity.BUDGET_EXTRA_DATE, currentDate.toString())
      }
      startActivity(i)
    }
  }

  private fun setupDate() {
    refreshDate()

    binding?.imgBtnNextDate?.setOnClickListener {
      currentDate = currentDate.plusMonths(1)
      refreshDate()
    }

    binding?.imgBtnPrevDate?.setOnClickListener {
      currentDate = currentDate.minusMonths(1)
      refreshDate()
    }
  }

  private fun refreshDate() {
    binding?.tvCurrentDate?.text = currentDate.toStringFormat(
      Constants.MONTHLY_DATE_FORMAT,
      Constants.LOCALE_ID
    )

    val fromDate = currentDate.withDayOfMonth(1)
    val toDate = currentDate.withDayOfMonth(currentDate.lengthOfMonth())

    lifecycleScope.launch(ioDispatcher) {// set to Dispatcher.IO, because data doesn't show if Dispatcher set as Main or Default.
      val data = viewModel.findAllBudget(fromDate, toDate)
      budgetAdapter.submitList(data) // will be computed on a background thread.
    }
  }

  override fun onStart() {
    super.onStart()
    currentDate = LocalDate.now()
    refreshDate()
  }

  override fun onItemDelete(detailBudget: DetailBudget) {
    binding?.root?.let {
      Snackbar.make(
        it,
        requireContext().getString(R.string.msg_delete),
        Snackbar.LENGTH_SHORT
      ).setAction("Ya") {
        lifecycleScope.launch {
          viewModel.deleteBudgetById(detailBudget.budget.uuid).collect { status ->
            when(status) {
              ResourceState.FAILED -> {
                showShortToast(requireContext().getString(R.string.msg_delete_failed))
              }
              ResourceState.SUCCESS -> {
                showShortToast(requireContext().getString(R.string.msg_success))
                refreshDate()
              }
              ResourceState.LOADING -> {}
            }
          }
        }
      }.show()
    }
  }

  override fun onItemUpdate(detailBudget: DetailBudget) {
    val i = Intent(requireContext(), CreateBudgetActivity::class.java).apply {
      putExtra(CreateBudgetActivity.BUDGET_EXTRA_DATE, currentDate.toString())
      putExtra(CreateBudgetActivity.BUDGET_EXTRA_ACTION, ActionType.EDIT.name)
      putExtra(CreateBudgetActivity.BUDGET_ID, detailBudget.budget.uuid.toString())
    }
    startActivity(i)
  }
}