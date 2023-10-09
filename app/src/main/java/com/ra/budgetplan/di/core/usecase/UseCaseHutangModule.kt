package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.hutang.CreateHutang
import com.ra.budgetplan.domain.usecase.hutang.CreatePembayaranHutang
import com.ra.budgetplan.domain.usecase.hutang.DeleteHutang
import com.ra.budgetplan.domain.usecase.hutang.DeleteRecordPembayaranHutang
import com.ra.budgetplan.domain.usecase.hutang.FindAllRecordPembayaranHutang
import com.ra.budgetplan.domain.usecase.hutang.FindHutangById
import com.ra.budgetplan.domain.usecase.hutang.FindHutangByIdWithFlow
import com.ra.budgetplan.domain.usecase.hutang.GetSizeListPembayaranHutangById
import com.ra.budgetplan.domain.usecase.hutang.SavePembayaranHutang
import com.ra.budgetplan.domain.usecase.hutang.ShowAllHutang
import com.ra.budgetplan.domain.usecase.hutang.UpdateHutang
import com.ra.budgetplan.domain.usecase.hutang.UpdatePembayaranHutang
import com.ra.budgetplan.domain.usecase.hutang.impl.CreateHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.CreatePembayaranHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.DeleteHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.DeleteRecordPembayaranHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.FindAllRecordPembayaranHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.FindHutangByIdImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.FindHutangByIdWithFlowImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.GetSizeListPembayaranHutangByIdImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.SavePembayaranHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.ShowAllHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.UpdateHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.UpdatePembayaranHutangImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseHutangModule {

  @Binds
  fun bindGetSizePembayaranHutangById(getSizeListPembayaranHutangByIdImpl: GetSizeListPembayaranHutangByIdImpl): GetSizeListPembayaranHutangById

  @Binds
  fun bindCreateHutang(createHutangImpl: CreateHutangImpl): CreateHutang

  @Binds
  fun bindDeleteHutang(deleteHutangImpl: DeleteHutangImpl): DeleteHutang

  @Binds
  fun bindShowAllHutang(showAllHutangImpl: ShowAllHutangImpl): ShowAllHutang

  @Binds
  fun bindUpdateHutang(updateHutangImpl: UpdateHutangImpl): UpdateHutang

  @Binds
  fun bindCreatePembayaranHutang(createPembayaranHutangImpl: CreatePembayaranHutangImpl): CreatePembayaranHutang

  @Binds
  fun bindFindAllRecordPembayaranHutang(findAllRecordPembayaranHutang: FindAllRecordPembayaranHutangImpl): FindAllRecordPembayaranHutang

  @Binds
  fun bindSavePembayaranHutang(savePembayaranHutangImpl: SavePembayaranHutangImpl): SavePembayaranHutang

  @Binds
  fun bindFindHutangByIdWithFlow(findHutangByIdWithFlowImpl: FindHutangByIdWithFlowImpl): FindHutangByIdWithFlow

  @Binds
  fun bindFindHutangById(findHutangByIdImpl: FindHutangByIdImpl): FindHutangById

  @Binds
  fun bindDeleteRecordPembayaranHutang(deleteRecordPembayaranHutangImpl: DeleteRecordPembayaranHutangImpl): DeleteRecordPembayaranHutang

  @Binds
  fun bindUpdatePembayaranHutang(updatePembayaranHutangImpl: UpdatePembayaranHutangImpl): UpdatePembayaranHutang
}

