package com.ra.bkuang.features.transaction.presentation.tab.expense.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.usecase.FindAkunByIdUseCase
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunUseCase
import com.ra.bkuang.features.category.domain.usecase.FindCategoryByTypeUseCase
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetPengeluaranByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.SavePengeluaranUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.UpdatePengeluaranUseCase
import com.ra.bkuang.features.transaction.presentation.tab.expense.uistate.CreateExpenseUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateExpenseViewModel @Inject constructor(
  private val savePengeluaranUseCase: SavePengeluaranUseCase,
  private val getPengeluaranByIdUseCase: GetPengeluaranByIdUseCase,
  private val updatePengeluaranUseCase: UpdatePengeluaranUseCase,
  private val findKategoriByType: FindCategoryByTypeUseCase,
  private val findAllAkunUseCase: FindAllAkunUseCase,
  private val findAkunByIdUseCase: FindAkunByIdUseCase
): BaseViewModel() {

  private val _uiState = MutableStateFlow(CreateExpenseUiState())
  val uiState = _uiState.asStateFlow()

  fun getAllAccount() {
    _uiState.update {
      it.copy(
        accountList = findAllAkunUseCase.invoke()
      )
    }
  }

  fun getExpenseById(uuid: UUID) {
    viewModelScope.launch {
      val data = getPengeluaranByIdUseCase.invoke(uuid)
      _uiState.update {
        it.copy(
          expenseModel = data
        )
      }
    }
  }

  fun checkAccountMoney(accountId: UUID, amount: Int) {
    viewModelScope.launch {
      val account = findAkunByIdUseCase.invoke(accountId)
      if(account.total - amount >= 0) {
        onSave(true)
      } else {
        showSaveAlert(true)
      }
    }
  }

  fun onSave(state: Boolean) {
    _uiState.update {
      it.copy(
        isSave = state
      )
    }
  }

  fun showSaveAlert(state: Boolean) {
    _uiState.update {
      it.copy(
        showSaveAlert = state
      )
    }
  }

  fun setCategoryByType(tipeKategori: TransactionType) {
    viewModelScope.launch {
      findKategoriByType(tipeKategori).collect { res ->
        when (res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                categoryList = res.data ?: mutableListOf()
              )
            }
          }
          is Result.Error -> Unit
        }
      }
    }
  }

  fun updateExpense(newExpenseModel: PengeluaranModel, oldExpenseModel: PengeluaranModel) {
    viewModelScope.launch {
      updatePengeluaranUseCase.invoke(newExpenseModel, oldExpenseModel).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessful = true,
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessful = false,
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            isSuccessful = null
          )
        }
      }
    }
  }

  fun saveExpense(expenseModel: PengeluaranModel) {
    viewModelScope.launch {
      savePengeluaranUseCase.invoke(expenseModel).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessful = true,
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessful = false,
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            isSuccessful = null
          )
        }
      }
    }
  }
}