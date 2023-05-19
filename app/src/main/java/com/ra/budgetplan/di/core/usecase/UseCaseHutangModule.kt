package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.hutang.DeleteHutang
import com.ra.budgetplan.domain.usecase.hutang.SaveHutang
import com.ra.budgetplan.domain.usecase.hutang.UpdateHutang
import com.ra.budgetplan.domain.usecase.hutang.impl.DeleteHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.SaveHutangImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.UpdateHutangImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseHutangModule {

  @Binds
  @Singleton
  fun bindSaveHutang(saveHutangImpl: SaveHutangImpl): SaveHutang

  @Binds
  @Singleton
  fun bindDeleteHutang(deleteHutangImpl: DeleteHutangImpl): DeleteHutang

  @Binds
  @Singleton
  fun bindUpdateHutang(updateHutangImpl: UpdateHutangImpl): UpdateHutang

}