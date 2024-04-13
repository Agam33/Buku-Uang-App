package com.ra.bkuang.features.debt.di

import com.ra.bkuang.features.debt.domain.usecase.CancelAlarmDebt
import com.ra.bkuang.features.debt.domain.usecase.CreateHutang
import com.ra.bkuang.features.debt.domain.usecase.CreatePembayaranHutang
import com.ra.bkuang.features.debt.domain.usecase.DeleteHutang
import com.ra.bkuang.features.debt.domain.usecase.DeleteRecordPembayaranHutang
import com.ra.bkuang.features.debt.domain.usecase.FindAllRecordPembayaranHutang
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByAlarmId
import com.ra.bkuang.features.debt.domain.usecase.FindHutangById
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdWithFlow
import com.ra.bkuang.features.debt.domain.usecase.GetSizeListPembayaranHutangById
import com.ra.bkuang.features.debt.domain.usecase.SavePembayaranHutang
import com.ra.bkuang.features.debt.domain.usecase.SetAlarmDebt
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutang
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutang
import com.ra.bkuang.features.debt.domain.usecase.UpdatePembayaranHutang
import com.ra.bkuang.features.debt.domain.usecase.impl.CancelAlarmDebtImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.CreateHutangImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.CreatePembayaranHutangImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.DeleteHutangImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.DeleteRecordPembayaranHutangImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.FindAllRecordPembayaranHutangImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.FindHutangByAlarmIdImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.FindHutangByIdImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.FindHutangByIdWithFlowImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.GetSizeListPembayaranHutangByIdImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.SavePembayaranHutangImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.SetAlarmDebtImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.ShowAllHutangImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.UpdateHutangImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.UpdatePembayaranHutangImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DebtUseCaseModule {

  @Binds
  @Singleton
  fun bindFindHutangByAlarmId(findHutangByAlarmIdImpl: FindHutangByAlarmIdImpl): FindHutangByAlarmId

  @Binds
  @Singleton
  fun bindCancelAlarmDebt(cancelAlarmDebtImpl: CancelAlarmDebtImpl): CancelAlarmDebt

  @Binds
  @Singleton
  fun bindSetAlarmDebt(setAlarmDebt: SetAlarmDebtImpl): SetAlarmDebt

  @Binds
  @Singleton
  fun bindFindAllRecordPembayaranHutang(findAllRecordPembayaranHutang: FindAllRecordPembayaranHutangImpl): FindAllRecordPembayaranHutang

  @Binds
  @Singleton
  fun bindSavePembayaranHutang(savePembayaranHutangImpl: SavePembayaranHutangImpl): SavePembayaranHutang

  @Binds
  @Singleton
  fun bindFindHutangByIdWithFlow(findHutangByIdWithFlowImpl: FindHutangByIdWithFlowImpl): FindHutangByIdWithFlow


  @Binds
  @Singleton
  fun bindGetSizePembayaranHutangById(getSizeListPembayaranHutangByIdImpl: GetSizeListPembayaranHutangByIdImpl): GetSizeListPembayaranHutangById

  @Binds
  @Singleton
  fun bindCreateHutang(createHutangImpl: CreateHutangImpl): CreateHutang

  @Binds
  @Singleton
  fun bindDeleteHutang(deleteHutangImpl: DeleteHutangImpl): DeleteHutang

  @Binds
  @Singleton
  fun bindShowAllHutang(showAllHutangImpl: ShowAllHutangImpl): ShowAllHutang

  @Binds
  @Singleton
  fun bindUpdateHutang(updateHutangImpl: UpdateHutangImpl): UpdateHutang

  @Binds
  @Singleton
  fun bindCreatePembayaranHutang(createPembayaranHutangImpl: CreatePembayaranHutangImpl): CreatePembayaranHutang


  @Binds
  @Singleton
  fun bindFindHutangById(findHutangByIdImpl: FindHutangByIdImpl): FindHutangById

  @Binds
  @Singleton
  fun bindDeleteRecordPembayaranHutang(deleteRecordPembayaranHutangImpl: DeleteRecordPembayaranHutangImpl): DeleteRecordPembayaranHutang

  @Binds
  @Singleton
  fun bindUpdatePembayaranHutang(updatePembayaranHutangImpl: UpdatePembayaranHutangImpl): UpdatePembayaranHutang
}
