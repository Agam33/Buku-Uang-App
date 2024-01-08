package com.ra.bkuang.presentation.ui.features.budget

import android.os.Bundle
import com.ra.bkuang.R
import com.ra.bkuang.presentation.ui.base.BaseFragment
import com.ra.bkuang.databinding.FragmentBudgetBinding
import com.ra.bkuang.presentation.ui.features.budget.fragment.MonthBudgetFragment

class BudgetFragment : BaseFragment<FragmentBudgetBinding>(R.layout.fragment_budget) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    childFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, MonthBudgetFragment())
      .commit()
  }
}