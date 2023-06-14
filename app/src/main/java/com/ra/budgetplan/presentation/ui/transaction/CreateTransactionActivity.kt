package com.ra.budgetplan.presentation.ui.transaction

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ra.budgetplan.R
import com.ra.budgetplan.databinding.ActivityCreateTransactionBinding
import com.ra.budgetplan.presentation.ui.transaction.TransactionFragment.Companion.EXTRA_TRANSACTION_TYPE
import com.ra.budgetplan.presentation.ui.transaction.fragment.CreateExpenseFragment
import com.ra.budgetplan.presentation.ui.transaction.fragment.CreateIncomeFragment
import com.ra.budgetplan.presentation.ui.transaction.fragment.CreateTransferFragment
import com.ra.budgetplan.util.setupNoActionbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateTransactionActivity : AppCompatActivity() {

  private val binding: ActivityCreateTransactionBinding by lazy {
    ActivityCreateTransactionBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    val type = intent.getStringExtra(EXTRA_TRANSACTION_TYPE) as String

    setupNoActionbar(binding.toolbar, getTitle(type))
    getTypeLayout(getTransactionType(type))
  }

  private fun getTypeLayout(type: TransactionType) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    when(type) {
      TransactionType.EXPENSE ->  {
        fragmentTransaction
          .replace(R.id.fragment_create_transaction_container,
            CreateExpenseFragment(), type.name)
      }
      TransactionType.INCOME -> {
        fragmentTransaction
          .replace(R.id.fragment_create_transaction_container,
            CreateIncomeFragment(), type.name)
      }
      TransactionType.TRANSFER -> {
        fragmentTransaction
          .replace(R.id.fragment_create_transaction_container,
            CreateTransferFragment(), type.name)
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