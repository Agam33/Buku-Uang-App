package com.ra.bkuang.features.debt.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.features.account.domain.model.AkunModel
import com.ra.bkuang.features.debt.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.features.debt.domain.model.HutangModel
import com.ra.bkuang.features.debt.domain.model.PembayaranHutangModel
import com.ra.bkuang.features.account.domain.usecase.FindAllAkun
import com.ra.bkuang.features.debt.domain.usecase.CancelAlarmDebt
import com.ra.bkuang.features.debt.domain.usecase.DeleteHutang
import com.ra.bkuang.features.debt.domain.usecase.DeleteRecordPembayaranHutang
import com.ra.bkuang.features.debt.domain.usecase.FindAllRecordPembayaranHutang
import com.ra.bkuang.features.debt.domain.usecase.FindHutangById
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdWithFlow
import com.ra.bkuang.features.debt.domain.usecase.GetSizeListPembayaranHutangById
import com.ra.bkuang.features.debt.domain.usecase.SavePembayaranHutang
import com.ra.bkuang.features.debt.domain.usecase.SetAlarmDebt
import com.ra.bkuang.features.debt.domain.usecase.UpdatePembayaranHutang
import com.ra.bkuang.common.base.BaseViewModel
import com.ra.bkuang.common.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DetailDebtViewModel @Inject constructor(
  @IoDispatcherQualifier private val ioDispatcher: CoroutineDispatcher,
  private val findAllAkun: FindAllAkun,
  private val findAllRecordPembayaranHutang: FindAllRecordPembayaranHutang,
  private val savePembayaranHutang: SavePembayaranHutang,
  private val findHutangByIdWithFlow: FindHutangByIdWithFlow,
  private val findHutangById: FindHutangById,
  private val deleteRecordPembayaranHutang: DeleteRecordPembayaranHutang,
  private val updatePembayaranHutang: UpdatePembayaranHutang,
  private val getSizeListPembayaranHutangById: GetSizeListPembayaranHutangById,
  private val deleteHutang: DeleteHutang,
  private val cancelAlarmDebt: CancelAlarmDebt,
  private val setAlarmDebt: SetAlarmDebt,
): BaseViewModel() {

  private var _rvDebtRecordState = MutableLiveData<Boolean>()
  val rvDebtRecordState: LiveData<Boolean> = _rvDebtRecordState

  private var _emptyListState = MutableLiveData<Boolean>()
  val emptyListState: LiveData<Boolean> = _emptyListState

  private var _accounts = MutableLiveData<List<AkunModel>>()
  val accounts: LiveData<List<AkunModel>> = _accounts

  private var _debtRecord = MutableLiveData<Resource<List<DetailPembayaranHutangModel>>>()
  val debtRecord: LiveData<Resource<List<DetailPembayaranHutangModel>>> = _debtRecord

  private var _debtModel = MutableLiveData<HutangModel>()
  val debtModel: LiveData<HutangModel> get() = _debtModel

  private var _sizeListPembayaranHutang = MutableLiveData<String>()
  val sizeListPembayaranHutang: LiveData<String> = _sizeListPembayaranHutang

  private var _detailDebt: MutableStateFlow<HutangModel?> = MutableStateFlow(null)
  val detailDebt get() = _detailDebt.asStateFlow()

  suspend fun deleteHutang(hutang: HutangModel) = withContext(ioDispatcher) {
    return@withContext deleteHutang.invoke(hutang)
  }

  suspend fun setAlarmDebt(calendar: Calendar, model: HutangModel) = withContext(ioDispatcher) {
      return@withContext setAlarmDebt.invoke(calendar, model)
  }

  fun cancelAlarmDebt(model: HutangModel) {
    viewModelScope.launch {
      cancelAlarmDebt.invoke(model)
    }
  }

  fun setState(rvState: Boolean, emptyState: Boolean) {
    _rvDebtRecordState.postValue(rvState)
    _emptyListState.postValue(emptyState)
  }

  fun getSizeListPembayaranHutang(id: String) {
    viewModelScope.launch {
      getSizeListPembayaranHutangById.invoke(id).collect {
        _sizeListPembayaranHutang.postValue("${it ?: 0}")
      }
    }
  }

  fun getAllAccount() {
    viewModelScope.launch {
      _accounts.postValue(findAllAkun.invoke())
    }
  }

  suspend fun getHutangById(id: String): HutangModel = withContext(ioDispatcher) {
    return@withContext findHutangById.invoke(id)
  }

  fun getHutangByIdWithFlow(id: String) = findHutangByIdWithFlow.invoke(id)

  suspend fun savePembayaranHutang(pembayaranHutangModel: PembayaranHutangModel) =
    savePembayaranHutang.invoke(pembayaranHutangModel)

  suspend fun deleteRecordPembayaranHutang(detailPembayaranHutangModel: DetailPembayaranHutangModel) =
    deleteRecordPembayaranHutang.invoke(detailPembayaranHutangModel)

  suspend fun updatePembayaranHutang(newModel: PembayaranHutangModel, oldModel: PembayaranHutangModel) =
    updatePembayaranHutang.invoke(newModel, oldModel)

  fun getAllDebtRecord(id: String) {
    viewModelScope.launch {
      _debtRecord.postValue(findAllRecordPembayaranHutang.invoke(id))
    }
  }
}