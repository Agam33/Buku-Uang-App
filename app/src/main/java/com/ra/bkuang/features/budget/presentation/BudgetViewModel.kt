package com.ra.bkuang.features.budget.presentation

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.category.domain.usecase.FindCategoryByTypeUseCase
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.features.budget.domain.usecase.CreateBudgetUseCase
import com.ra.bkuang.features.budget.domain.usecase.DeleteBudgetByIdUseCase
import com.ra.bkuang.features.budget.domain.usecase.EditBudgetUseCase
import com.ra.bkuang.features.budget.domain.usecase.FindAllBudgetByDateUseCase
import com.ra.bkuang.features.budget.domain.usecase.FindBudgetByIdUseCase
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
  private val findBudgetByIdUseCase: FindBudgetByIdUseCase,
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher
): BaseViewModel() {

  private var _listCategoryByType = MutableStateFlow<List<KategoriModel>>(listOf())
  val listCategoryByType: StateFlow<List<KategoriModel>> get() = _listCategoryByType

  private var _budgetModel = MutableStateFlow<BudgetModel?>(null)
  val budgetModel: StateFlow<BudgetModel?> get() = _budgetModel

  fun findBudgetById(id: UUID) {
    viewModelScope.launch {
      val budget = findBudgetByIdUseCase.invoke(id)
      _budgetModel.emit(budget)
    }
  }

  suspend fun findAllBudget(fromDate: LocalDate, toDate: LocalDate) = withContext(ioDispatcher) {
    return@withContext findAllBudgetByDateUseCase.invoke(fromDate, toDate)
  }

  fun setCategoryByType(transactionType: TransactionType) = viewModelScope.launch {
    findKategoriByType.invoke(transactionType).collect {
      when (it) {
        is ResultState.Empty -> {}
        is ResultState.Success -> {
          _listCategoryByType.emit(it.data ?: mutableListOf())
        }
        else -> {}
      }
    }
  }

  fun deleteBudgetById(id: UUID): Flow<ResourceState> =
    deleteBudgetByIdUseCase.invoke(id)

  fun updateBudget(budgetModel: BudgetModel): Flow<ResourceState> =
    editBudgetUseCase.invoke(budgetModel)

  fun createBudget(budgetModel: BudgetModel): Flow<ResourceState> =
    createBudgetUseCase.invoke(
      budgetModel.bulanTahun,
      budgetModel.bulanTahun,
      budgetModel
    )
}