package com.ra.bkuang.features.transaction.presentation.tab.transfer.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByDateUseCase
import com.ra.bkuang.features.transaction.presentation.tab.transfer.uistate.TransferFragmentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TransferFragmentViewModel @Inject constructor(
  private val getTransferByDateUseCase: GetTransferByDateUseCase,
  private val deleteTransferByIdUseCase: DeleteTransferByIdUseCase,
): BaseViewModel() {

  private var _uiState = MutableStateFlow(TransferFragmentUiState())
  val uiState = _uiState.asStateFlow()

  fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTransferByDateUseCase.invoke(fromDate, toDate).collect { res ->
        when (res) {
          is Result.Error -> {
            _uiState.update {
              it.copy(
                transferList = emptyList()
              )
            }
          }

          is Result.Success -> {
            _uiState.update {
              it.copy(
                transferList = res.data ?: mutableListOf()
              )
            }
          }
        }
      }
    }
  }
   fun deleteTransferById(uuid: UUID) {
     viewModelScope.launch {
       deleteTransferByIdUseCase.invoke(uuid).collect { res ->
         when(res) {
           is Result.Success -> {
             _uiState.update {
               it.copy(
                 isSuccessful = res.data
               )
             }
           }
           is Result.Error -> {
             _uiState.update {
               it.copy(
                 isSuccessful = false
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