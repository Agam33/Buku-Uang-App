package com.ra.bkuang.features.budget.presentation

import androidx.lifecycle.viewModelScope
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Resource
import com.ra.bkuang.common.util.ResourceState
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.usecase.FindCategoryByType
import com.ra.bkuang.features.budget.domain.model.BudgetModel
import com.ra.bkuang.features.budget.domain.usecase.CreateBudget
import com.ra.bkuang.features.budget.domain.usecase.DeleteBudgetById
import com.ra.bkuang.features.budget.domain.usecase.EditBudget
import com.ra.bkuang.features.budget.domain.usecase.FindAllBudgetByDate
import com.ra.bkuang.features.budget.domain.usecase.FindBudgetById
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
  private val findAllBudgetByDate: FindAllBudgetByDate,
  private val editBudget: EditBudget,
  private val deleteBudgetById: DeleteBudgetById,
  private val createBudget: CreateBudget,
  private val findKategoriByType: FindCategoryByType,
  private val findBudgetById: FindBudgetById,
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher
): BaseViewModel() {

  private var _listCategoryByType = MutableStateFlow<List<KategoriModel>>(listOf())
  val listCategoryByType: StateFlow<List<KategoriModel>> get() = _listCategoryByType

  private var _budgetModel = MutableStateFlow<BudgetModel?>(null)
  val budgetModel: StateFlow<BudgetModel?> get() = _budgetModel

  fun findBudgetById(id: UUID) {
    viewModelScope.launch {
      val budget = findBudgetById.invoke(id)
      _budgetModel.emit(budget)
    }
  }

  suspend fun findAllBudget(fromDate: LocalDate, toDate: LocalDate) = withContext(ioDispatcher) {
    return@withContext findAllBudgetByDate.invoke(fromDate, toDate)
  }

  fun setCategoryByType(transactionType: TransactionType) = viewModelScope.launch {
    findKategoriByType.invoke(transactionType).collect {
      when (it) {
        is Resource.Empty -> {}
        is Resource.Success -> {
          _listCategoryByType.emit(it.data ?: mutableListOf())
        }
        else -> {}
      }
    }
  }

  fun deleteBudgetById(id: UUID): Flow<ResourceState> =
    deleteBudgetById.invoke(id)

  fun updateBudget(budgetModel: BudgetModel): Flow<ResourceState> =
    editBudget.invoke(budgetModel)

  fun createBudget(budgetModel: BudgetModel): Flow<ResourceState> =
    createBudget.invoke(
      budgetModel.bulanTahun,
      budgetModel.bulanTahun,
      budgetModel
    )
}