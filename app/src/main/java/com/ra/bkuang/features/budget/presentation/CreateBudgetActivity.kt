package com.ra.bkuang.features.budget.presentation

import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.ra.bkuang.R
import com.ra.bkuang.common.base.BaseActivity
import com.ra.bkuang.common.util.ActionType
import com.ra.bkuang.common.util.Extension.setupActionBar
import com.ra.bkuang.common.util.Extension.showShortToast
import com.ra.bkuang.common.util.getActionType
import com.ra.bkuang.common.view.spinner.TransactionSpinnerAdapter
import com.ra.bkuang.databinding.ActivityCreateBudgetBinding
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.features.budget.presentation.viewmodel.BudgetViewModel
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@AndroidEntryPoint
class CreateBudgetActivity : BaseActivity<ActivityCreateBudgetBinding>(R.layout.activity_create_budget) {

  private val viewModel: BudgetViewModel by viewModels()

  private lateinit var listCategoryAdapter: TransactionSpinnerAdapter<KategoriModel>

  private var categoryId: UUID? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    observer()
    setupCategories()

    setupActionBar(
      binding.toolbar,
      getString(R.string.txt_budget),
      true
    )

    val actionType = intent?.getStringExtra(BUDGET_EXTRA_ACTION) as String
    when(getActionType(actionType)) {
      ActionType.CREATE -> {
        createBudget()
      }
      ActionType.EDIT -> {
        val budgetId = intent?.getStringExtra(BUDGET_ID) as String
        viewModel.findBudgetById(UUID.fromString(budgetId))
      }
    }
  }

  private fun setupView(it: BudgetModel) {
    binding.edtLimit.text = Editable.Factory.getInstance().newEditable(it.maxPengeluaran.toString())
  }

  private fun editBudget(budgetModel: BudgetModel) {
    binding.btnSave.setOnClickListener {
      val limit = binding.edtLimit.text.toString()

      if(limit.length > 10) {
        showShortToast(getString(R.string.msg_too_over))
        return@setOnClickListener
      }

      if(limit.isEmpty() || limit[0] == '0') {
        showShortToast(getString(R.string.msg_empty))
        return@setOnClickListener
      }

      budgetModel.maxPengeluaran = limit.toInt()
      budgetModel.idKategori = categoryId ?: return@setOnClickListener
      viewModel.updateBudget(budgetModel)
    }
  }

  private fun createBudget() {
    binding.btnSave.setOnClickListener {
      val limit = binding.edtLimit.text.toString()

      if(limit.length > 10) {
        showShortToast(getString(R.string.msg_too_over))
        return@setOnClickListener
      }

      if(limit.isEmpty() || limit[0] == '0') {
        showShortToast(getString(R.string.msg_empty))
        return@setOnClickListener
      }

      val date = intent?.getStringExtra(BUDGET_EXTRA_DATE) as String

      val budgetDate = LocalDate.parse(date)

      val budgetModel = BudgetModel(
        uuid = UUID.randomUUID(),
        idKategori = categoryId ?: return@setOnClickListener,
        bulanTahun = budgetDate,
        updatedAt = LocalDateTime.now(),
        createdAt = LocalDateTime.now(),
        maxPengeluaran = limit.toInt(),
        pengeluaran = 0,
        deskripsi = ""
      )
      viewModel.createBudget(budgetModel)
    }
  }

  private fun setupCategories() {
    binding.spCategory.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
      override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, p3: Long) {
        val model = adapter?.getItemAtPosition(position) as KategoriModel
        categoryId = model.uuid
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {}
    }
  }

  private fun setupListCategory(listCategory: List<KategoriModel>) {
    binding.run {
      listCategoryAdapter = TransactionSpinnerAdapter(this@CreateBudgetActivity, 0, listCategory)
      spCategory.apply {
        adapter = listCategoryAdapter
      }
    }
  }

  private fun observer() {
    viewModel.setCategoryByType(TransactionType.PENGELUARAN)
    lifecycleScope.launch {
      viewModel.uiState.collect { uiState ->
        setupListCategory(uiState.listCategoryByType)

        uiState.budgetModel?.let {
          setupView(uiState.budgetModel)
          editBudget(uiState.budgetModel)
        }

        uiState.isSuccessfulCreate?.let {
          if(it) {
            showShortToast(getString(R.string.msg_success))
            onBackPressedDispatcher.onBackPressed()
          } else {
            showShortToast(getString(R.string.msg_already_exist))
          }
        }

        uiState.isSuccessfulUpdate?.let {
          if(it) {
            showShortToast(getString(R.string.msg_success))
            onBackPressedDispatcher.onBackPressed()
          } else {
            showShortToast(getString(R.string.msg_failed_update))
          }
        }
      }
    }
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

  companion object {
    const val BUDGET_EXTRA_ACTION = "create-or-edit-budget"
    const val BUDGET_EXTRA_DATE = "date-budget"
    const val BUDGET_ID = "budget-id"
  }
}