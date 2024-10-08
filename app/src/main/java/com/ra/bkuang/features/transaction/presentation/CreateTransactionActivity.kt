package com.ra.bkuang.features.transaction.presentation

import android.os.Bundle
import android.view.MenuItem
import com.ra.bkuang.R
import com.ra.bkuang.databinding.ActivityCreateTransactionBinding
import com.ra.bkuang.common.base.BaseActivity
import com.ra.bkuang.features.transaction.presentation.TransactionFragment.Companion.EXTRA_TRANSACTION_CREATE_OR_EDIT
import com.ra.bkuang.features.transaction.presentation.TransactionFragment.Companion.EXTRA_TRANSACTION_TYPE
import com.ra.bkuang.features.transaction.presentation.TransactionType.Companion.getTransactionType
import com.ra.bkuang.features.transaction.presentation.tab.expense.CreateExpenseFragment
import com.ra.bkuang.features.transaction.presentation.tab.income.CreateIncomeFragment
import com.ra.bkuang.features.transaction.presentation.tab.transfer.CreateTransferFragment
import com.ra.bkuang.features.transaction.presentation.component.DetailTransactionDialog.Companion.EXTRA_TRANSACTION_ID
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Extension.setupActionBar
import com.ra.bkuang.common.util.getActionType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTransactionActivity : BaseActivity<ActivityCreateTransactionBinding>(R.layout.activity_create_transaction) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val type = intent.getStringExtra(EXTRA_TRANSACTION_TYPE) as String

    setupActionBar(binding.toolbar, getTitle(type))
    getTypeLayout(getTransactionType(type))
  }

  private fun getTypeLayout(type: TransactionType) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()

    val actionType = intent.getStringExtra(EXTRA_TRANSACTION_CREATE_OR_EDIT) as String

    val arg = Bundle()

    when(getActionType(actionType)) {
      ActionType.CREATE -> arg.putString(EXTRA_TRANSACTION_CREATE_OR_EDIT, actionType)
      ActionType.EDIT -> {
        val uuid = intent.getStringExtra(EXTRA_TRANSACTION_ID) as String
        arg.putString(EXTRA_TRANSACTION_CREATE_OR_EDIT, actionType)
        arg.putString(EXTRA_TRANSACTION_ID, uuid)
      }
    }

    when(type) {
      TransactionType.EXPENSE ->  {
        fragmentTransaction
          .replace(R.id.fragment_create_transaction_container,
            CreateExpenseFragment().apply {
              arguments = arg
            }, type.name)
      }
      TransactionType.INCOME -> {
        fragmentTransaction
          .replace(R.id.fragment_create_transaction_container,
            CreateIncomeFragment().apply {
              arguments = arg
            }, type.name)
      }
      TransactionType.TRANSFER -> {
        fragmentTransaction
          .replace(R.id.fragment_create_transaction_container,
            CreateTransferFragment().apply {
              arguments = arg
            }, type.name)
      }
    }

    fragmentTransaction
      .commit()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when(item.itemId) {
      android.R.id.home -> {
        onBackPressedDispatcher.onBackPressed()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun getTitle(type: String): String =
    when(type) {
      TransactionType.TRANSFER.name -> getString(R.string.title_transfer)
      TransactionType.INCOME.name -> getString(R.string.title_income)
      else -> getString(R.string.title_expense)
    }
}