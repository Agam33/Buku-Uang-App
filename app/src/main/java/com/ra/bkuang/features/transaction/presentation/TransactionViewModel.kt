package com.ra.bkuang.features.transaction.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.category.domain.model.KategoriModel
import com.ra.bkuang.features.transaction.domain.model.PendapatanModel
import com.ra.bkuang.features.transaction.domain.model.PengeluaranModel
import com.ra.bkuang.features.transaction.domain.model.TransferModel
import com.ra.bkuang.features.account.domain.usecase.FindAkunByIdUseCase
import com.ra.bkuang.features.account.domain.usecase.FindAllAkunUseCase
import com.ra.bkuang.features.category.domain.usecase.FindCategoryByTypeUseCase
import com.ra.bkuang.features.transaction.domain.usecase.GetTotalTransactionByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.DeletePendapatanByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetListDetailPendapatanByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetPendapatanByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.GetTotalPendapatanByDateWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.SavePendapatanUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pendapatan.UpdatePendapatanUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.DeletePengeluaranByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetListDetailPengeluaranByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetPengeluaranByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.GetTotalPengeluaranByDateWithFlowUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.SavePengeluaranUseCase
import com.ra.bkuang.features.transaction.domain.usecase.pengeluaran.UpdatePengeluaranUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.DeleteTransferByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByDateUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.GetTransferByIdUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.SaveTransferUseCase
import com.ra.bkuang.features.transaction.domain.usecase.transfer.UpdateTransferUseCase
import com.ra.bkuang.common.util.ResultState
import com.ra.bkuang.core.preferences.UserSettingPref
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.features.transaction.domain.model.TransactionDetail
import com.ra.bkuang.common.util.Extension.toFormatRupiah
import com.ra.bkuang.features.transaction.data.entity.DetailPendapatan
import com.ra.bkuang.features.transaction.data.entity.DetailPengeluaran
import com.ra.bkuang.features.transaction.data.entity.DetailTransfer
import com.ra.bkuang.features.transaction.data.entity.TransactionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
  private val saveTransferUseCase: SaveTransferUseCase,
  private val savePengeluaranUseCase: SavePengeluaranUseCase,
  private val savePendapatanUseCase: SavePendapatanUseCase,
  private val findAllAkunUseCase: FindAllAkunUseCase,
  private val findKategoriByType: FindCategoryByTypeUseCase,
  private val getListDetailPengeluaranByDateUseCase: GetListDetailPengeluaranByDateUseCase,
  private val getListDetailPendapatanByDateUseCase: GetListDetailPendapatanByDateUseCase,
  private val getTransferByDateUseCase: GetTransferByDateUseCase,
  private val userSettingPref: UserSettingPref,
  private val getTotalPendapatanByDateWithFlowUseCase: GetTotalPendapatanByDateWithFlowUseCase,
  private val getTotalPengeluaranByDateWithFlowUseCase: GetTotalPengeluaranByDateWithFlowUseCase,
  private val getTotalTransactionByDateUseCase: GetTotalTransactionByDateUseCase,
  private val deletePengeluaranByIdUseCase: DeletePengeluaranByIdUseCase,
  private val deleteTransferByIdUseCase: DeleteTransferByIdUseCase,
  private val deletePendapatanByIdUseCase: DeletePendapatanByIdUseCase,
  private val getPendapatanByIdUseCase: GetPendapatanByIdUseCase,
  private val getPengeluaranByIdUseCase: GetPengeluaranByIdUseCase,
  private val getTransferByIdUseCase: GetTransferByIdUseCase,
  private val updateTransferUseCase: UpdateTransferUseCase,
  private val updatePendapatanUseCase: UpdatePendapatanUseCase,
  private val updatePengeluaranUseCase: UpdatePengeluaranUseCase,
  private val findAkunByIdUseCase: FindAkunByIdUseCase
): BaseViewModel() {

  private var _saveTransactionState = MutableLiveData<Boolean>()
  val saveTransactionState: LiveData<Boolean> = _saveTransactionState

  private var _pendapatanModel = MutableLiveData<PendapatanModel>()
  val pendapatanModel: LiveData<PendapatanModel> = _pendapatanModel

  private var _pengeluaranModel = MutableLiveData<PengeluaranModel>()
  val pengeluaranModel: LiveData<PengeluaranModel> = _pengeluaranModel

  private var _transferModel = MutableLiveData<TransferModel>()
  val transferModel: LiveData<TransferModel> = _transferModel

  private var _listAccount = MutableLiveData<List<AkunModel>>()
  val listAccount: LiveData<List<AkunModel>> get() = _listAccount

  private var _listCategoryByType = MutableLiveData<List<KategoriModel>>()
  val listCategoryByType: LiveData<List<KategoriModel>> get() = _listCategoryByType

  private var _listPengeluaran = MutableLiveData<ResultState<List<DetailPengeluaran>>>()
  val listPengeluaran: LiveData<ResultState<List<DetailPengeluaran>>> get() = _listPengeluaran

  private val _rvIncomeState = MutableLiveData<Boolean>()
  val rvIncomeState: LiveData<Boolean> get() = _rvIncomeState

  private val _emptyIncomeLayoutState = MutableLiveData<Boolean>()
  val emptyIncomeLayoutState: LiveData<Boolean> get() = _emptyIncomeLayoutState

  private val _emptyTransferLayoutState = MutableLiveData<Boolean>()
  val emptyTransferLayoutState: LiveData<Boolean> get() = _emptyTransferLayoutState

  private val _rvTransferState = MutableLiveData<Boolean>()
  val rvTransferState: LiveData<Boolean> get() = _rvTransferState

  private var _listTransfer = MutableLiveData<ResultState<List<DetailTransfer>>>()
  val listTransfer: LiveData<ResultState<List<DetailTransfer>>> get() = _listTransfer

  private val _rvExpenseState = MutableLiveData<Boolean>()
  val rvExpenseState: LiveData<Boolean> get() = _rvExpenseState

  private val _emptyExpenseLayoutState = MutableLiveData<Boolean>()
  val emptyExpenseLayoutState: LiveData<Boolean> get() = _emptyExpenseLayoutState

  private val _textPengeluaran = MutableSharedFlow<String>()
  val textPengeluaran: SharedFlow<String> = _textPengeluaran.asSharedFlow()

  private val _textPendapatan = MutableSharedFlow<String>()
  val textPendapatan: SharedFlow<String> = _textPendapatan.asSharedFlow()

  private val _textTotal = MutableSharedFlow<String>()
  val textTotal: SharedFlow<String> = _textTotal.asSharedFlow()

  private var _currentDate = MutableLiveData<Pair<LocalDateTime, LocalDateTime>>()
  val currentDate: LiveData<Pair<LocalDateTime, LocalDateTime>> get() = _currentDate

  private var _incomes = MutableLiveData<ResultState<List<DetailPendapatan>>>()
  val incomes: LiveData<ResultState<List<DetailPendapatan>>> = _incomes

  private var _detailTransaction = MutableLiveData<TransactionDetail>()
  val detailTransaction: LiveData<TransactionDetail> = _detailTransaction

  private val _saveTransactionDialogStateUi = MutableLiveData<Boolean>()
  val saveTransactionDialogStateUi: LiveData<Boolean> = _saveTransactionDialogStateUi

  private val _updateTransactionState = MutableLiveData<Boolean>()
  val updateTransactionState: LiveData<Boolean> = _updateTransactionState

  fun setSaveTransactionState(state: Boolean) {
    _saveTransactionState.postValue(state)
  }

  fun setUpdateTransctionState(state: Boolean) {
    _updateTransactionState.postValue(state)
  }

  fun setSaveTransactionDialogStateUi(state: Boolean) {
    _saveTransactionDialogStateUi.postValue(state)
  }

  fun checkAccountMoney(idAkun: UUID, amount: Int, action: suspend () -> Unit = {}) {
    viewModelScope.launch {
      val account = findAkunByIdUseCase.invoke(idAkun)
      if(account.total - amount >= 0) {
        action()
        _saveTransactionDialogStateUi.postValue(false)
      } else {
        _saveTransactionDialogStateUi.postValue(true)
      }
    }
  }

  fun getPendapatanById(uuid: UUID) {
    viewModelScope.launch {
      val data = getPendapatanByIdUseCase.invoke(uuid)
      _pendapatanModel.postValue(data)
    }
  }

  fun getPengeluaranById(uuid: UUID) {
    viewModelScope.launch {
      val data = getPengeluaranByIdUseCase.invoke(uuid)
      _pengeluaranModel.postValue(data)
    }
  }

  fun getTransferById(uuid: UUID) {
    viewModelScope.launch {
      val data = getTransferByIdUseCase.invoke(uuid)
      _transferModel.postValue(data)
    }
  }

  fun setDetailTransaction(detail: TransactionDetail) {
    _detailTransaction.postValue(detail)
  }

  fun setCurrentDate(localDate: Pair<LocalDateTime, LocalDateTime>) {
    _currentDate.postValue(localDate)
  }

  fun getDateViewType(): LiveData<String> =
    userSettingPref.getDateViewType().asLiveData()

  fun setStateIncomeListUi(rvState: Boolean, emptyState: Boolean) {
    _rvIncomeState.postValue(rvState)
    _emptyIncomeLayoutState.postValue(emptyState)
  }

  fun setStateExpenseListUi(rvState: Boolean, emptyState: Boolean) {
    _rvExpenseState.postValue(rvState)
    _emptyExpenseLayoutState.postValue(emptyState)
  }

  fun setStateTransferListUi(rvState: Boolean, emptyState: Boolean) {
    _rvTransferState.postValue(rvState)
    _emptyTransferLayoutState.postValue(emptyState)
  }

  fun getTotalPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalPendapatanByDateWithFlowUseCase.invoke(fromDate, toDate)
        .onEach { _textPendapatan.emit(it.toFormatRupiah()) }
        .collect()
    }
  }

  fun getTotalPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalPengeluaranByDateWithFlowUseCase.invoke(fromDate, toDate)
        .onEach {  _textPengeluaran.emit(it.toFormatRupiah()) }
        .collect()
    }
  }

  fun getTotalByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      getTotalTransactionByDateUseCase.invoke(fromDate, toDate)
        .onEach {  _textTotal.emit(it.toFormatRupiah()) }
        .collect()
    }
  }

  fun getPengeluaranByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      val list = getListDetailPengeluaranByDateUseCase.invoke(fromDate, toDate)
      if(list.isEmpty()) _listPengeluaran.postValue(ResultState.Empty)
      else _listPengeluaran.postValue(ResultState.Success(list))
    }
  }

  fun getPendapatanByDate(fromDate: LocalDateTime, toDate: LocalDateTime)  {
    viewModelScope.launch {
      val list = getListDetailPendapatanByDateUseCase.invoke(fromDate, toDate)
      if(list.isEmpty()) _incomes.postValue(ResultState.Empty)
      else _incomes.postValue(ResultState.Success(list))
    }
  }

  fun getTransferByDate(fromDate: LocalDateTime, toDate: LocalDateTime) {
    viewModelScope.launch {
      val list = getTransferByDateUseCase.invoke(fromDate, toDate)
      _listTransfer.postValue(ResultState.Loading)
      if(list.isEmpty()) _listTransfer.postValue(ResultState.Empty)
      else _listTransfer.postValue(ResultState.Success(list))
    }
  }

  fun getAllAccount() = viewModelScope.launch {
    _listAccount.postValue(findAllAkunUseCase.invoke())
  }

  fun setCategoryByType(tipeKategori: TransactionType) = viewModelScope.launch {
    findKategoriByType.invoke(tipeKategori).collect {
      when (it) {
        is ResultState.Empty -> {}
        is ResultState.Success -> {
          _listCategoryByType.postValue(it.data ?: mutableListOf())
        }
        else -> {}
      }
    }
  }

  suspend fun savePengeluaran(pengeluaranModel: PengeluaranModel) =
    savePengeluaranUseCase.invoke(pengeluaranModel)


  suspend fun savePendapatan(pendapatanModel: PendapatanModel) =
    savePendapatanUseCase.invoke(pendapatanModel)


  suspend fun saveTransfer(transferModel: TransferModel) =
    saveTransferUseCase.invoke(transferModel)


  suspend fun deletePendapatanById(uuid: UUID) =
    deletePendapatanByIdUseCase.invoke(uuid)


  suspend fun deletePengeluaranById(uuid: UUID) =
    deletePengeluaranByIdUseCase.invoke(uuid)


  suspend fun deleteTransferById(uuid: UUID) =
    deleteTransferByIdUseCase.invoke(uuid)


  suspend fun updatePendapatan(newPendapatanModel: PendapatanModel, oldPendapatanModel: PendapatanModel) =
    updatePendapatanUseCase.invoke(newPendapatanModel, oldPendapatanModel)


  suspend fun updatePengeluaran(newPengeluaranModel: PengeluaranModel, oldPengeluaranModel: PengeluaranModel) =
    updatePengeluaranUseCase.invoke(newPengeluaranModel,oldPengeluaranModel)

  suspend fun updateTransfer(newTransferModel: TransferModel, oldTransferModel: TransferModel)=
    updateTransferUseCase.invoke(newTransferModel, oldTransferModel)
}