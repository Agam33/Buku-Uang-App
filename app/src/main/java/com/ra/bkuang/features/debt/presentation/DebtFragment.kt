package com.ra.bkuang.features.debt.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.databinding.FragmentDebtBinding
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.features.debt.presentation.adapter.DebtAdapter
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DebtFragment : BaseFragment<FragmentDebtBinding>(R.layout.fragment_debt) {

  private val sharedViewModel: DebtViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding?.vm = sharedViewModel
    binding?.lifecycleOwner = viewLifecycleOwner
    setupRv()
    createNewDebt()
  }

  private fun createNewDebt() {
    binding?.fabAddDebt?.setOnClickListener {
      val i = Intent(requireContext(), CreateDebtActivity::class.java).apply {
        putExtra(DEBT_EXTRA_ACTION, ActionType.CREATE.name)
      }
      startActivity(i)
    }
  }

  private fun setupRv() {
    refresh()
    sharedViewModel.hutangList.observe(viewLifecycleOwner) {
      when(it) {
        is Resource.Empty -> {
          sharedViewModel.setState(rvState = true, emptyState = false)
        }

        is Resource.Success -> {
          sharedViewModel.setState(rvState = false, emptyState = true)
          setupList(it.data)
        }

        is Resource.Loading -> {}
      }
    }
  }

  private fun refresh() {
    sharedViewModel.getHutangList()
  }

  private fun setupList(list: List<HutangModel>?) {
    val debtAdapter = DebtAdapter()
    debtAdapter.submitList(list)

    debtAdapter.setItemClickListener = object : DebtAdapter.OnItemClickListener {
      override fun setOnItemClickCallback(model: HutangModel) {
        val i = Intent(requireContext(), DetailDebtActivity::class.java).apply {
          putExtra(DEBT_MODEL_ID, model.uuid.toString())
        }
        startActivity(i)
      }
    }

    binding?.rvDebtList?.apply {
      adapter = debtAdapter
      setHasFixedSize(true)
      layoutManager = LinearLayoutManager(context)
    }
  }

  override fun onStart() {
    super.onStart()
    refresh()
  }

  companion object {
    const val DEBT_EXTRA_ACTION = "debt-extra-action"
    const val DEBT_MODEL = "debt-model"
    const val DEBT_MODEL_ID = "debt-model-id"
  }
}