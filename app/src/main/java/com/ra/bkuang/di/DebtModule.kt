package com.ra.bkuang.di

import com.ra.bkuang.alarm.DebtAlarm
import com.ra.bkuang.alarm.DebtAlarmManagerImpl
import com.ra.bkuang.data.local.datasource.HutangLocalDataSource
import com.ra.bkuang.data.local.datasource.impl.HutangLocalDataSourceImpl
import com.ra.bkuang.data.repository.PembayaranHutangRepositoryImpl
import com.ra.bkuang.domain.repository.PembayaranHutangRepository
import com.ra.bkuang.domain.usecase.hutang.CancelAlarmDebt
import com.ra.bkuang.domain.usecase.hutang.CreateHutang
import com.ra.bkuang.domain.usecase.hutang.CreatePembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.DeleteHutang
import com.ra.bkuang.domain.usecase.hutang.DeleteRecordPembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.FindAllRecordPembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.FindHutangByAlarmId
import com.ra.bkuang.domain.usecase.hutang.FindHutangById
import com.ra.bkuang.domain.usecase.hutang.FindHutangByIdWithFlow
import com.ra.bkuang.domain.usecase.hutang.GetSizeListPembayaranHutangById
import com.ra.bkuang.domain.usecase.hutang.SavePembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.SetAlarmDebt
import com.ra.bkuang.domain.usecase.hutang.ShowAllHutang
import com.ra.bkuang.domain.usecase.hutang.UpdateHutang
import com.ra.bkuang.domain.usecase.hutang.UpdatePembayaranHutang
import com.ra.bkuang.domain.usecase.hutang.impl.CancelAlarmDebtImpl
import com.ra.bkuang.domain.usecase.hutang.impl.CreateHutangImpl
import com.ra.bkuang.domain.usecase.hutang.impl.CreatePembayaranHutangImpl
import com.ra.bkuang.domain.usecase.hutang.impl.DeleteHutangImpl
import com.ra.bkuang.domain.usecase.hutang.impl.DeleteRecordPembayaranHutangImpl
import com.ra.bkuang.domain.usecase.hutang.impl.FindAllRecordPembayaranHutangImpl
import com.ra.bkuang.domain.usecase.hutang.impl.FindHutangByAlarmIdImpl
import com.ra.bkuang.domain.usecase.hutang.impl.FindHutangByIdImpl
import com.ra.bkuang.domain.usecase.hutang.impl.FindHutangByIdWithFlowImpl
import com.ra.bkuang.domain.usecase.hutang.impl.GetSizeListPembayaranHutangByIdImpl
import com.ra.bkuang.domain.usecase.hutang.impl.SavePembayaranHutangImpl
import com.ra.bkuang.domain.usecase.hutang.impl.SetAlarmDebtImpl
import com.ra.bkuang.domain.usecase.hutang.impl.ShowAllHutangImpl
import com.ra.bkuang.domain.usecase.hutang.impl.UpdateHutangImpl
import com.ra.bkuang.domain.usecase.hutang.impl.UpdatePembayaranHutangImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DebtModule {

  @Binds
  @Singleton
  fun bindDebtAlarmManager(debtAlarmManagerImpl: DebtAlarmManagerImpl): DebtAlarm

  @Binds
  @Singleton
  fun bindHutangRepository(
    pembayaranHutangRepositoryImpl: PembayaranHutangRepositoryImpl
  ): PembayaranHutangRepository

  @Binds
  @Singleton
  fun bindHutangLocalDataSource(
    hutangLocalDataSourceImpl: HutangLocalDataSourceImpl
  ): HutangLocalDataSource
}


@Module
@InstallIn(SingletonComponent::class)
interface UseCaseDebtModule {

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
