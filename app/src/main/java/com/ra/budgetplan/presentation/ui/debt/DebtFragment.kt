package com.ra.budgetplan.presentation.ui.debt

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.budgetplan.R
import com.ra.budgetplan.customview.dialog.CautionDeleteDialog
import com.ra.budgetplan.customview.dialog.CautionDeleteDialog.Companion.MSG_CAUTION_DIALOG
import com.ra.budgetplan.databinding.FragmentDebtBinding
import com.ra.budgetplan.domain.model.HutangModel
import com.ra.budgetplan.presentation.ui.debt.adapter.DebtAdapter
import com.ra.budgetplan.presentation.viewmodel.DebtViewModel
import com.ra.budgetplan.util.ActionType
import com.ra.budgetplan.util.Resource
import com.ra.budgetplan.util.StatusItem
import com.ra.budgetplan.util.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DebtFragment : Fragment() {

  private var _binding: FragmentDebtBinding? = null
  private val binding get() = _binding

  private val sharedViewModel: DebtViewModel by viewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    _binding = FragmentDebtBinding.inflate(layoutInflater, container, false)

    _binding?.vm = sharedViewModel
    _binding?.lifecycleOwner = viewLifecycleOwner

    setupRv()
    createNewDebt()

    return _binding?.root
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

    debtAdapter.setIconDeleteListener = object : DebtAdapter.OnIconDeleteListener {

      override fun setOnItemDelete(model: HutangModel) {
        val arg = Bundle()
        arg.putString(MSG_CAUTION_DIALOG, requireContext().resources.getString(R.string.msg_delete_debt))

        val cautionDeleteDialog = CautionDeleteDialog().apply {
          arguments  = arg
        }

        cautionDeleteDialog.onOptionItemClick = object : CautionDeleteDialog.OnOptionItemClick {

          override fun onDelete() {
            viewLifecycleOwner.lifecycleScope.launch {
              sharedViewModel.deleteHutang(model).collect { status ->
                when(status) {
                  StatusItem.LOADING -> {}
                  StatusItem.SUCCESS -> {
                    showShortToast(requireContext().resources.getString(R.string.msg_success))
                    refresh()
                    cautionDeleteDialog.dismiss()
                  }
                  StatusItem.FAILED -> {}
                }
              }
            }
          }

          override fun onCancel() {
            cautionDeleteDialog.dismiss()
          }
        }

        cautionDeleteDialog.show(childFragmentManager, "caution-delete-dialog")
      }
    }

    debtAdapter.setIconAlarmListener = object : DebtAdapter.OnIconAlarmListener {
      override fun setOnItemAlarm(model: HutangModel) {
        // TODO("Not yet implemented")
      }
    }

    debtAdapter.setIconEditListener = object : DebtAdapter.OnIconEditListener {
      override fun setOnItemEdit(model: HutangModel) {
        val i = Intent(requireContext(), CreateDebtActivity::class.java).apply {
          putExtra(DEBT_EXTRA_ACTION, ActionType.EDIT.name)
          putExtra(DEBT_MODEL, model)
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