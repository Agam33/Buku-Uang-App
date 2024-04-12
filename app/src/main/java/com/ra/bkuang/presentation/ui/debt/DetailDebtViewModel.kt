package com.ra.bkuang.presentation.ui.debt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ra.bkuang.di.IoDispatcherQualifier
import com.ra.bkuang.domain.model.AkunModel
import com.ra.bkuang.domain.model.DetailPembayaranHutangModel
import com.ra.bkuang.domain.model.HutangModel
import com.ra.bkuang.domain.model.PembayaranHutangModel
import com.ra.bkuang.domain.usecase.akun.FindAllAkun
import com.ra.bkuang.domain.usecase.hutang.CancelAlarmDebt
import com.ra.bkuang.domain.usecase.hutang.DeleteHutang
import com.ra.bkuang.domain.usecase.hutang.DeleteRecordPembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.FindAllRecordPembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.FindHutangById
import com.ra.bkuang.domain.usecase.hutang.FindHutangByIdWithFlow
import com.ra.bkuang.domain.usecase.hutang.GetSizeListPembayaranHutangById
import com.ra.bkuang.domain.usecase.hutang.SavePembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.SetAlarmDebt
import com.ra.bkuang.domain.usecase.hutang.UpdateHutang
import com.ra.bkuang.domain.usecase.hutang.UpdatePembayaranHutang
import com.ra.bkuang.presentation.base.BaseViewModel
import com.ra.bkuang.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID
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