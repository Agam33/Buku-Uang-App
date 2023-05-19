package com.ra.budgetplan.di.core.usecase

import com.ra.budgetplan.domain.usecase.cicilan.DeleteCicilan
import com.ra.budgetplan.domain.usecase.cicilan.FindAllCicilan
import com.ra.budgetplan.domain.usecase.cicilan.FindCicilanById
import com.ra.budgetplan.domain.usecase.cicilan.SaveCicilan
import com.ra.budgetplan.domain.usecase.cicilan.UpdateCicilan
import com.ra.budgetplan.domain.usecase.cicilan.impl.DeleteCicilanImpl
import com.ra.budgetplan.domain.usecase.cicilan.impl.FindAllCicilanImpl
import com.ra.budgetplan.domain.usecase.cicilan.impl.FindCicilanByIdImpl
import com.ra.budgetplan.domain.usecase.cicilan.impl.UpdateCicilanImpl
import com.ra.budgetplan.domain.usecase.hutang.impl.SaveHutangImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseCicilanModule {

  @Binds
  @Singleton
  fun bindSaveCicilan(saveHutangImpl: SaveHutangImpl): SaveCicilan

  @Binds
  @Singleton
  fun bindUpdateCicilan(updateCicilanImpl: UpdateCicilanImpl): UpdateCicilan

  @Binds
  @Singleton
  fun bindDeleteCicilan(deleteCicilanImpl: DeleteCicilanImpl): DeleteCicilan

  @Binds
  @Singleton
  fun bindFindCicilanById(findCicilanByIdImpl: FindCicilanByIdImpl): FindCicilanById

  @Binds
  @Singleton
  fun bindFindAllCicilan(findAllCicilanImpl: FindAllCicilanImpl): FindAllCicilan
}