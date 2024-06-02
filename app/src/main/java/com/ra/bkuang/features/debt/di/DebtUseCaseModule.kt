package com.ra.bkuang.features.debt.di

import com.ra.bkuang.features.debt.domain.usecase.CancelAlarmDebtUseCase
import com.ra.bkuang.features.debt.domain.usecase.CreateHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.CreatePembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.DeleteHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.DeleteRecordPembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindAllRecordPembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByAlarmIdUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdUseCase
import com.ra.bkuang.features.debt.domain.usecase.FindHutangByIdWithFlowUseCase
import com.ra.bkuang.features.debt.domain.usecase.GetSizeListPembayaranHutangByIdUseCase
import com.ra.bkuang.features.debt.domain.usecase.SavePembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.SetAlarmDebtUseCase
import com.ra.bkuang.features.debt.domain.usecase.ShowAllHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.UpdateHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.UpdatePembayaranHutangUseCase
import com.ra.bkuang.features.debt.domain.usecase.impl.CancelAlarmDebtUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.CreateHutangUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.CreatePembayaranHutangUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.DeleteHutangUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.DeleteRecordPembayaranHutangUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.FindAllRecordPembayaranHutangUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.FindHutangByAlarmIdUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.FindHutangByIdUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.FindHutangByIdWithFlowUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.GetSizeListPembayaranHutangByIdUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.SavePembayaranHutangUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.SetAlarmDebtUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.ShowAllHutangUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.UpdateHutangUseCaseImpl
import com.ra.bkuang.features.debt.domain.usecase.impl.UpdatePembayaranHutangUseCaseImpl
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
  fun bindFindHutangByAlarmId(findHutangByAlarmIdImpl: FindHutangByAlarmIdUseCaseImpl): FindHutangByAlarmIdUseCase

  @Binds
  @Singleton
  fun bindCancelAlarmDebt(cancelAlarmDebtImpl: CancelAlarmDebtUseCaseImpl): CancelAlarmDebtUseCase

  @Binds
  @Singleton
  fun bindSetAlarmDebt(setAlarmDebt: SetAlarmDebtUseCaseImpl): SetAlarmDebtUseCase

  @Binds
  @Singleton
  fun bindFindAllRecordPembayaranHutang(findAllRecordPembayaranHutang: FindAllRecordPembayaranHutangUseCaseImpl): FindAllRecordPembayaranHutangUseCase

  @Binds
  @Singleton
  fun bindSavePembayaranHutang(savePembayaranHutangImpl: SavePembayaranHutangUseCaseImpl): SavePembayaranHutangUseCase

  @Binds
  @Singleton
  fun bindFindHutangByIdWithFlow(findHutangByIdWithFlowImpl: FindHutangByIdWithFlowUseCaseImpl): FindHutangByIdWithFlowUseCase


  @Binds
  @Singleton
  fun bindGetSizePembayaranHutangById(getSizeListPembayaranHutangByIdImpl: GetSizeListPembayaranHutangByIdUseCaseImpl): GetSizeListPembayaranHutangByIdUseCase

  @Binds
  @Singleton
  fun bindCreateHutang(createHutangImpl: CreateHutangUseCaseImpl): CreateHutangUseCase

  @Binds
  @Singleton
  fun bindDeleteHutang(deleteHutangImpl: DeleteHutangUseCaseImpl): DeleteHutangUseCase

  @Binds
  @Singleton
  fun bindShowAllHutang(showAllHutangImpl: ShowAllHutangUseCaseImpl): ShowAllHutangUseCase

  @Binds
  @Singleton
  fun bindUpdateHutang(updateHutangImpl: UpdateHutangUseCaseImpl): UpdateHutangUseCase

  @Binds
  @Singleton
  fun bindCreatePembayaranHutang(createPembayaranHutangImpl: CreatePembayaranHutangUseCaseImpl): CreatePembayaranHutangUseCase


  @Binds
  @Singleton
  fun bindFindHutangById(findHutangByIdImpl: FindHutangByIdUseCaseImpl): FindHutangByIdUseCase

  @Binds
  @Singleton
  fun bindDeleteRecordPembayaranHutang(deleteRecordPembayaranHutangImpl: DeleteRecordPembayaranHutangUseCaseImpl): DeleteRecordPembayaranHutangUseCase

  @Binds
  @Singleton
  fun bindUpdatePembayaranHutang(updatePembayaranHutangImpl: UpdatePembayaranHutangUseCaseImpl): UpdatePembayaranHutangUseCase
}
