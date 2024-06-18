package com.ra.bkuang.features.category.di

import com.ra.bkuang.features.category.domain.usecase.DeleteKategoriUseCase
import com.ra.bkuang.features.category.domain.usecase.FindCategoryByTypeUseCase
import com.ra.bkuang.features.category.domain.usecase.FindCategoryWithFlowUseCase
import com.ra.bkuang.features.category.domain.usecase.SaveKategoriUseCase
import com.ra.bkuang.features.category.domain.usecase.UpdateKategoriUseCase
import com.ra.bkuang.features.category.domain.usecase.impl.DeleteKategoriUseCaseImpl
import com.ra.bkuang.features.category.domain.usecase.impl.FindCategoryByTypeUseCaseImpl
import com.ra.bkuang.features.category.domain.usecase.impl.FindCategoryWithFlowUseCaseImpl
import com.ra.bkuang.features.category.domain.usecase.impl.SaveKategoriUseCaseImpl
import com.ra.bkuang.features.category.domain.usecase.impl.UpdateKategoriUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface KategoriUseCaseModule {

  @Binds
  @Singleton
  fun bindFindCategoryByFlow(findCategoryWithFlowImpl: FindCategoryWithFlowUseCaseImpl): FindCategoryWithFlowUseCase

  @Binds
  @Singleton
  fun bindFindCategoryByType(findCategoryByTypeImpl: FindCategoryByTypeUseCaseImpl): FindCategoryByTypeUseCase

  @Binds
  @Singleton
  fun bindSaveKategoriUseCase(saveKategoriImpl: SaveKategoriUseCaseImpl): SaveKategoriUseCase

  @Binds
  @Singleton
  fun bindDeleteKategoriUseCase(deleteKategoriImpl: DeleteKategoriUseCaseImpl): DeleteKategoriUseCase

  @Binds
  @Singleton
  fun bindUpdateKategoriUseCase(updateKategoriImpl: UpdateKategoriUseCaseImpl): UpdateKategoriUseCase
}