package com.ra.bkuang.features.category.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Result
import com.ra.bkuang.features.category.data.model.KategoriModel
import com.ra.bkuang.features.category.domain.usecase.DeleteKategoriUseCase
import com.ra.bkuang.features.category.domain.usecase.FindCategoryWithFlowUseCase
import com.ra.bkuang.features.category.domain.usecase.SaveKategoriUseCase
import com.ra.bkuang.features.category.domain.usecase.UpdateKategoriUseCase
import com.ra.bkuang.features.category.presentation.CategoryUiState
import com.ra.bkuang.core.data.source.local.database.entity.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
  private val deleteKategoriUseCase: DeleteKategoriUseCase,
  private val updateKategoriUseCase: UpdateKategoriUseCase,
  private val findCategoryWithFlowUseCase: FindCategoryWithFlowUseCase,
  private val saveKategoriUseCase: SaveKategoriUseCase,
): BaseViewModel<CategoryUiState>(CategoryUiState()) {

  fun setCurrentCategory(transactionType: TransactionType) = viewModelScope.launch {
    _uiState.update {
      it.copy(
        currCategory = transactionType
      )
    }
  }

  fun setCategories() {
    viewModelScope.launch {
      findCategoryWithFlowUseCase().collect { data ->
        _uiState.update {
          it.copy(
            mapCategory = data
          )
        }
      }
    }
  }

  fun deleteCategory(kategoriModel: KategoriModel) {
    viewModelScope.launch {
      deleteKategoriUseCase.invoke(kategoriModel).collect { res ->
        when(res) {
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessfulDelete = false
              )
            }
          }
          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessfulDelete = true
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

  fun updateCategory(kategoriModel: KategoriModel) {
    viewModelScope.launch {
      updateKategoriUseCase.invoke(kategoriModel).collect { res ->
        when (res) {
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessfulUpdate = false
              )
            }
          }

          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessfulUpdate = true
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            isSuccessfulUpdate = null
          )
        }
      }
    }
  }

  fun saveCategory(kategoriModel: KategoriModel) {
    viewModelScope.launch {
      saveKategoriUseCase.invoke(kategoriModel).collect { res ->
        when (res) {
          is Result.Error -> {
            _uiState.update {
              it.copy(
                isSuccessfulSave = false
              )
            }
          }

          is Result.Success -> {
            _uiState.update {
              it.copy(
                isSuccessfulSave = true
              )
            }
          }
        }

        _uiState.update {
          it.copy(
            isSuccessfulSave = null
          )
        }
      }
    }
  }
}