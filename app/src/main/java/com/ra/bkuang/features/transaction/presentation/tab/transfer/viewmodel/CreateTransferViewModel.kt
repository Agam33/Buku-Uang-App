package com.ra.bkuang.features.transaction.presentation.tab.transfer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.account.domain.usecase.FindAkunByIdUseCase
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunUseCase
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.SaveTransferUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.UpdateTransferUseCase
import com.ra.bkuang.features.transaction.presentation.tab.transfer.uistate.CreateTransferFragmentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateTransferViewModel @Inject constructor(
  private val saveTransferUseCase: SaveTransferUseCase,
  private val updateTransferUseCase: UpdateTransferUseCase,
  private val findAllAkunUseCase: FindAllAkunUseCase,
  private val findAkunByIdUseCase: FindAkunByIdUseCase,
  private val getTransferByIdUseCase: GetTransferByIdUseCase
): BaseViewModel<CreateTransferFragmentUiState>(CreateTransferFragmentUiState()) {

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

  fun getTransferById(uuid: String) {
    viewModelScope.launch {
      val data = getTransferByIdUseCase(uuid)
      _uiState.update {
        it.copy(
          transferModel = data
        )
      }
    }
  }

  fun getAllAccount() {
    viewModelScope.launch {
      val accounts = findAllAkunUseCase.invoke()
      _uiState.update {
        it.copy(
          accountList = accounts
        )
      }
    }
  }

  fun saveTransfer(transferModel: TransferModel) {
    viewModelScope.launch {
      saveTransferUseCase.invoke(transferModel).collect { res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessful = true,
                isSave = false
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessful = false,
                isSave = false
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            isSuccessful = null,
            isSave = false
          )
        }
      }
    }
  }


  fun updateTransfer(newTransferModel: TransferModel, oldTransferModel: TransferModel) {
    viewModelScope.launch {
      updateTransferUseCase.invoke(newTransferModel, oldTransferModel).collect {res ->
        when(res) {
          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessful = true,
                isSave = false
              )
            }
          }
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessful = false,
                isSave = false
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            isSuccessful = null,
            isSave = false
          )
        }
      }
    }
  }
}