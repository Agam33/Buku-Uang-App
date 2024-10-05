package com.ra.bkuang.features.budget.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.budget.data.model.BudgetModel
import com.ra.bkuang.features.budget.domain.usecase.CreateBudgetUseCase
import com.ra.bkuang.features.budget.domain.usecase.DeleteBudgetByIdUseCase
import com.ra.bkuang.features.budget.domain.usecase.EditBudgetUseCase
import com.ra.bkuang.features.budget.domain.usecase.FindAllBudgetByDateUseCase
import com.ra.bkuang.features.budget.domain.usecase.FindBudgetByIdUseCase
import com.ra.bkuang.features.budget.presentation.BudgetFragmentUiState
import com.ra.bkuang.features.category.domain.usecase.FindCategoryByTypeUseCase
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
  private val findAllBudgetByDateUseCase: FindAllBudgetByDateUseCase,
  private val editBudgetUseCase: EditBudgetUseCase,
  private val deleteBudgetByIdUseCase: DeleteBudgetByIdUseCase,
  private val createBudgetUseCase: CreateBudgetUseCase,
  private val findKategoriByType: FindCategoryByTypeUseCase,
  private val findBudgetByIdUseCase: FindBudgetByIdUseCase
): BaseViewModel<BudgetFragmentUiState>(BudgetFragmentUiState()) {

  fun findBudgetById(id: UUID) {
    viewModelScope.launch {
      val budget = findBudgetByIdUseCase.invoke(id)
      _uiState.update {
        it.copy(
          budgetModel = budget
        )
      }
    }
  }

  fun findAllBudget(fromDate: LocalDate, toDate: LocalDate) {
    viewModelScope.launch {
      findAllBudgetByDateUseCase(fromDate, toDate).collect { allBudget ->
        _uiState.update {
          it.copy(
            budgetList = allBudget
          )
        }
      }
    }
  }

  fun setCategoryByType(transactionType: TransactionType) {
    viewModelScope.launch {
      findKategoriByType.invoke(transactionType).collect { data ->
        when (data) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                listCategoryByType = data.data ?: mutableListOf(),
                isEmptyState = false
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                listCategoryByType = emptyList(),
                isEmptyState = true
              )
            }
          }
        }
      }
    }
  }

  fun deleteBudgetById(id: UUID) {
    viewModelScope.launch {
      deleteBudgetByIdUseCase(id).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessfulDelete = res.data
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessfulDelete = false
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            isSuccessfulDelete = null
          )
        }
      }
    }
  }

  fun updateBudget(budgetModel: BudgetModel) {
    viewModelScope.launch {
      editBudgetUseCase(budgetModel).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessfulUpdate = res.data,
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessfulUpdate = false,
              )
            }
          }
        }
        _uiState.update {
          it.copy(
            isSuccessfulUpdate = null,
          )
        }
      }
    }
  }

  fun createBudget(budgetModel: BudgetModel) {
    viewModelScope.launch {
      createBudgetUseCase(
        budgetModel.bulanTahun,
        budgetModel.bulanTahun,
        budgetModel
      ).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessfulCreate = res.data,
              )
            }

            _uiState.update {
              it.copy(
                isSuccessfulCreate = null,
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessfulCreate = false,
              )
            }
          }
        }
      }
    }
  }
}