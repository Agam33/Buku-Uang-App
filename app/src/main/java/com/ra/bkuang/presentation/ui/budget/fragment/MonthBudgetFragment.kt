package com.ra.bkuang.presentation.ui.budget.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ra.bkuang.R
import com.ra.bkuang.base.BaseFragment
import com.ra.bkuang.databinding.FragmentMonthBudgetBinding
import com.ra.bkuang.data.entity.DetailBudget
import com.ra.bkuang.presentation.ui.budget.CreateBudgetActivity
import com.ra.bkuang.presentation.ui.budget.CreateBudgetActivity.Companion.BUDGET_EXTRA_ACTION
import com.ra.bkuang.presentation.ui.budget.CreateBudgetActivity.Companion.BUDGET_EXTRA_DATE
import com.ra.bkuang.presentation.ui.budget.CreateBudgetActivity.Companion.BUDGET_ID
import com.ra.bkuang.presentation.ui.budget.adapter.BudgetAdapter
import com.ra.bkuang.presentation.viewmodel.BudgetViewModel
import com.ra.bkuang.util.ActionType
import com.ra.bkuang.util.Constants.LOCALE_ID
import com.ra.bkuang.util.Constants.MONTHLY_DATE_FORMAT
import com.ra.bkuang.util.Extension.showShortToast
import com.ra.bkuang.util.Extension.toStringFormat
import com.ra.bkuang.util.ResourceState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate

@AndroidEntryPoint
class MonthBudgetFragment : BaseFragment<FragmentMonthBudgetBinding>(R.layout.fragment_month_budget), BudgetAdapter.OnItemLongClickListener {
  private val viewModel: BudgetViewModel by viewModels()

  private var CURRENT_DATE = LocalDate.now()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupDate()
    createBudget()
  }

  private fun createBudget() {
    binding?.run {
      btnCreate.setOnClickListener {
        val i = Intent(requireContext(), CreateBudgetActivity::class.java).apply {
          putExtra(BUDGET_EXTRA_ACTION, ActionType.CREATE.name)
          putExtra(BUDGET_EXTRA_DATE, CURRENT_DATE.toString())
        }
        startActivity(i)
      }
    }
  }

  private fun setupDate() {
    binding?.run {
      refreshDate()

      imgBtnNextDate.setOnClickListener {
        CURRENT_DATE = CURRENT_DATE.plusMonths(1)
        refreshDate()
      }

      imgBtnPrevDate.setOnClickListener {
        CURRENT_DATE = CURRENT_DATE.minusMonths(1)
        refreshDate()
      }
    }
  }

  private fun refreshDate() {
    binding?.run {
      tvCurrentDate.text = CURRENT_DATE.toStringFormat(MONTHLY_DATE_FORMAT, LOCALE_ID)

      val fromDate = CURRENT_DATE.withDayOfMonth(1)
      val toDate = CURRENT_DATE.withDayOfMonth(CURRENT_DATE.lengthOfMonth())

      viewModel.findAllBudget(fromDate, toDate)

      viewModel.listBudget.observe(viewLifecycleOwner) {
        val budgetAdapter = BudgetAdapter()
        budgetAdapter.submitList(it)
        budgetAdapter.onItemLongClickListener = this@MonthBudgetFragment

        rvBudget.apply {
          adapter = budgetAdapter
          setHasFixedSize(true)
          layoutManager = LinearLayoutManager(requireContext())
        }
      }
    }
  }

  override fun onStart() {
    super.onStart()
    CURRENT_DATE = LocalDate.now()
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
      putExtra(BUDGET_EXTRA_DATE, CURRENT_DATE.toString())
      putExtra(BUDGET_EXTRA_ACTION, ActionType.EDIT.name)
      putExtra(BUDGET_ID, detailBudget.budget.uuid.toString())
    }
    startActivity(i)
  }
}