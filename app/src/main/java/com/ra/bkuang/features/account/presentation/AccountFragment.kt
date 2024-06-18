package com.ra.bkuang.features.account.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseFragment
import com.ra.bkuang.common.util.DrawerMenuToolbarListener
import com.ra.bkuang.common.util.Extension.hide
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.view.dialog.CautionDeleteDialog
import com.ra.bkuang.common.view.spinner.SpinnerItemOptions
import com.ra.bkuang.databinding.FragmentAccountBinding
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.account.presentation.adapter.RvAccountAdapter
import com.ra.bkuang.features.account.presentation.createaccount.CreateNewAccountActivity
import com.ra.bkuang.features.account.presentation.viewmodel.AccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(R.layout.fragment_account),
  RvAccountAdapter.OptionAccountClickCallBack {

  private val viewModel: AccountViewModel by viewModels()

  @Inject lateinit var accountAdapter: RvAccountAdapter

  private val deleteDialog = CautionDeleteDialog()

  var drawerMenuToolbarListener: DrawerMenuToolbarListener? = null

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    init()
    observer()
    setupActionBar()
  }

  private fun setupActionBar() {
    binding?.toolbar?.title = getString(R.string.title_menu_account)

    binding?.toolbar?.setNavigationOnClickListener {
      drawerMenuToolbarListener?.onDrawerMenuClicked()
    }

    binding?.toolbar?.setOnMenuItemClickListener { menuItem ->
      when(menuItem.itemId) {
        R.id.menu_add_account -> {
          val i = Intent(requireActivity(), CreateNewAccountActivity::class.java).apply {
            putExtra(
              EXTRA_TEXT,
              CREATE_ACCOUNT
            )
          }
          startActivity(i)
          true
        }

        else -> false
      }
    }
  }

  private fun observer() {
    lifecycleScope.launch {
      viewModel.uiState.collect { data ->
        setupAccountAdapter(data.accounts)

        binding?.tvTotal?.text = data.totalMoney
        binding?.tvIncome?.text = data.totalIncome
        binding?.tvExpense?.text = data.totalExpense

        data.isSuccessfulDelete?.let {
          if(it) {
            showShortToast(requireContext().resources.getString(R.string.msg_success))
            deleteDialog.dismiss()
          }
        }
      }
    }
  }

  private fun setupAccountAdapter(list: List<AkunModel>) {
    if(list.isEmpty()) {
      binding?.rvAccount?.hide(true)
      binding?.emptyLayout?.state = false
      return
    }

    binding?.rvAccount?.hide(false)
    binding?.emptyLayout?.state = true

    accountAdapter.optionAccountClickCallBack = this@AccountFragment
    accountAdapter.submitList(list)

    binding?.run {
      rvAccount.apply {
        adapter = accountAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
      }
    }
  }

  private fun init() {
    viewModel.getAllAccount()
    viewModel.getOverallMoney()
  }

  override fun option(options: SpinnerItemOptions, akun: AkunModel) {
    when(options) {
      SpinnerItemOptions.DELETE -> {
        deleteDialog.optionListener = object : CautionDeleteDialog.OptionListener {

          override fun onDelete() {
            viewModel.deleteAccount(akun)
          }

          override fun onCancel() { deleteDialog.dismiss() }

        }
        deleteDialog.show(parentFragmentManager, "Delete Dialog")
      }
      SpinnerItemOptions.EDIT -> {
        val i = Intent(requireActivity(), CreateNewAccountActivity::class.java).apply {
          putExtra(
            EXTRA_TEXT,
            EDIT_ACCOUNT
          )
          putExtra(ACCOUNT_MODEL, akun)
        }
        startActivity(i)
      }
    }
  }

  companion object {
    const val EDIT_ACCOUNT = "edit-account"
    const val CREATE_ACCOUNT = "create-account"
    const val ACCOUNT_MODEL = "account-model"
    const val EXTRA_TEXT = "extra-text"
  }
}