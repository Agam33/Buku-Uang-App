package com.ra.bkuang.features.transaction.presentation.tab.income.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunUseCase
import com.ra.bkuang.features.category.domain.usecase.FindCategoryByTypeUseCase
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.SavePendapatanUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.UpdatePendapatanUseCase
import com.ra.bkuang.features.transaction.presentation.tab.income.uistate.CreateIncomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateIncomeViewModel @Inject constructor(
  private val savePendapatanUseCase: SavePendapatanUseCase,
  private val updatePendapatanUseCase: UpdatePendapatanUseCase,
  private val findKategoriByType: FindCategoryByTypeUseCase,
  private val getPendapatanByIdUseCase: GetPendapatanByIdUseCase,
  private val findAllAkunUseCase: FindAllAkunUseCase,
): BaseViewModel() {

  private var _uiState = MutableStateFlow(CreateIncomeUiState())
  val uiState = _uiState.asStateFlow()

  fun saveIncome(incomeModel: PendapatanModel) {
    viewModelScope.launch {
      savePendapatanUseCase(incomeModel).collect { res ->
        when(res) {
          is Result.Success -> {}
          is Result.Error -> {}
        }
      }
    }
  }

  fun updateIncome(newIncomeModel: PendapatanModel, oldIncomeModel: PendapatanModel) {
      viewModelScope.launch {
        updatePendapatanUseCase(newIncomeModel, oldIncomeModel).collect { res ->
          when(res) {
            is Result.Success -> {}
            is Result.Error -> {}
          }
        }
      }
  }

  fun getAllAccount() {
    _uiState.update {
      it.copy(
        accountList = findAllAkunUseCase.invoke()
      )
    }
  }

  fun getIncomeById(uuid: UUID) {
    viewModelScope.launch {
      val data = getPendapatanByIdUseCase(uuid)
      _uiState.update {
        it.copy(
          incomeModel = data
        )
      }
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
}
