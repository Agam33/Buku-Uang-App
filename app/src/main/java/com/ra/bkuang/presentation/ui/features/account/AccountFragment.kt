package com.ra.bkuang.presentation.ui.features.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ra.bkuang.R
import com.ra.bkuang.presentation.ui.base.BaseFragment
import com.ra.bkuang.presentation.ui.customview.dialog.CautionDeleteDialog
import com.ra.bkuang.presentation.ui.customview.spinner.SpinnerItemOptions
import com.ra.bkuang.databinding.FragmentAccountBinding
import com.ra.bkuang.domain.model.AkunModel
import com.ra.bkuang.presentation.viewmodel.AccountViewModel
import com.ra.bkuang.presentation.util.Extension.showShortToast
import com.ra.bkuang.domain.util.Resource
import com.ra.bkuang.domain.util.ResourceState
import com.ra.bkuang.presentation.ui.features.account.adapter.RvAccountAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>(R.layout.fragment_account),
  RvAccountAdapter.OnOptionAccountClickCallBack {

  private val viewModel: AccountViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    createNewAccount()
    setupAccountList()
    observer()
    refresh()
  }

  private fun setupAccountList() {
    viewModel.accounts.observe(viewLifecycleOwner) {
      when(it) {
        is Resource.Success -> {
          viewModel.setRvState(false)
          viewModel.setEmptyLayoutState(true)
          setupAccountAdapter(it.data)
        }
        is Resource.Empty -> {
          viewModel.setRvState(true)
          viewModel.setEmptyLayoutState(false)
        }
        is Resource.Loading -> {}
      }
    }
  }

  private fun setupAccountAdapter(list: List<AkunModel>?) {
    val accountAdapter = RvAccountAdapter()
    accountAdapter.onOptionAccountClickCallBack = this@AccountFragment
    accountAdapter.submitList(list)

    binding?.run {
      rvAccount.apply {
        adapter = accountAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(requireContext())
      }
    }
  }

  private fun refresh() {
    viewModel.getAllAccount()
    viewModel.getOverallMoney()
  }

  private fun observer() {
    binding?.lifecycleOwner = viewLifecycleOwner
    binding?.vm = viewModel

    viewModel.emptyMessageState.observe(viewLifecycleOwner) {
      binding?.emptyLayout?.state = it
    }
  }

  private fun createNewAccount() {
    binding?.run {
      fabAddAccount.setOnClickListener {
        val i = Intent(requireActivity(), com.ra.bkuang.presentation.ui.features.account.CreateNewAccountActivity::class.java).apply {
          putExtra(
            com.ra.bkuang.presentation.ui.features.account.AccountFragment.Companion.EXTRA_TEXT,
            com.ra.bkuang.presentation.ui.features.account.AccountFragment.Companion.CREATE_ACCOUNT
          )
        }
        startActivity(i)
      }
    }
  }

  override fun onStart() {
    super.onStart()
    refresh()
  }

  override fun option(options: SpinnerItemOptions, akun: AkunModel) {
    when(options) {
      SpinnerItemOptions.DELETE -> {
        val deleteDialog = CautionDeleteDialog()

        deleteDialog.onOptionItemClick = object : CautionDeleteDialog.OnOptionItemClick {

          override fun onDelete() {
            viewLifecycleOwner.lifecycleScope.launch {
              viewModel.deleteAccount(akun).collect { status ->
                when(status) {
                  ResourceState.SUCCESS -> {
                    refresh()
                    showShortToast(requireContext().resources.getString(R.string.msg_success))
                    deleteDialog.dismiss()
                  }
                  ResourceState.LOADING -> {}
                  ResourceState.FAILED -> {}
                }
              }
            }
          }

          override fun onCancel() { deleteDialog.dismiss() }

        }
        deleteDialog.show(parentFragmentManager, "Delete Dialog")
      }
      SpinnerItemOptions.EDIT -> {
        val i = Intent(requireActivity(), com.ra.bkuang.presentation.ui.features.account.CreateNewAccountActivity::class.java).apply {
          putExtra(
            com.ra.bkuang.presentation.ui.features.account.AccountFragment.Companion.EXTRA_TEXT,
            com.ra.bkuang.presentation.ui.features.account.AccountFragment.Companion.EDIT_ACCOUNT
          )
          putExtra(com.ra.bkuang.presentation.ui.features.account.AccountFragment.Companion.ACCOUNT_MODEL, akun)
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