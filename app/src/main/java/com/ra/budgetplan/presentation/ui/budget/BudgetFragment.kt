package com.ra.budgetplan.presentation.ui.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ra.budgetplan.R
import com.ra.budgetplan.base.BaseFragment
import com.ra.budgetplan.databinding.FragmentBudgetBinding
import com.ra.budgetplan.presentation.ui.budget.fragment.MonthBudgetFragment

class BudgetFragment : BaseFragment<FragmentBudgetBinding>(R.layout.fragment_budget) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    childFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, MonthBudgetFragment())
      .commit()
  }
}