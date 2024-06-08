package com.ra.bkuang.features.debt.presentation.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunUseCase
import com.ra.bkuang.features.debt.domain.usecase.CancelAlarmDebtUseCase
import com.ra.bkuang.features.debt.domain.usecase.DeleteHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.DeleteRecordPembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindAllRecordPembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdWithFlowUseCase
import com.ra.bkuang.features.debt.domain.usecase.GetSizeListPembayaranHutangByIdUseCase
import com.ra.bkuang.features.debt.domain.usecase.SavePembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.SetAlarmDebtUseCase
import com.ra.bkuang.features.debt.domain.usecase.UpdatePembayaranHutangUseCase
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DetailDebtViewModel @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val findAllAkunUseCase: FindAllAkunUseCase,
  private val findAllRecordPembayaranHutangUseCase: FindAllRecordPembayaranHutangUseCase,
  private val savePembayaranHutangUseCase: SavePembayaranHutangUseCase,
  private val findHutangByIdWithFlowUseCase: FindHutangByIdWithFlowUseCase,
  private val findHutangByIdUseCase: FindHutangByIdUseCase,
  private val deleteRecordPembayaranHutangUseCase: DeleteRecordPembayaranHutangUseCase,
  private val updatePembayaranHutangUseCase: UpdatePembayaranHutangUseCase,
  private val getSizeListPembayaranHutangByIdUseCase: GetSizeListPembayaranHutangByIdUseCase,
  private val deleteHutangUseCase: DeleteHutangUseCase,
  private val cancelAlarmDebtUseCase: CancelAlarmDebtUseCase,
  private val setAlarmDebtUseCase: SetAlarmDebtUseCase,
): BaseViewModel() {

  private var _detailDebtUiState = MutableStateFlow(DetailDebtUiState())
  val detailDebtUiState get() = _detailDebtUiState.asStateFlow()

  fun deleteHutang(hutang: HutangModel) {
    viewModelScope.launch {
      deleteHutangUseCase(hutang).collect { res ->
        when(res) {
          is Result.Success -> {
            _detailDebtUiState.update {
              it.copy(
                isSuccessfulDeleteDetail = true
              )
            }
          }
          is Result.Error -> {
            _detailDebtUiState.update {
              it.copy(
                isSuccessfulDeleteDetail = false
              )
            }
          }
        }
        _detailDebtUiState.update {
          it.copy(
            isSuccessfulDeleteDetail = null
          )
        }
      }
    }
  }

  suspend fun setAlarmDebt(calendar: Calendar, model: HutangModel) = withContext(ioDispatcher) {
      return@withContext setAlarmDebtUseCase.invoke(calendar, model)
  }

  fun cancelAlarmDebt(model: HutangModel) {
    viewModelScope.launch {
      cancelAlarmDebtUseCase.invoke(model)
    }
  }

  fun getSizeListPembayaranHutang(id: String) {
    viewModelScope.launch {
      getSizeListPembayaranHutangByIdUseCase.invoke(id).collect { res ->
        _detailDebtUiState.update {
          it.copy(
            sizeDebtRecord = res ?: 0
          )
        }
      }
    }
  }

  fun getAllAccount() {
    viewModelScope.launch {
      _detailDebtUiState.update {
        it.copy(
          accounts = findAllAkunUseCase.invoke()
        )
      }
    }
  }

  suspend fun getHutangById(id: String): HutangModel = withContext(ioDispatcher) {
    return@withContext findHutangByIdUseCase.invoke(id)
  }

  fun getHutangByIdWithFlow(id: String) {
    viewModelScope.launch {
      findHutangByIdWithFlowUseCase.invoke(id).collect { res ->
        when(res) {
          is Result.Success -> {
            _detailDebtUiState.update {
              it.copy(
                detailDebt = res.data
              )
            }
          }
          is Result.Error -> {
            _detailDebtUiState.update {
              it.copy(
                detailDebt = null
              )
            }
          }
        }
      }
    }
  }

  fun savePembayaranHutang(pembayaranHutangModel: PembayaranHutangModel) {
    viewModelScope.launch {
      savePembayaranHutangUseCase.invoke(pembayaranHutangModel).collect { res ->
        when(res) {
         is Result.Success -> {
           _detailDebtUiState.update {
             it.copy(
               isSuccessfulCreate = true
             )
           }
         }
         is Result.Error -> {
           _detailDebtUiState.update {
             it.copy(
               isSuccessfulCreate = false
             )
           }
         }
        }
        _detailDebtUiState.update {
          it.copy(
            isSuccessfulCreate = null
          )
        }
      }
    }
  }


  fun deleteRecordPembayaranHutang(detailPembayaranHutangModel: DetailPembayaranHutangModel) {
    viewModelScope.launch {
      deleteRecordPembayaranHutangUseCase(detailPembayaranHutangModel).collect { res ->
        when(res) {
          is Result.Success -> {
            _detailDebtUiState.update {
              it.copy(
                isSuccessfulDeleteRecord = true
              )
            }
          }
          is Result.Error -> {
            _detailDebtUiState.update {
              it.copy(
                isSuccessfulDeleteRecord = false
              )
            }
          }
        }

        _detailDebtUiState.update {
          it.copy(
            isSuccessfulDeleteRecord = null
          )
        }
      }
    }
  }


  fun updatePembayaranHutang(newModel: PembayaranHutangModel, oldModel: PembayaranHutangModel) {
    viewModelScope.launch {
      updatePembayaranHutangUseCase(newModel, oldModel).collect { res ->
        when(res) {
          is Result.Success -> {
            _detailDebtUiState.update {
              it.copy(
                isSuccessfulUpdate = true
              )
            }
          }
          is Result.Error -> {
            _detailDebtUiState.update {
              it.copy(
                isSuccessfulUpdate = false
              )
            }
          }
        }
      }

      _detailDebtUiState.update {
        it.copy(
          isSuccessfulUpdate = null
        )
      }
    }
  }


  fun getAllDebtRecord(id: String) {
    viewModelScope.launch {
      findAllRecordPembayaranHutangUseCase(id).collect { res ->
        when(res) {
          is Result.Success -> {
            _detailDebtUiState.update {
              it.copy(
                paidDebtRecord = res.data ?: mutableListOf()
              )
            }
          }
          is Result.Error -> {
            _detailDebtUiState.update {
              it.copy(
                paidDebtRecord = mutableListOf()
              )
            }
          }
        }
      }
    }
  }
}