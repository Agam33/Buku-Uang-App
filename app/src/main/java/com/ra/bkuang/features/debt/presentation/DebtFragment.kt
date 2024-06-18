package com.ra.bkuang.features.debt.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.DrawerMenuToolbarListener
import com.ra.bkuang.common.util.Extension.hide
import com.ra.bkuang.databinding.FragmentDebtBinding
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.presentation.adapter.DebtAdapter
import com.ra.bkuang.features.debt.presentation.detail.DetailDebtActivity
import com.ra.bkuang.features.debt.presentation.viewmodel.DebtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DebtFragment : BaseFragment<FragmentDebtBinding>(R.layout.fragment_debt) {

  private val sharedViewModel: DebtViewModel by viewModels()

  @Inject lateinit var debtAdapter: DebtAdapter

  var drawerMenuToolbarListener: DrawerMenuToolbarListener? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    observer()
    setupActionBar()
    createNewDebt()
  }

  private fun setupActionBar() {
    binding?.toolbar?.title = getString(R.string.txt_debt)

    binding?.toolbar?.setNavigationOnClickListener {
      drawerMenuToolbarListener?.onDrawerMenuClicked()
    }

    binding?.toolbar?.setOnMenuItemClickListener { menuItem ->
      true
    }
  }

  private fun observer() {
    lifecycleScope.launch {
      sharedViewModel.uiState.collect { uiState ->
        if(uiState.debtList.isEmpty()) {
          binding?.rvDebtList?.hide(true)
          binding?.emptyLayout?.state = false
        } else {
          binding?.rvDebtList?.hide(false)
          binding?.emptyLayout?.state = true
          setupList(uiState.debtList)
        }
      }
    }
  }

  private fun createNewDebt() {
    binding?.fabAddDebt?.setOnClickListener {
      val i = Intent(requireContext(), CreateDebtActivity::class.java).apply {
        putExtra(DEBT_EXTRA_ACTION, ActionType.CREATE.name)
      }
      startActivity(i)
    }
  }

  private fun refresh() {
    sharedViewModel.getHutangList()
  }

  private fun setupList(list: List<HutangModel>?) {

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